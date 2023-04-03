package com.jbritland;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jbritland.entities.BankAccount;
import com.jbritland.entities.BankTransaction;
import com.jbritland.entities.BankTransaction.TransactionType;
import com.jbritland.entities.CurrentAccount;

public class CurrentAccountDepositTest {
	
	private BankAccount account;
	
	@BeforeEach
	public void SetUp() {
		LocalDateTime dt = LocalDateTime.now();
		this.account = new CurrentAccount("CUR", 1L, 1L, 200.00, dt,
			new ArrayList<BankTransaction>(), 100.00);
		// This is an account with a balance of 200 and an overdraft limit of 100
	}
	
	@Test
	public void instantiateCurrentAccount() {
		assertNotNull(account);
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
	
}
