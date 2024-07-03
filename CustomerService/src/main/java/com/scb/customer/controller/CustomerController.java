package com.scb.customer.controller;

import com.scb.customer.dto.TransactionData;
import com.scb.customer.entity.Account;
import com.scb.customer.entity.Customer;
import com.scb.customer.entity.Transaction;
import com.scb.customer.exception.AccountNotFoundException;
import com.scb.customer.exception.InSufficientBalanceException;
import com.scb.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/createCustomer")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountByCustomerId(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.getAccountByCustomerId(id),HttpStatus.OK);
    }

    @PostMapping("/account/deposit")
    public Account deposit(@RequestBody TransactionData data) throws AccountNotFoundException {
        return customerService.deposit(data.getFromAccountNumber(), data.getAmount());
    }

    @PostMapping("/account/withdraw")
    public Account withdraw(@RequestBody TransactionData data) throws AccountNotFoundException {
        return customerService.withdraw(data.getFromAccountNumber(), data.getAmount());
    }

    @PostMapping("/account/transfer")
    public ResponseEntity transfer(@RequestBody TransactionData data) throws InSufficientBalanceException {
        try{
            return new ResponseEntity(customerService.transfer(data.getFromAccountNumber(), data.getToAccountNumber(), data.getAmount()), HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/account/transactions/{id}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long id) throws AccountNotFoundException {
        return new ResponseEntity<>(customerService.getTransactions(id), HttpStatus.OK);
    }

}