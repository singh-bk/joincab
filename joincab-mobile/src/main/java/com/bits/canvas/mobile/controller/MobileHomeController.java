package com.bits.canvas.mobile.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bits.canvas.mobile.common.CommonConstants;
import com.bits.canvas.mobile.common.HopDto;
import com.bits.canvas.mobile.common.PostDto;
import com.bits.canvas.mobile.common.TripDto;
import com.bits.canvas.mobile.enums.TripType;
import com.bits.canvas.mobile.request.JourneyDetailsRequest;
import com.bits.canvas.mobile.request.JourneyStartRequest;
import com.bits.canvas.mobile.response.PostResponse;
import com.bits.canvas.mobile.response.RideSearchResponse;
import com.bits.canvas.mobile.transformer.HopTransformer;
import com.bits.canvas.mobile.transformer.PostTransformer;
import com.bits.canvas.persistence.entity.HopDetails;
import com.bits.canvas.persistence.entity.PostDetails;
import com.bits.canvas.redis.connector.RedisConnector;

@Controller
@RequestMapping(value=CommonConstants.MOBILE_HOME_PREFIX)
public class MobileHomeController {

	public static final Logger LOG = Logger.getLogger(MobileHomeController.class);

	@Autowired
	RedisConnector redisConnector;
	
	@Autowired
	PostTransformer postTransformer;
	
	@Autowired
	HopTransformer hopTransformer;
	
	/**
	 * Will be initiated by the post user
	 * Steps
	 * 	1. get the confirmed joined hoppers for the given postid
	 *  2. update DB status for the all
	 *  	a. - change post details to started
	 *  	b. - change hop details to started
	 *  	c. - change trip details to started
	 *  3. send a gcm notification to the hoppers to start
	 *  	the notification will contain buttons to confirm and cancel
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/load",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PostResponse loadHome(@RequestBody JourneyStartRequest request) {
		PostResponse postResponse = new PostResponse();
		return postResponse;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/details",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RideSearchResponse loadJourneyDetails(@RequestBody JourneyDetailsRequest request){
		RideSearchResponse response = new RideSearchResponse();
		List<HopDto> hopDtoList = new ArrayList<HopDto>();
		List<PostDto> postDtoList = new ArrayList<PostDto>();
		
		String itemId = request.getItemId();		
		int opType = request.getOpType();
		List<TripDto> tripDtoList = (List)redisConnector.getAllObjects(itemId+"Trip");
		/*
		 * In case of HOP request
		 * 		the join/pickup request is confirmed
		 * 		the join and or pick up request is not confirmed
		 */
		if(opType == TripType.HOP.getCode()){
			//TODO - use pipelining here to get all requests in one single call.
			for(TripDto tripDto: tripDtoList){
				String postId = tripDto.getItemId();
				try{
					PostDetails postDetails = (PostDetails)redisConnector.getObjectValue(postId);
					PostDto postDto = postTransformer.transform(postDetails);
					postDtoList.add(postDto);
				}catch(Exception ex){
					//TODO - handle test data. remove in production
				}
			}
		}else if(opType == TripType.POST.getCode()){
			//TODO - use pipelining here to get all requests in one single call.
			for(TripDto tripDto: tripDtoList){
				String hopId = tripDto.getItemId();
				try{
				HopDetails hopDetails = (HopDetails)redisConnector.getObjectValue(hopId);
				HopDto hopDto = hopTransformer.transform(hopDetails);
				hopDtoList.add(hopDto);
				}catch(Exception ex){
					//TODO - handle test data. remove in production
				}
			}
		}
		response.setHopList(hopDtoList);
		response.setPostList(postDtoList);
		return response;
	}
}
