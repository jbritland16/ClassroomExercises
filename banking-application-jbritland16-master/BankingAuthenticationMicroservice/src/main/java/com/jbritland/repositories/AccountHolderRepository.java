package com.jbritland.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jbritland.entities.AccountHolder;

import jakarta.transaction.Transactional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
	
	@Query("FROM AccountHolder "
			+ "WHERE email = :aEmail AND password = :aPassword")
	public AccountHolder accountholderMatchingPasswordAndEmail(@Param("aEmail") String email,
			@Param("aPassword") String password);
	
	@Query("FROM AccountHolder "
			+ "WHERE accountholderId = :aId AND password = :aPassword")
	/**
	 * @param accountholderId
	 * @param password
	 * Returns the AccountHolder matching the given ID and password, or null
	 */
	public AccountHolder accountholderMatchingPasswordAndId(@Param("aId") long accountholderId,
			@Param("aPassword") String password);
	
	@Query("FROM AccountHolder a "
			+ "WHERE a.accountholderId = :aId")
	public AccountHolder getAccountholderById(@Param("aId") long accountholderId);
	
	@Query("UPDATE AccountHolder SET firstName = :aFirstname, lastName = :aLastname, "
			+ "email = :aEmail WHERE accountholderId = :aId")
	@Transactional
	@Modifying(clearAutomatically = true)
	public int updateInfoById(@Param("aId") long id,
			@Param("aFirstname") String firstName, 
			@Param("aLastname") String lastName,
			@Param("aEmail") String email);
	
	@Query("UPDATE AccountHolder SET password = :newPassword "
			+ "WHERE accountholderId = :aId")
	@Transactional
	@Modifying(clearAutomatically = true)
	public int updatePasswordById(@Param("aId") long aId,
			@Param("newPassword") String newPassword);
	
	@Query("FROM AccountHolder WHERE email = :aEmail")
	public AccountHolder accountholderMatchingEmail(@Param("aEmail") String email);
	
	@Query("FROM AccountHolder WHERE email = :aEmail AND accountholderId = :aId")
	public AccountHolder accountholderMatchingEmailAndId(@Param("aId") long aId,
			@Param("aEmail") String aEmail);

}
