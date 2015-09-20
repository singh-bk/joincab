package com.bits.canvas.mobile.transformer;

import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.request.JoinRequest;
import com.bits.canvas.persistence.entity.TripDetails;

@Component
public class TripTransformer {


	public TripDetails transform(TripDetails tripDetails, JoinRequest joinRequest, Integer tripStatus) {
		
		tripDetails.setHopId(joinRequest.getHopId());
		tripDetails.setPostId(joinRequest.getPostId());
		tripDetails.setOpType(joinRequest.getRequestType());
		tripDetails.setPrice(joinRequest.getPrice());
		tripDetails.setStatus(tripStatus);
		
		return tripDetails;
	}

}
