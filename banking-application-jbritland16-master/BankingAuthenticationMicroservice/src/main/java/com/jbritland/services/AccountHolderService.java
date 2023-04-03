package com.jbritland.services;

import com.jbritland.entities.AccountHolder;

public interface AccountHolderService {

	public AccountHolder registerAccountholder(AccountHolder newUser);
	
	public AccountHolder authenticateAccountholderByEmail(String email, String password);
	
	public AccountHolder updateAccountholder(AccountHolder userInfo);
	
	public AccountHolder updatePassword(AccountHolder userInfo);
	
	public boolean emailInUseByAnotherAccount(AccountHolder userInfo);
	
}
