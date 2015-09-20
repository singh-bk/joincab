package com.bits.canvas.mobile.transformer;

import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.common.HopDto;
import com.bits.canvas.mobile.enums.HopStatus;
import com.bits.canvas.mobile.request.HopRequest;
import com.bits.canvas.persistence.entity.HopDetails;
import com.bits.canvas.persistence.entity.UserProfile;

/**
 * 
 * @author Birendra K Singh
 *
 */
@Component
public class HopTransformer {

	/**
	 * 
	 * @param hopDetails
	 * @param hopRequest
	 * @return
	 */
	public HopDetails transform(HopDetails hopDetails, HopRequest hopRequest) {
		
		hopDetails.setUserId(hopRequest.getUserId());
		hopDetails.setCscId(hopRequest.getCscId());
		hopDetails.setSrcLat(hopRequest.getSrcLat());
		hopDetails.setSrcLng(hopRequest.getSrcLng());
		hopDetails.setSrcAddress(hopRequest.getSrcAddress()); // TODO - make sure its less than 500
		hopDetails.setSrcPlaceId(hopRequest.getSrcPlaceId());
		hopDetails.setDestLat(hopRequest.getDestLat());
		hopDetails.setDestLng(hopRequest.getDestLng());
		hopDetails.setDestAddress(hopRequest.getDestAddress());//TODO - less than 500
		hopDetails.setDestPlaceId(hopRequest.getDestPlaceId());
		hopDetails.setPps(hopRequest.getPps());
		hopDetails.setTravelTime(hopRequest.getTravelTime()); //TODO - make sure its epoch time
		hopDetails.setDestAddress(hopRequest.getDestAddress());
		hopDetails.setReqCnt(0);
		hopDetails.setStatus(HopStatus.INITIATED.getCode());
		hopDetails.setDenyCnt(0);
		hopDetails.setPickCnt(0);
		
		return hopDetails;
	}
	
	/**
	 * 
	 * @param hopDetails
	 * @return
	 */
	public HopDto transform(HopDetails hopDetails){
		HopDto hopDto = new HopDto();
		hopDto.setDestAddress(hopDetails.getDestAddress());
		hopDto.setHopId(hopDetails.getHopId());
		hopDto.setPps(hopDetails.getPps());
		hopDto.setSrcAddress(hopDetails.getSrcAddress());
		hopDto.setSrcLat(hopDetails.getSrcLat());
		hopDto.setSrcLng(hopDetails.getSrcLng());
		hopDto.setStatus(hopDetails.getStatus());
		hopDto.setTravelTime(hopDetails.getTravelTime());
		return hopDto;
	}
	
	/**
	 * 
	 * @param hopDto
	 * @param userProfile
	 * @return
	 */
	public HopDto appendUserProfile(HopDto hopDto, UserProfile userProfile){
		hopDto.setGender(userProfile.getGender());
		hopDto.setDisplayName(userProfile.getDisplayName());
		hopDto.setRating(5);//TODO
		hopDto.setUserId(userProfile.getUserId());
		hopDto.setUserImageUrl(userProfile.getImgUrl());
		return hopDto;
	}

}
