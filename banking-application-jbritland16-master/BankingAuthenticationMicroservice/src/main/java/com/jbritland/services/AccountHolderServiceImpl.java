package com.jbritland.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbritland.entities.AccountHolder;
import com.jbritland.repositories.AccountHolderRepository;

@Service
public class AccountHolderServiceImpl implements AccountHolderService {
	
	@Autowired
	private AccountHolderRepository ahRepo;

	/**
	 * Adds the new AccountHolder to the database. Client should verify that 
	 * email is not in use by another account using emailInUseByAnotherAccount method.
	 */
	@Override
	public AccountHolder registerAccountholder(AccountHolder newUser) {
		if (this.ahRepo.accountholderMatchingEmail(newUser.getEmail()) == null) {
			return this.ahRepo.save(newUser);
		}
		else {
			return null;
		}
	}

	/**
	 * Checks that the email and password match and returns the matching AccountHolder 
	 * if any; otherwise, returns null
	 */
	@Override
	public AccountHolder authenticateAccountholderByEmail(String email, String password) {
		return this.ahRepo.accountholderMatchingPasswordAndEmail(email, password);
	}

	/**
	 * Updates the email and name fields in an AccountHolder record. Client should 
	 * verify that email is not in use by another account using the 
	 * emailInUseByAnotherAccount method and should also authenticate using the 
	 * authenticateAccountholderByEmail method.
	 */
	@Override
	public AccountHolder updateAccountholder(AccountHolder userInfo) {
		if (this.ahRepo.updateInfoById(userInfo.getAccountholderId(), userInfo.getFirstName(),
				userInfo.getLastName(), userInfo.getEmail()) == 1) {
			return this.ahRepo.getAccountholderById(userInfo.getAccountholderId());
		}
		else {
			return null;
		}
	}

	/**
	 * Updates the password field in the AccountHolder record. Client should 
	 * authenticate using the authenticateAccountholderByEmail method.
	 */
	@Override
	public AccountHolder updatePassword(AccountHolder newInfo) {
		if (this.ahRepo.updatePasswordById(
				newInfo.getAccountholderId(), newInfo.getPassword()) == 1) {
			return this.ahRepo.getAccountholderById(newInfo.getAccountholderId());
		}
		else {
			return null;
		}
	}

	/**
	 * The email field is bound by unique constraint. This method is 
	 * for verifying that an email is available for use by an account. 
	 * Will return false if the email is not in use or if it already belongs 
	 * to the ID number in the AccountHolder object. Otherwise, returns true.
	 */
	@Override
	public boolean emailInUseByAnotherAccount(AccountHolder userInfo) {
		if (this.ahRepo.accountholderMatchingEmail(userInfo.getEmail()) == null 
				|| this.ahRepo.accountholderMatchingEmailAndId(userInfo.getAccountholderId(),
						userInfo.getEmail()) != null) {
			return false;
		}
		else {
			return true;
		}
	}


}
