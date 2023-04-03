package com.jbritland.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jbritland.entities.ReversalEvent;
import com.jbritland.services.ReversalListService;

@RestController
public class ReversalListRestController {
	
	@Autowired
	ReversalListService rService;
	
	@GetMapping("/reversals/view-all")
	public List<ReversalEvent> getAllReversalEvents() {
		return rService.getAllReversalEvents();
	}
	
	@GetMapping("/reversal/by-transaction-number")
	public ReversalEvent getReversalByTransactionNumber(@RequestBody long transactionId) {
		return rService.getReversalByTransactionNumber(transactionId);
	}
	
	@GetMapping("/reversal/by-id")
	public ReversalEvent getReversalByReversalId(@RequestBody int reversalId) {
		return rService.getReversalByReversalId(reversalId);
	}

}
