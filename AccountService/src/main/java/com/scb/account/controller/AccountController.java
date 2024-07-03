package com.scb.account.controller;

import com.scb.account.entity.Account;
import com.scb.account.entity.Transaction;
import com.scb.account.exception.AccountNotFoundException;
import com.scb.account.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;
    @GetMapping("cust/{id}")
    public ResponseEntity<Account> getAccountByCustomerId(@PathVariable Long id) {
        return accountService.getAccountByCustomerId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{accNo}")
    public ResponseEntity<Account> getAccountByAccNo(@PathVariable String accNo) {
        return accountService.getAccountByAccNo(accNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<Account> createTransaction(@RequestBody Account account) {
        return new ResponseEntity<>(accountService.saveAccount(account), HttpStatus.OK);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long id) throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.getTransactions(id), HttpStatus.OK);
    }

}