package com.jbritland.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jbritland.entities.BankAccount;
import com.jbritland.services.BankAccountService;

import dtos.BankAccountTransactionDetailsDTO;

@RestController
public class BankAccountRestController {
	
	@Autowired
	BankAccountService baService;
	
	@PostMapping("/accounts/create-account")
	public BankAccount createNewAccount(@RequestBody BankAccount account) {
		return baService.createNewAccount(account);
	}
	
	@GetMapping("/accounts/user-accounts")
	public List<BankAccount> getAccountsByAccountholder(@RequestBody long accountholderId) {
		return baService.getAccountsByAccountholderId(accountholderId);
	}
	
	@GetMapping("/account/account-details")
	public BankAccount getAccountByAccountPrefixNumber(@RequestBody long accountNumber) {
		return baService.getAccountByAccountNumber(accountNumber);	
	}
	
	@PostMapping("/account/transaction/withdraw")
	public boolean withdrawTransaction(@RequestBody BankAccountTransactionDetailsDTO details) {
		return baService.withdrawTransaction(details.getWithdrawAccount(), 
				details.getAmount(), details.getTransactionDetails());
	}
	
	@PostMapping("/account/transaction/deposit")
	public boolean depositTransaction(@RequestBody BankAccountTransactionDetailsDTO details) {
		return baService.depositTransaction(details.getDepositAccount(), 
				details.getAmount(), details.getTransactionDetails());
	}
	
	@PostMapping("/account/transaction/transfer")
	public int internalTransferTransaction(@RequestBody BankAccountTransactionDetailsDTO details) {
		return baService.internalTransferTransaction(details.getWithdrawAccount(),
				details.getDepositAccount(), details.getAmount(), details.getTransactionDetails());
	}
	
}
