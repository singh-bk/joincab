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
public class PickService {

	@Autowired
	PostDetailsRepo postDetailsRepo;
	
	@Autowired
	TripDetailsRepo tripDetailsRepo;
	
	@Autowired
	RedisConnector redisConnector;
	
	/**
	 * Do the below in one single script request to avoid race conditions
	 * 1. Check if the hop is still active
	 * 		get the no of seats available - If >=1, post is still active. Ideally it should be always 1
	 * 2. If the hop is active
	 * 		store a new cache in redis - (<tripId>, <tripDetails>)
	 * 		also - <postId>, <list of hopId> - see below for explanation
	 * 3. 
	 * @param tripDetails
	 * @param postId
	 * @return
	 */
	public JoinResponse completePickRequest(TripDetails tripDetails){
		JoinResponse joinResponse = new JoinResponse();
		boolean postActive = isHopActive(tripDetails);
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
	 * return true if the hop can accept more pick requests
	 * false otherwise
	 * @param postId
	 * @return
	 */
	public boolean isHopActive(TripDetails tripDetails){ 
		Integer value = redisConnector.getIntvalue(tripDetails.getHopId()+"Stat");
		if(value != null && value >= 1){
			/*
			 * put the trip details in the cache (<tripId>, <tripDetails>)
			 * Add/update the cache for the below
			 * 		(<postId>trip, <listofHopId>)
			 * 		for the given postId, append the hopId 
			 * 		The above is needed to hide hop data from the post initiator, once he has requested pick for the same.
			 */
			redisConnector.set(tripDetails.getId(), tripDetails);
			TripDto tripDto = new TripDto();
			tripDto.setItemId(tripDetails.getHopId());
			tripDto.setStatus(TripStatus.PICK_REQUEST.getCode());
			redisConnector.addToList(tripDetails.getPostId()+"Trip", tripDto);
			TripDto tripDto2 = new TripDto();
			tripDto2.setItemId(tripDetails.getPostId());
			tripDto2.setStatus(TripStatus.PICK_REQUEST.getCode());
			redisConnector.addToList(tripDetails.getHopId()+"Trip", tripDto2);
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
