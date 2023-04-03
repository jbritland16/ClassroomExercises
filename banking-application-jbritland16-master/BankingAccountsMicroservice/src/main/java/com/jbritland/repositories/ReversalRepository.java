package com.jbritland.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jbritland.entities.ReversalEvent;

@Repository
public interface ReversalRepository extends JpaRepository<ReversalEvent, Integer> {
	
	@Query(value = "SELECT * FROM reversal_event WHERE transaction_id = :tId", nativeQuery = true)
	public ReversalEvent getReversalByTransactionNumber(@Param("tId") long transactionId);

}
