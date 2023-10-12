package com.jbritland.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jbritland.entities.ConsumedOffer;

@Repository
public interface ConsumedOfferRepository extends JpaRepository<ConsumedOffer, Integer> {

	public List<ConsumedOffer> findByAccountholderId(long accountholderId);
	public ConsumedOffer findByConsumedOfferId(int consumedOfferId);
	
}
