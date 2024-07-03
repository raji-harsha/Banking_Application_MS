package com.scb.customer.service;

import com.scb.customer.entity.Account;
import com.scb.customer.entity.Customer;
import com.scb.customer.entity.Transaction;
import com.scb.customer.exception.AccountNotFoundException;
import com.scb.customer.exception.InSufficientBalanceException;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    
    Optional<Customer> getCustomerById(Long id);


    Account getAccountByCustomerId(Long customerId);

    Account deposit(String accountId, Double amount) throws AccountNotFoundException;

    Account withdraw(String accountId, Double amount) throws AccountNotFoundException;

    Account transfer(String fromAccountId, String toAccountId, Double amount) throws AccountNotFoundException, InSufficientBalanceException;

    List<Transaction> getTransactions(Long id) throws AccountNotFoundException;

    Customer createCustomer(Customer customer);
}