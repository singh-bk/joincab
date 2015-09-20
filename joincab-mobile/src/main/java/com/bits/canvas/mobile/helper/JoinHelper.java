package com.bits.canvas.mobile.helper;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.request.JoinRequest;
import com.bits.canvas.mobile.transformer.TripTransformer;
import com.bits.canvas.persistence.entity.TripDetails;

@Component
public class JoinHelper {

	
	@Autowired
	TripTransformer tripTransformer;
	
	/**
	 * 
	 * @param joinRequest
	 * @param tripStatus
	 * @return
	 */
	public TripDetails populateTripDetails(JoinRequest joinRequest, Integer tripStatus){
		TripDetails tripDetails = new TripDetails();
		tripDetails.setTripId(UUID.randomUUID().toString());
		tripDetails = tripTransformer.transform(tripDetails, joinRequest, tripStatus);
		return tripDetails;
	}
}
