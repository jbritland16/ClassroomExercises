package com.jbritland;

import static org.junit.Assert.assertEquals;
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
import com.jbritland.entities.RewardException;
import com.jbritland.entities.SavingsAccount;

public class SavingsAccountDepositTest {
	
	BankAccount account;
	
	@BeforeEach
	public void SetUp() {
		LocalDateTime dt = LocalDateTime.now();
		this.account = new SavingsAccount("SAV", 1L, 1L, 200.00, dt,
			new ArrayList<BankTransaction>(), 1000);
		// This is an account with a balance of 200 and an overdraft limit of 100
	}

	@Test
	public void depositZero() {
		try {
			account.deposit(0, "some details");
		}
		catch (Exception e) {
			Assert.fail("Test failed: " + e.getMessage());
		}
		assertEquals(200, account.getBalance(), 0.001);
		assertEquals(1, account.getBankTransactions().size());
	}
	
	@Test
	public void depositSmallAmount() {
		try {
			account.deposit(20, "some details");
		}
		catch (Exception e) {
			Assert.fail("Test failed: " + e.getMessage());
		}
		assertEquals(220, account.getBalance(), 0.001);
		assertEquals(1, account.getBankTransactions().size());
		List<BankTransaction> t = account.getBankTransactions();
		assertEquals(TransactionType.DEPOSIT, t.get(t.size() - 1).getType());
	}
	
	@Test
	public void depositAmountCrossRewardThreshold() {
		RewardException e = assertThrows(RewardException.class, () -> {
			account.deposit(1000, "some details");
	    });
		assertEquals(1000, e.getThresholdCrossed());
		assertEquals(1200, account.getBalance(), 0.001);
		assertEquals(1, account.getBankTransactions().size());
		List<BankTransaction> t = account.getBankTransactions();
		assertEquals(TransactionType.DEPOSIT, t.get(t.size() - 1).getType());
	}
	
}
