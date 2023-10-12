package com.jbritland.services;

import java.util.List;

import com.jbritland.entities.BankAccount;

public interface BankAccountService {

	/**
	 * Saves the given account to the database and returns the entity for further operations
	 * @param account
	 * @return account
	 */
	public BankAccount createNewAccount(BankAccount account);
	
	/**
	 * Returns a list of accounts matching the given accountholder's ID number
	 * @param accountholderId
	 */
	public List<BankAccount> getAccountsByAccountholderId(long accountholderId);
	
	/**
	 * Returns a reference to the BankAccount entity from the database matching the given 
	 * account prefix and number for further operations
	 * @param accountPrefix
	 * @param accountNumber
	 */
	public BankAccount getAccountByAccountNumber(long accountNumber);
	
	/**
	 * Performs a withdraw transaction on the given entity and saves it to the database. 
	 * Returns true if the transaction is successful. If the transaction reduces 
	 * the balance below 0 (or the overdraft limit if applicable), a reversal transaction 
	 * is also added and saved to the database and false is returned.
	 * @param account
	 * @param amount
	 * @param transactionDetails
	 * @return transactionSuccessful
	 */
	public boolean withdrawTransaction(BankAccount account, double amount, String transactionDetails);
	
	/**
	 * Performs a deposit transaction on the given entity and saves it to the database. 
	 * Returns true if the balance goes over the next reward threshold and an offer is earned. 
	 * Else returns false (but the transaction is still successful and is saved).
	 * @param account
	 * @param amount
	 * @param transactionDetails
	 * @return boolean reward
	 */
	public boolean depositTransaction(BankAccount account, double amount, String transactionDetails);
	
	/**
	 * Performs a withdraw transaction on the outAccount. If the transaction reduces the 
	 * balance to below 0 (or the overdraft limit if applicable), a reversal transaction 
	 * is also added and saved to the database, and no changes are made to the inAccount. If 
	 * the withdrawal is successful, a deposit transaction is performed on the inAccount, 
	 * including checking to see if the inAccount has passed the threshold for the next reward.
	 * Finally, saves both accounts and returns a value based on the result of the transaction.
	 * For overdraft and reversal, returns -1. For success and no reward, returns 0. 
	 * For success and reward, returns 1.
	 * @param outAccount
	 * @param inAccount
	 * @param amount
	 * @param transactionDetails
	 * @return
	 */
	public int internalTransferTransaction(BankAccount outAccount, BankAccount inAccount,
			double amount, String transactionDetails);
	
}
