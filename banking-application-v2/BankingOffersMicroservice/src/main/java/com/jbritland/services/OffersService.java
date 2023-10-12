package com.jbritland.services;

import java.util.List;

import com.jbritland.entities.ConsumedOffer;
import com.jbritland.entities.Reward;

public interface OffersService {
	
	public List<Reward> viewRewardsByMaximumThreshold(int threshold);
	public List<Reward> viewAllRewards();
	public List<ConsumedOffer> viewConsumedOffersByAccountholderId(long accountholderId);
	public ConsumedOffer addConsumedOffer(ConsumedOffer consumedOffer);
	
}
