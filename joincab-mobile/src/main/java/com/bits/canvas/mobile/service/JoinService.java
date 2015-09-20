package com.bits.canvas.mobile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.common.TripDto;
import com.bits.canvas.mobile.enums.TripStatus;
import com.bits.canvas.mobile.response.JoinResponse;
import com.bits.canvas.persistence.entity.TripDetails;
import com.bits.canvas.persistence.repo.PostDetailsRepo;
import com.bits.canvas.persistence.repo.TripDetailsRepo;
import com.bits.canvas.redis.connector.RedisConnector;

@Component
public class JoinService {

	@Autowired
	PostDetailsRepo postDetailsRepo;
	
	@Autowired
	TripDetailsRepo tripDetailsRepo;
	
	@Autowired
	RedisConnector redisConnector;
	
	/**
	 * Do the below in one single script request to avoid race conditions
	 * 1. Check if the post is still active
	 * 		get the no of seats available - If >=1, post is still active
	 * 2. If the post is active
	 * 		store a new cache in redis - (<tripId>, <tripDetails>)
	 * 3. 
	 * @param tripDetails
	 * @param postId
	 * @return
	 */
	public JoinResponse completeJoinRequest(TripDetails tripDetails){
		JoinResponse joinResponse = new JoinResponse();
		boolean postActive = isPostActive(tripDetails);
		if(postActive){
			//TODO - insert into DB in asycn way
			tripDetails = insertIntoTripDetails(tripDetails);
			joinResponse.setTripId(tripDetails.getTripId());
			if(postActive){
				joinResponse.setSuccess(true);
				//TODO - incerement join request cnt in the post details table
			}
			else{
				//TODO - delete from the trip details table 
				joinResponse.setSuccess(false);
			}
		}else{
			joinResponse.setSuccess(false);
		}
		return joinResponse;
	}
	
	/**
	 *TODO - use pipelining to club both the calls
	 * return true if the post can accept more join requests
	 * false otherwise
	 * @param postId
	 * @return
	 */
	public boolean isPostActive(TripDetails tripDetails){ 
		Integer value = redisConnector.getIntvalue(tripDetails.getPostId()+"Stat");
		if(value != null && value >= 1){
			/*
			 * put the trip details in the cache (<tripId>, <tripDetails>)
			 * Add/update the cache for the below
			 * 		(<hopId>trip, <listofPostId>)
			 * 		(<postId>trip, <listofhopid>)
			 * 		for the given hopId, append the postId 
			 * 		The above is needed to hide post data from the hopper, once he has requested join for the same.
			 */
			redisConnector.set(tripDetails.getId(), tripDetails);
			TripDto tripDto = new TripDto();
			tripDto.setItemId(tripDetails.getPostId());
			tripDto.setStatus(TripStatus.JOIN_REQUEST.getCode());
			redisConnector.addToList(tripDetails.getHopId()+"Trip", tripDto);
			TripDto tripDto2 = new TripDto();
			tripDto2.setItemId(tripDetails.getHopId());
			tripDto2.setStatus(TripStatus.JOIN_REQUEST.getCode());
			redisConnector.addToList(tripDetails.getPostId()+"Trip", tripDto2);
			return true;
		}
		else{
			return false;
		}
	}

	
	public TripDetails insertIntoTripDetails(TripDetails tripDetails){
		tripDetails = tripDetailsRepo.save(tripDetails);
		return tripDetails;
	}
}
