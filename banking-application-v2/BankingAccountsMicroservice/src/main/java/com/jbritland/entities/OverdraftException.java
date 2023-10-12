package com.jbritland.entities;

public class OverdraftException extends Exception {
	
	private ReversalEvent reversalEvent;
	
	public OverdraftException(String message, ReversalEvent reversalEvent) {
		super(message);
		this.reversalEvent = reversalEvent;
	}
	
	public ReversalEvent getReversalEvent() {
		return this.reversalEvent;
	}

}
