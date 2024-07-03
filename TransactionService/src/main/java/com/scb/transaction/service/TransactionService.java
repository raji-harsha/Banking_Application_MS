package com.scb.transaction.service;

import com.scb.transaction.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByAccountNumber(String accId);

    Transaction createAndSaveTransaction(Transaction transaction);
}