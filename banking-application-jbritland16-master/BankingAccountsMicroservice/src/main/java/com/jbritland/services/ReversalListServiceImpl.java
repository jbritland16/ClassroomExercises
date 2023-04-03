package com.jbritland.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbritland.entities.ReversalEvent;
import com.jbritland.repositories.ReversalRepository;

@Service
public class ReversalListServiceImpl implements ReversalListService {
	
	@Autowired
	private ReversalRepository rRepo;

	@Override
	public List<ReversalEvent> getAllReversalEvents() {
		return rRepo.findAll();
	}

	@Override
	public ReversalEvent getReversalByTransactionNumber(long transactionId) {
		return rRepo.getReversalByTransactionNumber(transactionId);
	}

	@Override
	public ReversalEvent getReversalByReversalId(int reversalId) {
		return rRepo.getReferenceById(reversalId);
	}

}
