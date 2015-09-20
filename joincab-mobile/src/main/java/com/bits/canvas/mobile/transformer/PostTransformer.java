package com.bits.canvas.mobile.transformer;

import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.common.HopDto;
import com.bits.canvas.mobile.common.PostDto;
import com.bits.canvas.mobile.enums.PostStatus;
import com.bits.canvas.mobile.request.PostRequest;
import com.bits.canvas.persistence.entity.PostDetails;
import com.bits.canvas.persistence.entity.UserProfile;

@Component
public class PostTransformer {

	/**
	 * 
	 * @param postDetails
	 * @param postRequest
	 * @return
	 */
	public PostDetails transform(PostDetails postDetails, PostRequest postRequest) {
		
		postDetails.setUserId(postRequest.getUserId());
		postDetails.setCscId(postRequest.getCscId());
		postDetails.setSrcLat(postRequest.getSrcLat());
		postDetails.setSrcLng(postRequest.getSrcLng());
		postDetails.setSrcAddress(postRequest.getSrcAddress()); // TODO - make sure its less than 500
		postDetails.setSrcPlaceId(postRequest.getSrcPlaceId());
		postDetails.setDestLat(postRequest.getDestLat());
		postDetails.setDestLng(postRequest.getDestLng());
		postDetails.setDestAddress(postRequest.getDestAddress());//TODO - less than 500
		postDetails.setDestPlaceId(postRequest.getDestPlaceId());
		postDetails.setPps(postRequest.getPps());
		postDetails.setTravelTime(postRequest.getTravelTime()); //TODO - make sure its epoch time
		postDetails.setDestAddress(postRequest.getDestAddress());
		postDetails.setSeatsOffered(postRequest.getSeatsOffered());
		postDetails.setSeatsAvail(postRequest.getSeatsOffered());//initial seats available will be equal to seats offered
		postDetails.setReqCnt(0);//Initial Request count has to be 0.
		postDetails.setStatus(PostStatus.INITIATED.getCode());
		postDetails.setDenyCnt(0);
		postDetails.setJoinCnt(0);
		
		return postDetails;
	}

	/**
	 * 
	 * @param postDetails
	 * @return
	 */
	public PostDto transform(PostDetails postDetails){
		PostDto postDto = new PostDto();
		postDto.setDestAddress(postDetails.getDestAddress());
		postDto.setPostId(postDetails.getPostId());
		postDto.setPps(postDetails.getPps());
		postDto.setSrcAddress(postDetails.getSrcAddress());
		postDto.setSrcLat(postDetails.getSrcLat());
		postDto.setSrcLng(postDetails.getSrcLng());
		postDto.setStatus(postDetails.getStatus());
		postDto.setTravelTime(postDetails.getTravelTime());
		return postDto;
	}
	

	/**
	 * 
	 * @param postDto
	 * @param userProfile
	 * @return
	 */
	public PostDto appendUserProfile(PostDto postDto, UserProfile userProfile){
		postDto.setGender(userProfile.getGender());
		postDto.setDisplayName(userProfile.getDisplayName());
		postDto.setRating(5);//TODO
		postDto.setUserId(userProfile.getUserId());
		postDto.setUserImageUrl(userProfile.getImgUrl());
		postDto.setVehicleNo(userProfile.getVehNum());
		postDto.setVehicleType(userProfile.getVehType());
		return postDto;
	}
}
