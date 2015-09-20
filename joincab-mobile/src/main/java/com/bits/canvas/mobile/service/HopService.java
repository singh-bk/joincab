package com.bits.canvas.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.persistence.entity.HopDetails;
import com.bits.canvas.persistence.entity.UserProfile;
import com.bits.canvas.persistence.repo.HopDetailsRepo;
import com.bits.canvas.persistence.repo.UserProfileRepo;
import com.bits.canvas.redis.connector.RedisConnector;

@Component
public class HopService {

	@Autowired
	HopDetailsRepo hopDetailsRepo;
	
	@Autowired
	RedisConnector redisConnector;
	
	@Autowired
	UserProfileRepo userProfileRepo;

	/**
	 * Save the hop details to redis
	 * Check if the user profile is present in the cache
	 * 		if not, fetch from db and put it in cache.
	 * 
	 * Following values goes into the cache
	 * 		1. key-hopId, value-postDetails
	 * 		2. key-hopId appended with Stat, value - status(here it will initiated/1)
	 * 				This is needed to quickly determine the status of the postId and take action upon join/pickup
	 * 		3. key-emailid, value-userprofile
	 * @param hopDetails
	 */
	public void saveHopDetails(HopDetails hopDetails){
		//TODO - make this multiple calls via pipeline
		redisConnector.set(hopDetails.getHopId(), hopDetails);
		redisConnector.set(hopDetails.getHopId()+"Stat", 1);//No of seats requested by hopper is always 1 - TODO make it user enetered at a later date
		UserProfile profile = (UserProfile)redisConnector.getObjectValue(hopDetails.getUserId());
		if(profile == null){
			profile = userProfileRepo.findOne(hopDetails.getUserId());
			redisConnector.set(hopDetails.getUserId(), profile);
		}
		//TODO - move this save to the DB in an async way
		hopDetailsRepo.save(hopDetails);
	}
	
	public List<HopDetails> getHopByCscIdAndStatus(String cscId, Integer status){
		
		List<HopDetails> hopList = hopDetailsRepo.findByCscIdAndStatus(cscId, status);
		return hopList;
	}
}
