package com.jbritland.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Embeddable
public class Transaction {
	
	@Id
	@GenericGenerator(
			name = "transaction-id-generator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "transaction_sequence"),
					@Parameter(name = "initial_value", value = "10000000"),
					@Parameter(name = "increment_size", value = "1")
			})
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "transaction-id-generator")
	@Column(updatable=false)
	private long transactionId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accountPrefix")
	@JoinColumn(name = "accountNumber")
	private BankAccount account;
	
	public enum TransactionType {
		DEPOSIT,
		WITHDRAW,
		OPENING_BALANCE,
		REVERSAL
	}
	private TransactionType type;
	
	private double amount;
	private double resultingBalance;
	private LocalDateTime transactionTimestamp;
	private String details;
	
	public Transaction() {
		super();
	}

	public Transaction(long transactionId, BankAccount account, TransactionType type, double amount,
			double resultingBalance, LocalDateTime transactionTimestamp, String details) {
		super();
		this.transactionId = transactionId;
		this.account = account;
		this.type = type;
		this.amount = amount;
		this.resultingBalance = resultingBalance;
		this.transactionTimestamp = transactionTimestamp;
		this.details = details;
	}

	public Transaction(BankAccount account, TransactionType type, double amount, double resultingBalance,
			LocalDateTime transactionTimestamp, String details) {
		super();
		this.account = account;
		this.type = type;
		this.amount = amount;
		this.resultingBalance = resultingBalance;
		this.transactionTimestamp = transactionTimestamp;
		this.details = details;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public BankAccount getAccount() {
		return account;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public double getResultingBalance() {
		return resultingBalance;
	}

	public LocalDateTime getTransactionTimestamp() {
		return transactionTimestamp;
	}

	public String getDetails() {
		return details;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", transactionTimestamp=" + transactionTimestamp + "]";
	}
	
}
