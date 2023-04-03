package com.jbritland.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Embeddable
public class Reward {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable=false)
	long rewardId;
	
	public enum RewardType {
		DIGITAL,
		POSTED
	}
	
	private RewardType rewardType;
	String rewardTitle;
	String rewardDescription;
	int requiredThreshold;
	
	public Reward() {
		super();
	}

	public Reward(long rewardId, RewardType rewardType, String rewardTitle, String rewardDescription, int requiredThreshold) {
		super();
		this.rewardId = rewardId;
		this.rewardType = rewardType;
		this.rewardTitle = rewardTitle;
		this.rewardDescription = rewardDescription;
		this.requiredThreshold = requiredThreshold;
	}

	public Reward(RewardType rewardType, String rewardTitle, String rewardDescription, int requiredThreshold) {
		super();
		this.rewardType = rewardType;
		this.rewardTitle = rewardTitle;
		this.rewardDescription = rewardDescription;
		this.requiredThreshold = requiredThreshold;
	}

	public long getRewardId() {
		return rewardId;
	}

	public String getRewardTitle() {
		return rewardTitle;
	}

	public String getRewardDescription() {
		return rewardDescription;
	}

	public int getRequiredThreshold() {
		return requiredThreshold;
	}

	@Override
	public String toString() {
		return "Reward [rewardId=" + rewardId + ", rewardTitle=" + rewardTitle + ", rewardDescription="
				+ rewardDescription + ", requiredThreshold=" + requiredThreshold + "]";
	}

	public RewardType getRewardType() {
		return rewardType;
	}

}
