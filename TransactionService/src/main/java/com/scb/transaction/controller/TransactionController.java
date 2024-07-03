package com.scb.transaction.controller;


import com.scb.transaction.entity.Transaction;
import com.scb.transaction.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")

public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{accNo}")
    public ResponseEntity<List<Transaction>> getTransactionByAccNo(@PathVariable String accNo) {
        List<Transaction> loginDetails = transactionService.getTransactionsByAccountNumber(accNo);
        return new ResponseEntity<>(loginDetails, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.createAndSaveTransaction(transaction),HttpStatus.OK);
    }

}