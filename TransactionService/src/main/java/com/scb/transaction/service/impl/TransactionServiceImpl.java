package com.scb.transaction.service.impl;

import com.scb.transaction.entity.Transaction;
import com.scb.transaction.repository.TransactionRepository;
import com.scb.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }


    @Override
    public List<Transaction> getTransactionsByAccountNumber(String accId) {
        return transactionRepository.findTransactionsByFromAccountIdOrToAccountId(accId);
    }

    @Override
    public Transaction createAndSaveTransaction(Transaction transaction) {
        log.debug("Transaction created successfully : {}", transaction);
        return transactionRepository.save(transaction);
    }

}