package com.jbritland.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbritland.entities.ConsumedOffer;
import com.jbritland.entities.Reward;
import com.jbritland.repositories.ConsumedOfferRepository;
import com.jbritland.repositories.RewardRepository;

@Service
public class OffersServiceImpl implements OffersService {
	
	@Autowired
	private ConsumedOfferRepository coRepo;
	
	@Autowired
	private RewardRepository rRepo;

	@Override
	public List<Reward> viewRewardsByMaximumThreshold(int threshold) {
		return rRepo.getRewardsByMaxThreshold(threshold);
	}

	@Override
	public List<Reward> viewAllRewards() {
		return rRepo.findAll();
	}

	@Override
	public List<ConsumedOffer> viewConsumedOffersByAccountholderId(long accountholderId) {
		return coRepo.findByAccountholderId(accountholderId);
	}

	@Override
	public ConsumedOffer addConsumedOffer(ConsumedOffer consumedOffer) {
		consumedOffer.setReward(rRepo.findByRewardId(consumedOffer.getReward().getRewardId()));
		return consumedOffer = coRepo.save(consumedOffer);
	}

}
