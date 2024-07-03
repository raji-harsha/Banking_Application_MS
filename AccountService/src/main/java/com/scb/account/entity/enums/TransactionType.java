package com.scb.account.entity.enums;

public enum TransactionType {
    DEPOSIT("Self Deposit"),WITHDRAWAL("Withdraw"),TRANSFER("Transfer");
    private String type;

    public String getType() {
        return type;
    }

    TransactionType(String type){
        this.type = type;
    }
}
