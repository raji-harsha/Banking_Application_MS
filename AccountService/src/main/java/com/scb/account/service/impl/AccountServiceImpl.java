package com.scb.account.service.impl;

import com.scb.account.entity.Account;
import com.scb.account.entity.Transaction;
import com.scb.account.entity.enums.TransactionType;
import com.scb.account.exception.AccountNotFoundException;
import com.scb.account.exception.InSufficientBalanceException;
import com.scb.account.repository.AccountRepository;
import com.scb.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Optional<Account> getAccountByCustomerId(Long customerId) {
        return Optional.ofNullable(accountRepository.findAccountByCustomerId(customerId));
    }

    @Override
    public Optional<Account> getAccountByAccNo(String accNo) {
        return accountRepository.findByAccountNumber(accNo);
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account saveAccount(Account account) {
        log.debug("Account created successfully : {}", account);
        return accountRepository.save(account);
    }

    @Override
    public Account deposit(String accNo, Double amount) throws AccountNotFoundException {
        Account account = accountRepository.findByAccountNumber(accNo)
                .orElseThrow(() ->{
                    log.error("Source account not found");
                    return new AccountNotFoundException("Source account not found");
                });
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        Account savedAcc = accountRepository.save(account);
        createAndSaveTransaction(TransactionType.DEPOSIT, amount, savedAcc.getAccountNumber(), savedAcc.getAccountNumber());
        return savedAcc;
    }

    @Override
    public Account withdraw(String accNo, Double amount) throws AccountNotFoundException {
        Account account = accountRepository.findByAccountNumber(accNo)
                .orElseThrow(() -> {
                    log.error("Source account not found");
                    return new AccountNotFoundException("Source account not found");
                });

        double currentBalance = account.getBalance();

        if (currentBalance < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        double newBalance = currentBalance - amount;
        account.setBalance(newBalance);

        Account savedAcc = accountRepository.save(account);
        createAndSaveTransaction(TransactionType.WITHDRAWAL, amount, savedAcc.getAccountNumber(), savedAcc.getAccountNumber());
        return savedAcc;
    }

    @Override
    public Account transfer(String fromAccNo, String toAccNo, Double amount) throws AccountNotFoundException, InSufficientBalanceException {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccNo)
                .orElseThrow(() -> {
                    log.error("Source account not found");
                    return new AccountNotFoundException("Source account not found");
                });

        Account toAccount = accountRepository.findByAccountNumber(toAccNo)
                .orElseThrow(() -> {
                    log.error("Destination account not found");
                    return new AccountNotFoundException("Destination account not found :"+ toAccNo);
                });

        double currentBalance = fromAccount.getBalance();

        if (currentBalance < amount) {
            log.error("Insufficient balance in source account");
            throw new InSufficientBalanceException("Insufficient balance in source account");
        }

        double newBalanceFromAccount = currentBalance - amount;
        fromAccount.setBalance(newBalanceFromAccount);

        double newBalanceToAccount = toAccount.getBalance() + amount;
        toAccount.setBalance(newBalanceToAccount);

        accountRepository.save(fromAccount);
        log.debug("Source account updated successfully : {}", fromAccount);
        accountRepository.save(toAccount);
        log.debug("Destination account updated successfully : {}", toAccount);

        createAndSaveTransaction(TransactionType.TRANSFER, amount, fromAccount.getAccountNumber(), toAccount.getAccountNumber());
        return fromAccount;
    }

    @Override
    public List<Transaction> getTransactions(Long id) throws AccountNotFoundException {
        Account acc = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return restTemplate.getForObject("http://TRANSACTION-SERVICE/transaction/"+acc.getAccountNumber(),List.class);
    }

    public Transaction createAndSaveTransaction(TransactionType type, Double amount, String fromAccNo, String toAccNo) {
        Transaction transaction = new Transaction();
        transaction.setType(type.getType());
        transaction.setAmount(amount);
        transaction.setFromAccNo(fromAccNo);
        transaction.setToAccNo(toAccNo);
        transaction.setTransactionDate(LocalDateTime.now());
        log.debug("Transaction created successfully : {}", transaction);
        return restTemplate.postForObject("http://TRANSACTION-SERVICE/transaction/create",transaction, Transaction.class);
    }

}