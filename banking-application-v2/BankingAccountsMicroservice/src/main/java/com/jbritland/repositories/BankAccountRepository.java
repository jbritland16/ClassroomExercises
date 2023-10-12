package com.jbritland.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jbritland.entities.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

	//No addBankAccount method; save will be sufficient
	//No addTransaction method; can save parent BankAccount, which cascades
	
	public List<BankAccount> findByAccountholderId(long accountholderId);
	public BankAccount findByAccountNumber(long accountNumber);
	
}
