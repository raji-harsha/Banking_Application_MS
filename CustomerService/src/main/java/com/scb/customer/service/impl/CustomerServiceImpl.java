package com.scb.customer.service.impl;

import com.scb.customer.entity.Account;
import com.scb.customer.entity.Customer;
import com.scb.customer.entity.Transaction;
import com.scb.customer.exception.InSufficientBalanceException;
import com.scb.customer.repository.CustomerRepository;
import com.scb.customer.service.CustomerService;
import com.scb.customer.util.BankingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Account getAccountByCustomerId(Long customerId) {
        return restTemplate.getForObject("http://ACCOUNT-SERVICE/account/cust/" + customerId, Account.class);
    }

    @Override
    public Account deposit(String accNo, Double amount){
        Account account = restTemplate.getForObject("http://ACCOUNT-SERVICE/account/"+accNo,Account.class);
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        return restTemplate.postForObject("http://ACCOUNT-SERVICE/account/save",account, Account.class);
    }

    @Override
    public Account withdraw(String accNo, Double amount) {
        Account account = restTemplate.getForObject("http://ACCOUNT-SERVICE/account/"+accNo,Account.class);
        double currentBalance = account.getBalance();
        if (currentBalance < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        double newBalance = currentBalance - amount;
        account.setBalance(newBalance);
        return restTemplate.postForObject("http://ACCOUNT-SERVICE/account/save",account, Account.class);
    }

    @Override
    public Account transfer(String fromAccNo, String toAccNo, Double amount) throws  InSufficientBalanceException {
        Account fromAccount = restTemplate.getForObject("http://ACCOUNT-SERVICE/account/"+fromAccNo,Account.class);
        Account toAccount = restTemplate.getForObject("http://ACCOUNT-SERVICE/account/"+toAccNo,Account.class);
        double currentBalance = fromAccount.getBalance();
        if (currentBalance < amount) {
            log.error("Insufficient balance in source account");
            throw new InSufficientBalanceException("Insufficient balance in source account");
        }
        double newBalanceFromAccount = currentBalance - amount;
        fromAccount.setBalance(newBalanceFromAccount);
        double newBalanceToAccount = toAccount.getBalance() + amount;
        toAccount.setBalance(newBalanceToAccount);
        restTemplate.postForObject("http://ACCOUNT-SERVICE/account/save",fromAccount, Account.class);;
        log.debug("Source account updated successfully : {}", fromAccount);
        restTemplate.postForObject("http://ACCOUNT-SERVICE/account/save",toAccount, Account.class);;
        log.debug("Destination account updated successfully : {}", toAccount);
        return fromAccount;
    }

    @Override
    public List<Transaction> getTransactions(Long id) {
        return restTemplate.getForObject("http://ACCOUNT-SERVICE/account/transactions/"+id,List.class);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        log.debug("Customer saved successfully : {}", savedCustomer);
        Account account = new Account();
        account.setId(savedCustomer.getId());
        account.setAccountNumber(BankingUtils.generateAccountNumber());
        account.setCustomer(savedCustomer);
        restTemplate.postForObject("http://ACCOUNT-SERVICE/account/save",account, Account.class);;
        log.debug("Account created successfully : {}", account);
        return savedCustomer;
    }
}