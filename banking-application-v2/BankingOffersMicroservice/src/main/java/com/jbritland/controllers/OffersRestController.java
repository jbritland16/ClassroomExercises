package com.jbritland.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jbritland.entities.ConsumedOffer;
import com.jbritland.entities.Reward;
import com.jbritland.services.OffersService;

@RestController
public class OffersRestController {
	
	@Autowired
	OffersService oService;
	
	@GetMapping("/rewards/browse-all")
	public List<Reward> viewAllRewards() {
		return oService.viewAllRewards();
	}
	
	@GetMapping("/rewards/available")
	public List<Reward> viewRewardsUnderThreshold(@RequestBody int threshold) {
		return oService.viewRewardsByMaximumThreshold(threshold);
	}
	
	@PostMapping("/rewards/claim-reward")
	public ConsumedOffer claimReward(@RequestBody ConsumedOffer consumedOffer) {
		return oService.addConsumedOffer(consumedOffer);
	}
	
	@GetMapping("/rewards/past-rewards")
	public List<ConsumedOffer> viewPastRewards(@RequestBody long accountholderId) {
		return oService.viewConsumedOffersByAccountholderId(accountholderId);
	}
	
}
