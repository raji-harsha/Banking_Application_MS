package com.scb.account.service;

import com.scb.account.entity.Account;
import com.scb.account.entity.Transaction;
import com.scb.account.exception.AccountNotFoundException;
import com.scb.account.exception.InSufficientBalanceException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {



    Optional<Account> getAccountByCustomerId(Long customerId);

    Account saveAccount(Account account);

    Account deposit(String accountId, Double amount) throws AccountNotFoundException;

    Account withdraw(String accountId, Double amount) throws AccountNotFoundException;

    Account transfer(String fromAccountId, String toAccountId, Double amount) throws AccountNotFoundException, InSufficientBalanceException;

    List<Transaction> getTransactions(Long id) throws AccountNotFoundException;

    Optional<Account> getAccountByAccNo(String accNo);

    Optional<Account> getAccountById(Long id);
}