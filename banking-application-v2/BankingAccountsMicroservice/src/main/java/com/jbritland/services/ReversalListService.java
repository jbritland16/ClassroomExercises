package com.jbritland.services;

import java.util.List;

import com.jbritland.entities.ReversalEvent;

public interface ReversalListService {

	public List<ReversalEvent> getAllReversalEvents();
	public ReversalEvent getReversalByTransactionNumber(long transactionId);
	public ReversalEvent getReversalByReversalId(int reversalId);
	
}
