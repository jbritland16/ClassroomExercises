package com.jbritland.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "typeName")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonSubTypes({ @Type(PostedConsumedOffer.class), @Type(DigitalConsumedOffer.class) })
@JsonIdentityInfo(
		   generator = ObjectIdGenerators.PropertyGenerator.class,
		   property = "consumedOfferId")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ConsumedOffer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int consumedOfferId;
	
	@JoinColumn(name = "reward_id")
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	Reward reward;
	
	long accountholderId;
	LocalDateTime dateClaimed;

	public ConsumedOffer() {
		super();
	}
	
	public ConsumedOffer(int consumedOfferId, Reward reward, long accountholderId, LocalDateTime dateClaimed) {
		super();
		this.consumedOfferId = consumedOfferId;
		this.reward = reward;
		this.accountholderId = accountholderId;
		this.dateClaimed = dateClaimed;
	}

	public ConsumedOffer(Reward reward, long accountholderId, LocalDateTime dateClaimed) {
		super();
		this.reward = reward;
		this.accountholderId = accountholderId;
		this.dateClaimed = dateClaimed;
	}

	public int getConsumedOfferId() {
		return consumedOfferId;
	}

	public Reward getReward() {
		return reward;
	}
	
	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public long getAccountholderId() {
		return accountholderId;
	}
	
	public LocalDateTime getDateClaimed() {
		return dateClaimed;
	}

	@Override
	public String toString() {
		return "ConsumedOffer [consumedOfferId=" + consumedOfferId + ", reward=" + reward + ", accountholderId="
				+ accountholderId + "]";
	}
		
}
