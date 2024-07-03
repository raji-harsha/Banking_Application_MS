package com.scb.customer.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionData {

	String fromAccountNumber;
	String toAccountNumber;
	Double amount;

	Long customerId;

	String userName;
	String password;

}