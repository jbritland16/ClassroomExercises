package com.jbritland.entities;

import java.io.Serializable;

public class AccountNumber implements Serializable {

	private String accountPrefix;
	private long accountNumber;
	
	public AccountNumber() {
		super();
	}

	public AccountNumber(String accountPrefix, long accountNumber) {
		super();
		this.accountPrefix = accountPrefix;
		this.accountNumber = accountNumber;
	}	
	
}
