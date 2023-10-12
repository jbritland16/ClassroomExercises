package com.jbritland.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ReversalEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int reversalId;
	
	@JoinColumn(name = "transaction_id")
	@OneToOne(cascade = CascadeType.ALL, targetEntity = BankTransaction.class, fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"bankAccount"})
	private BankTransaction bankTransaction;
	
	private String accountNumber;
	private double withdrawalAmount;
	private double balance;
	private double overdraftLimit;
	
	public ReversalEvent() {
		super();
	}
	
	public ReversalEvent(BankTransaction bankTransaction, String accountNumber, double withdrawalAmount, double balance,
			double overdraftLimit) {
		super();
		this.bankTransaction = bankTransaction;
		this.accountNumber = accountNumber;
		this.withdrawalAmount = withdrawalAmount;
		this.balance = balance;
		this.overdraftLimit = overdraftLimit;
	}

	public ReversalEvent(String accountNumber, double withdrawalAmount, double balance, double overdraftLimit) {
		super();
		this.accountNumber = accountNumber;
		this.withdrawalAmount = withdrawalAmount;
		this.balance = balance;
		this.overdraftLimit = overdraftLimit;
	}

	public BankTransaction getTransaction() {
		return bankTransaction;
	}

	public int getReversalId() {
		return reversalId;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public double getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public double getBalance() {
		return balance;
	}

	public double getOverdraftLimit() {
		return overdraftLimit;
	}

	@Override
	public String toString() {
		return "Reversal event for transaction #" + bankTransaction.getTransactionId();
	}
	
	

}
