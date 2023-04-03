package com.jbritland.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jbritland.entities.Reward;

public interface RewardRepository extends JpaRepository<Reward, Long> {
	
	@Query("FROM Reward WHERE requiredThreshold <= :threshold")
	public List<Reward> getRewardsByMaxThreshold(@Param("threshold") int threshold);
	
	public Reward findByRewardId(long rewardId);

}
