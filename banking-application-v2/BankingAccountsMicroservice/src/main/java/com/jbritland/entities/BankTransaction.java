package com.jbritland.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(
		   generator = ObjectIdGenerators.PropertyGenerator.class,
		   property = "transactionId")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class BankTransaction {
	
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
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "accountNumber")
	@JsonBackReference
	@JsonProperty("bankAccount")
	private BankAccount bankAccount;
	
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
	
	public BankTransaction() {
		super();
	}

	public BankTransaction(long transactionId, BankAccount bankAccount, TransactionType type, double amount,
			double resultingBalance, LocalDateTime transactionTimestamp, String details) {
		super();
		this.transactionId = transactionId;
		this.bankAccount = bankAccount;
		this.type = type;
		this.amount = amount;
		this.resultingBalance = resultingBalance;
		this.transactionTimestamp = transactionTimestamp;
		this.details = details;
	}

	public BankTransaction(BankAccount bankAccount, TransactionType type, double amount, double resultingBalance,
			LocalDateTime transactionTimestamp, String details) {
		super();
		this.bankAccount = bankAccount;
		this.type = type;
		this.amount = amount;
		this.resultingBalance = resultingBalance;
		this.transactionTimestamp = transactionTimestamp;
		this.details = details;
	}

	public long getTransactionId() {
		return transactionId;
	}
	
	@JsonIgnore
	public BankAccount getAccount() {
		return bankAccount;
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
		return "BankTransaction [transactionId=" + transactionId + ", transactionTimestamp=" + transactionTimestamp + "]";
	}
	
}
