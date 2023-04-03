package com.jbritland.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "consumed_offer_id")
@JsonTypeName("postedOffer")
public class PostedConsumedOffer extends ConsumedOffer {
	
	String destinationAddress;
	String destinationCity;
	String destinationPostcode;

	public PostedConsumedOffer() {
		super();
	}

	public PostedConsumedOffer(int consumedOfferId, Reward reward, long accountholderId,
			String destinationAddress, String destinationCity, String destinationPostcode, 
			LocalDateTime dateClaimed) {
		super(consumedOfferId, reward, accountholderId, dateClaimed);
		this.destinationAddress = destinationAddress;
		this.destinationCity = destinationCity;
		this.destinationPostcode = destinationPostcode;
	}

	public PostedConsumedOffer(Reward reward, long accountholderId,
			String destinationAddress, String destinationCity, String destinationPostcode,
			LocalDateTime dateClaimed) {
		super(reward, accountholderId, dateClaimed);
		this.destinationAddress = destinationAddress;
		this.destinationCity = destinationCity;
		this.destinationPostcode = destinationPostcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public String getDestinationPostcode() {
		return destinationPostcode;
	}

	@Override
	public String toString() {
		return "PostedConsumedOffer [destinationAddress=" + destinationAddress + ", destinationCity="
				+ destinationCity + ", destinationPostcode=" + destinationPostcode + ", consumedOfferId="
				+ consumedOfferId + ", reward=" + reward + ", accountholderId=" + accountholderId + "]";
	}
	
	

}
