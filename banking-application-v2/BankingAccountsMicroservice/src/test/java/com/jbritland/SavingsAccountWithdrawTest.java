package com.jbritland;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jbritland.entities.BankAccount;
import com.jbritland.entities.BankTransaction;
import com.jbritland.entities.BankTransaction.TransactionType;
import com.jbritland.entities.OverdraftException;
import com.jbritland.entities.SavingsAccount;

public class SavingsAccountWithdrawTest {
	
	BankAccount account;

	@BeforeEach
	public void SetUp() {
		LocalDateTime dt = LocalDateTime.now();
		this.account = new SavingsAccount("SAV", 1L, 1L, 200.00, dt,
			new ArrayList<BankTransaction>(), 1000);
		// This is an account with a balance of 200 and an overdraft limit of 100
	}
	
	@Test
	public void withdrawZero() {
		try {
			account.withdraw(0, "some details");
		}
		catch (Exception e) {
			Assert.fail("Test failed: " + e.getMessage());
		}
		List<BankTransaction> bankTransactions = account.getBankTransactions();
		assertEquals(TransactionType.WITHDRAW, bankTransactions.get(bankTransactions.size() - 1).getType());
		assertEquals(200, account.getBalance(), 0.001);
	}
	
	@Test
	public void withdrawAmountUnderBalance() {
		try {
			account.withdraw(50, "some details");
		}
		catch (Exception e) {
			Assert.fail("Test failed: " + e.getMessage());
		}
		List<BankTransaction> bankTransactions = account.getBankTransactions();
		assertEquals(TransactionType.WITHDRAW, bankTransactions.get(bankTransactions.size() - 1).getType());
		assertEquals(150, account.getBalance(), 0.001);
	}
	
	@Test
	public void withdrawAmountEqualToBalance() {
		try {
			account.withdraw(200, "some details");
		}
		catch (Exception e) {
			Assert.fail("Test failed: " + e.getMessage());
		}
		List<BankTransaction> bankTransactions = account.getBankTransactions();
		assertEquals(TransactionType.WITHDRAW, bankTransactions.get(bankTransactions.size() - 1).getType());
		assertEquals(0, account.getBalance(), 0.001);
	}
	
	@Test
	public void withdrawAmountOverBalance() {
		OverdraftException e = assertThrows(OverdraftException.class, () -> {
	        account.withdraw(500, "some details");
	    });
		assertEquals(-300, account.getBalance(), 0.001);
		List<BankTransaction> bankTransactions = account.getBankTransactions();
		assertEquals(TransactionType.WITHDRAW, bankTransactions.get(bankTransactions.size() - 2).getType());
		assertEquals(TransactionType.REVERSAL, bankTransactions.get(bankTransactions.size() - 1).getType());
		assertNotNull(e.getReversalEvent());
	}
	

}
