package com.jbritland.entities;

public class RewardException extends Exception {
	
	int thresholdCrossed;

	public RewardException(int thresholdCrossed) {
		super();
		this.thresholdCrossed = thresholdCrossed;
	}

	public int getThresholdCrossed() {
		return this.thresholdCrossed;
	}
	
}
