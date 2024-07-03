package com.scb.admin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double amount;
    private LocalDateTime transactionDate;

    private String type;
    private String fromAccNo;

    private String toAccNo;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", type='" + type + '\'' +
                ", fromAccNo='" + fromAccNo + '\'' +
                ", toAccNo='" + toAccNo + '\'' +
                '}';
    }
}