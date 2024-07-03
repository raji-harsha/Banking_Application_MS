package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    private final String URI_EMPLOYEE = "http://localhost:8080/customer/";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{id}")
    public ResponseEntity getCustomerInfo(@PathVariable Long id) {
        Customer customer = restTemplate.getForObject(URI_EMPLOYEE + String.valueOf(id), Customer.class);
        return new ResponseEntity < > (customer, HttpStatus.OK);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountByCustomerId(@PathVariable Long id) {
        Account account = restTemplate.getForObject(URI_EMPLOYEE +"account/"+String.valueOf(id), Account.class);
        return new ResponseEntity < > (account, HttpStatus.OK);
    }

    @GetMapping("/account/transactions/{id}")
    public ResponseEntity<List<Transaction>> transactions(@PathVariable Long id) {
        return new ResponseEntity<>(restTemplate.getForObject(URI_EMPLOYEE +"account/transactions/"+String.valueOf(id), List.class), HttpStatus.OK);
    }

}