package com.jbritland.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "typeName")
@JsonSubTypes({ @Type(CurrentAccount.class), @Type(SavingsAccount.class) })
@JsonIdentityInfo(
		   generator = ObjectIdGenerators.PropertyGenerator.class,
		   property = "accountNumber")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public abstract class BankAccount {
	
	@Id
	@GenericGenerator(
			name = "bankaccount-id-generator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "bankaccount_sequence"),
					@Parameter(name = "initial_value", value = "10000000"),
					@Parameter(name = "increment_size", value = "1")
			})
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bankaccount-id-generator")
	@Column(updatable=false)
	private long accountNumber;
	
	private String accountPrefix;
	
	private long accountholderId;
	
	@Column(nullable = false)
	double balance;
	LocalDateTime dateOpened;
	
	@OneToMany(mappedBy="bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	List<BankTransaction> bankTransactions = new ArrayList<BankTransaction>();

	public BankAccount() {
		super();
	}

	public BankAccount(String accountPrefix, long accountNumber, long accountholderId, double balance, LocalDateTime dateOpened,
			List<BankTransaction> bankTransactions) {
		super();
		this.accountPrefix = accountPrefix;
		this.accountNumber = accountNumber;
		this.accountholderId = accountholderId;
		this.balance = balance;
		this.dateOpened = dateOpened;
		this.bankTransactions = bankTransactions;
	}

	public BankAccount(long accountholderId, double balance, LocalDateTime dateOpened,
			List<BankTransaction> bankTransactions) {
		super();
		this.accountholderId = accountholderId;
		this.balance = balance;
		this.dateOpened = dateOpened;
		this.bankTransactions = bankTransactions;
	}
	
	public String getAccountPrefix() {
		return accountPrefix;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public long getAccountholderId() {
		return accountholderId;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public LocalDateTime getDateOpened() {
		return dateOpened;
	}

	public List<BankTransaction> getBankTransactions() {
		return bankTransactions;
	}
	
	@Override
	public String toString() {
		return "BankAccount [accountPrefix=" + accountPrefix + "accountNumber=" + accountNumber + ", accountholderId="
				+ accountholderId + ", balance=" + balance + ", dateOpened=" + dateOpened + "]";
	}

	public abstract void withdraw(double amount, String transactionDetails) throws OverdraftException;
	
	public abstract void deposit(double amount, String transactionDetails) throws RewardException;
	
}
