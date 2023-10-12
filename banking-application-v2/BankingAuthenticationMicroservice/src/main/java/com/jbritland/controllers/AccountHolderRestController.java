package com.jbritland.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jbritland.entities.AccountHolder;
import com.jbritland.services.AccountHolderService;

@RestController
public class AccountHolderRestController {

	@Autowired
	private AccountHolderService ahService;
	
	/**
	 * Adds the new AccountHolder to the database. Client should verify that 
	 * email is not in use by another account using emailInUseByAnotherAccount method.
	 * Requires email, firstName, lastName, and password.
	 * Will return null if unsuccessful.
	 */
	@PostMapping("/users/register")
	public AccountHolder registerAccountholder(@RequestBody AccountHolder newUser) {
		return this.ahService.registerAccountholder(newUser);
	}
	
	/**
	 * Checks that the email and password match and returns the matching AccountHolder 
	 * if any; otherwise, returns null. Requires only email and password.
	 */
	@GetMapping("/users/login")
	public AccountHolder authenticateAccountholder(@RequestBody AccountHolder loginUser) {
		return this.ahService.authenticateAccountholderByEmail(
				loginUser.getEmail(), loginUser.getPassword());
	}
	
	/**
	 * Updates the email and name fields in an AccountHolder record. Client should 
	 * verify that email is not in use by another account using the 
	 * emailInUseByAnotherAccount method and should also authenticate using the 
	 * authenticateAccountholder method. Will return null if unsuccessful. 
	 * Requires email, firstName, lastName, and accountholderId fields.
	 */
	@PatchMapping("/user/update-info")
	public AccountHolder updateAccountholder(@RequestBody AccountHolder userInfo) {
		return this.ahService.updateAccountholder(userInfo);
	}
	
	/**
	 * Updates the password field in the AccountHolder record. Client should 
	 * authenticate using the authenticateAccountholderByEmail method. 
	 * Requires accountholderId and password fields.
	 */
	@PatchMapping("/user/update-password")
	public AccountHolder updatePassword(@RequestBody AccountHolder userInfo) {
		return this.ahService.updatePassword(userInfo);
	}
	
	/**
	 * The email field is bound by unique constraint. This method is 
	 * for verifying that an email is available for use by an account. 
	 * Will return false if the email is not in use or if it already belongs 
	 * to the ID number in the AccountHolder object. Otherwise, returns true.
	 * Requires email and accountholderId fields.
	 */
	@GetMapping("/users/email-in-use")
	public boolean emailInUseByAnotherAccount(@RequestBody AccountHolder userInfo) {
		return this.ahService.emailInUseByAnotherAccount(userInfo);
	}
	
}
