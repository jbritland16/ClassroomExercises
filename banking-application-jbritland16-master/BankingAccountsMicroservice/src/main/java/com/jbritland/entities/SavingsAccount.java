package com.jbritland.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jbritland.entities.BankTransaction.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "account_number")
@JsonTypeName("savingsAccount")
public class SavingsAccount extends BankAccount {
	
	private int nextOfferThreshold = 1000;

	public SavingsAccount() {
		super();
	}

	public SavingsAccount(String accountPrefix, long accountNumber, long accountholderId, double balance, LocalDateTime dateOpened,
			List<BankTransaction> bankTransactions, int nextOfferThreshold) {
		super(accountPrefix, accountNumber, accountholderId, balance, dateOpened, bankTransactions);
		this.nextOfferThreshold = nextOfferThreshold;
	}

	public SavingsAccount(long accountholderId, double balance, LocalDateTime dateOpened,
			List<BankTransaction> bankTransactions, int nextOfferThreshold) {
		super(accountholderId, balance, dateOpened, bankTransactions);
		this.nextOfferThreshold = nextOfferThreshold;
	}

	public int getNextOfferThreshold() {
		return nextOfferThreshold;
	}

	@Override
	public void withdraw(double amount, String transactionDetails) throws OverdraftException {
		this.balance -= amount;
		this.bankTransactions.add(new BankTransaction(this, TransactionType.WITHDRAW, amount, this.balance,
				LocalDateTime.now(), transactionDetails));
		if (this.balance < 0) {
			BankTransaction reversalTransaction = new BankTransaction(this, TransactionType.REVERSAL, amount, this.balance,
					LocalDateTime.now(), "Overdraft limit exceeded and reversal initiated");
			this.bankTransactions.add(reversalTransaction);
			throw new OverdraftException("The withdrawn amount exceeded the balance of the account", 
					new ReversalEvent(reversalTransaction,
					this.getAccountPrefix() + this.getAccountNumber(),
					amount, this.balance, 0));
		
		}
	}

	@Override
	public void deposit(double amount, String transactionDetails) throws RewardException {
		this.balance += amount;
		this.bankTransactions.add(new BankTransaction(this, TransactionType.DEPOSIT, amount, this.balance,
				LocalDateTime.now(), transactionDetails));
		if (this.balance >= this.nextOfferThreshold) {
			this.nextOfferThreshold += 1000;
			throw new RewardException(this.nextOfferThreshold - 1000);
		}
	}
	
	
	
}
