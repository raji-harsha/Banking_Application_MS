package com.example.demo.service.impl;

import com.example.demo.service.TransactionListenerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionListenerServiceImpl implements TransactionListenerService {

    @KafkaListener(topics = "transactions", groupId = "banking")
    public void consume(String message) {
        System.out.println("Consumed message: " +  message);
    }
}
