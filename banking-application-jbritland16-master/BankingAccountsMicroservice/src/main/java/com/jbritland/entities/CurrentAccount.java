package com.jbritland.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jbritland.entities.BankTransaction.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "account_number")
@JsonTypeName("currentAccount")
public class CurrentAccount extends BankAccount {
	
	private double overdraftLimit;

	public CurrentAccount() {
		super();
	}

	public CurrentAccount(String accountPrefix, long accountNumber, long accountholderId, double balance, LocalDateTime dateOpened,
			List<BankTransaction> bankTransactions, double overdraftLimit) {
		super(accountPrefix, accountNumber, accountholderId, balance, dateOpened, bankTransactions);
		this.overdraftLimit = overdraftLimit;
	}

	public CurrentAccount(long accountholderId, double balance, LocalDateTime dateOpened,
			List<BankTransaction> bankTransactions, double overdraftLimit) {
		super(accountholderId, balance, dateOpened, bankTransactions);
		this.overdraftLimit = overdraftLimit;
	}

	public double getOverdraftLimit() {
		return overdraftLimit;
	}

	@Override
	public void withdraw(double amount, String transactionDetails) throws OverdraftException {
		this.balance -= amount;
		this.bankTransactions.add(new BankTransaction(this, TransactionType.WITHDRAW, amount, this.balance,
				LocalDateTime.now(), transactionDetails));
		if (this.balance < (-1 * this.overdraftLimit)) {
			this.balance += amount;
			BankTransaction reversalTransaction = (new BankTransaction(this, TransactionType.REVERSAL,
					amount, this.balance, LocalDateTime.now(),
					"Overdraft limit exceeded and reversal initiated"));
			this.bankTransactions.add(reversalTransaction);
			throw new OverdraftException("The withdrawn amount exceeded the overdraft limit",
					new ReversalEvent(reversalTransaction,
							this.getAccountPrefix() + this.getAccountNumber(),
							amount, this.balance, this.overdraftLimit));
		}
		
		
	}
	
	@Override
	public void deposit(double amount, String transactionDetails) {
		this.balance += amount;
		this.bankTransactions.add(new BankTransaction(this, TransactionType.DEPOSIT, amount, this.balance,
				LocalDateTime.now(), transactionDetails));
	}
	
	
	
}
