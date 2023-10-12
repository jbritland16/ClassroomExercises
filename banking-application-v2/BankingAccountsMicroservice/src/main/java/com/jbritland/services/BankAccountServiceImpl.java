package com.jbritland.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbritland.entities.BankAccount;
import com.jbritland.entities.BankTransaction;
import com.jbritland.entities.BankTransaction.TransactionType;
import com.jbritland.entities.OverdraftException;
import com.jbritland.entities.RewardException;
import com.jbritland.repositories.BankAccountRepository;
import com.jbritland.repositories.ReversalRepository;

@Service
public class BankAccountServiceImpl implements BankAccountService {
	
	@Autowired
	private BankAccountRepository bRepo;
	
	@Autowired
	private ReversalRepository rRepo;

	@Override
	public BankAccount createNewAccount(BankAccount account) {
		account.getBankTransactions().add(new BankTransaction(account, 
				TransactionType.OPENING_BALANCE, account.getBalance(),
				account.getBalance(), account.getDateOpened(), 
				"Opening balance on creation of account"));
		return bRepo.save(account);
	}

	@Override
	public List<BankAccount> getAccountsByAccountholderId(long accountholderId) {
		return bRepo.findByAccountholderId(accountholderId);
	}

	@Override
	public BankAccount getAccountByAccountNumber(long accountNumber) {
		return bRepo.findByAccountNumber(accountNumber);
	}

	@Override
	public boolean withdrawTransaction(BankAccount account, double amount, String transactionDetails) {
		boolean transactionSuccessful;
		account = bRepo.findByAccountNumber(account.getAccountNumber());
		try {
			account.withdraw(amount, transactionDetails);
			transactionSuccessful = true;
		}
		catch (OverdraftException e) {
			transactionSuccessful = false;
			rRepo.save(e.getReversalEvent());
		}
		finally {
			bRepo.save(account);
		}
		return transactionSuccessful;
	}

	@Override
	public boolean depositTransaction(BankAccount account, double amount, String transactionDetails) {
		boolean reward;
		account = bRepo.findByAccountNumber(account.getAccountNumber());
		try {
			account.deposit(amount, transactionDetails);
			reward = false;
		}
		catch (RewardException e) {
			reward = true;
		}
		finally {
			bRepo.save(account);
		}
		return reward;
	}

	@Override
	public int internalTransferTransaction(BankAccount outAccount, BankAccount inAccount, double amount,
			String transactionDetails) {
		int transactionResult = -10;
		outAccount = bRepo.findByAccountNumber(outAccount.getAccountNumber());
		inAccount = bRepo.findByAccountNumber(inAccount.getAccountNumber());
		try {
			outAccount.withdraw(amount, transactionDetails);
			inAccount.deposit(amount, transactionDetails);
			transactionResult = 0;
		}
		catch (OverdraftException e) {
			transactionResult = -1;
			rRepo.save(e.getReversalEvent());
		}
		catch (RewardException e) {
			transactionResult = 1;
		}
		finally {
			bRepo.save(outAccount);
			bRepo.save(inAccount);
		}
		return transactionResult;
	}

}
