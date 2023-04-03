package com.jbritland.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AccountHolder {
	
	@Id
	@GenericGenerator(
			name = "accountholder-id-generator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "accountholder_sequence"),
					@Parameter(name = "initial_value", value = "10000"),
					@Parameter(name = "increment_size", value = "1")
			})
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "accountholder-id-generator")
	@Column(updatable=false)
	private long accountholderId;
	@Column(unique=true)
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	
	public AccountHolder() {
		super();
	}
	
	public AccountHolder(String email, String password, String firstName, String lastName) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public AccountHolder(long accountholderId, String email, String firstName,
			String lastName, String password) {
		super();
		this.accountholderId = accountholderId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	public long getAccountholderId() {
		return accountholderId;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getPassword() {
		return this.password;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + " (" + email + ")";
	}
	
	
	
}
