package com.jbritland.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "consumed_offer_id")
@JsonTypeName("digitalOffer")
public class DigitalConsumedOffer extends ConsumedOffer {
	
	String destinationEmail;

	public DigitalConsumedOffer() {
		super();
	}

	public DigitalConsumedOffer(int consumedOfferId, Reward reward, long accountholderId, 
			String destinationEmail, LocalDateTime dateClaimed) {
		super(consumedOfferId, reward, accountholderId, dateClaimed);
		this.destinationEmail = destinationEmail;
	}

	public DigitalConsumedOffer(Reward reward, long accountholderId, String destinationEmail, LocalDateTime dateClaimed) {
		super(reward, accountholderId, dateClaimed);
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	@Override
	public String toString() {
		return "DigitalConsumedOffer [destinationEmail=" + destinationEmail + ", consumedOfferId=" + consumedOfferId
				+ ", reward=" + reward + ", accountholderId=" + accountholderId + "]";
	}

	
	
}
