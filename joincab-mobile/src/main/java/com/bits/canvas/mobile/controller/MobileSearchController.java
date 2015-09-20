package com.bits.canvas.mobile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.bits.canvas.mobile.helper.SearchHelper;
import com.bits.canvas.mobile.request.RideSearchRequest;
import com.bits.canvas.mobile.response.RideSearchResponse;
import com.bits.canvas.persistence.entity.HopDetails;
import com.bits.canvas.persistence.entity.PostDetails;
import com.bits.canvas.persistence.entity.UserProfile;
import com.bits.canvas.redis.connector.RedisConnector;

/**
 * 
 * @author bk
 *
 */
@Controller
@RequestMapping(value=CommonConstants.MOBILE_SEARCH_PREFIX)
public class MobileSearchController {

	@Autowired
	SearchHelper searchHelper;
	
	@Autowired
	RedisConnector redisConnector;
	
	/**
	 * Get list of all rides - hop + post 
	 * Dont query the db - Get all data from redis cache
	 * 1. Get all post data
	 * 		Find all post data which lies between the range of 15 mins from start time
	 * 		Both before and after
	 * 2. Get all the hop data
	 * 		in a manner similar to above
	 * 			
	 * @param rideSearchRequest
	 * @return
	 */
	@RequestMapping(value="/rides",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RideSearchResponse getAllRides(@RequestBody RideSearchRequest rideSearchRequest) {
		RideSearchResponse rideSearchResponse = new RideSearchResponse();
		//searchHelper.getAllHopAndPost("BLR-KA-IN"); - NO more calls to db anymore
		//Get all the data from cache. fair to assume redis will respond.
		List<PostDto> postDtoList = fetchPostData(rideSearchRequest);
		List<HopDto> hopDtoList = fetchHopData(rideSearchRequest);
		rideSearchResponse.setPostList(postDtoList);
		rideSearchResponse.setHopList(hopDtoList);
		
		return rideSearchResponse;
	}
	
	/**
	 * postCd is 1
	 * cscId for BLR-KA-IN is 000001
	 * date will be in format 1511221000 - 2015 Nov 22 10:00 am
	 * @param postCd
	 * @param cscId
	 * @param date
	 * @return
	 */
	private List<PostDto> fetchPostData(RideSearchRequest rideSearchRequest){
		
		/*
		 * If postId is not null -
		 * 		 means the user has made a post request
		 * 		Do not other post requests to him. 
		 */
		String postId = rideSearchRequest.getPostId();
		if(postId != null && postId.length()>0){
			return null;
		}
		String hopId = rideSearchRequest.getHopId();
		String postCd = "1";
		String cscId = "000001";
		Long date = rideSearchRequest.getTravelTime();
		List<String> postIdList = new ArrayList<String>();
		List<PostDto> postDtoList = new ArrayList<PostDto>();
		
		//Get the time range
		String keyStart = postCd+cscId+(subtractFromDate(date));
		String keyEnd = postCd+cscId+(addToDate(date));		
		Long start = new Long(keyStart);
		Long end = new Long(keyEnd);
		
		//prepare a key list with time range
		List<String> postKeys = new ArrayList<String>();
		
		//Get the list of all postIds that the hopper has already send a join trip request to.
		List<String> joinTripIds = redisConnector.getAll(hopId+"Trip");
		
		for(long i=start; i<=end;i++){
			if(i%100==60){i=i+40;}
			//postKeys.add(i+"");
			//TODO - Change it to do a single call to redis via pipeline
			List<String> list = redisConnector.getAll(i+"");
			postIdList.addAll(list);
		}
		
		/*
		 * Make another redis call to get the complete post details with postId as the input
		 * fair to assume that redis will contain all the data and no call to db is required in case of failure.
		 * TODO - implement call to db in next iteration 
		 */
		List<PostDetails> postDetailsList = (List<PostDetails>)(Object)redisConnector.getObjectValueList(postIdList);
		
		/*
		 * Convert a list of post details to a list of postDto 
		 * which lies within the same source and same destination.
		 */
		postDetailsList = searchHelper.applyDistanceFilter(postDetailsList, 
				rideSearchRequest.getSrcLat(), rideSearchRequest.getSrcLng(), rideSearchRequest.getDestLat(), rideSearchRequest.getDestLng());
		/*
		 * Get the user profile info for the all the users from the cache.
		 */
		List<String> userIdList = new ArrayList<String>();
		for(PostDetails postDetails: postDetailsList){
			userIdList.add(postDetails.getUserId());
		}
		//Get all values in one call to redis
		List<UserProfile> userProfileList = (List<UserProfile>)(Object)redisConnector.getObjectValueList(userIdList);
		Map<String, UserProfile> userProfileListMap = new HashMap<String, UserProfile>();
		int i=0;
		for(UserProfile userProfile: userProfileList){
			String userId = userIdList.get(i);
			userProfileListMap.put(userId, userProfile);
			i++;
		}
		postDtoList = searchHelper.transform(postDetailsList, userProfileListMap, joinTripIds);
		//TODO - sort is based on travel time and source distance
		return postDtoList;
	}
	
	
	/**
	 * hopCd is 2
	 * cscId for BLR-KA-IN is 000001
	 * date will be in format 1511221000 - 2015 Nov 22 10:00 am
	 * @param postCd
	 * @param cscId
	 * @param date
	 * @return
	 */
	private List<HopDto> fetchHopData(RideSearchRequest rideSearchRequest){
		
		/*
		 * If hopId is not null -
		 * 		 means the user has made a hop request
		 * 		Do not other hop requests to him. 
		 */
		String hopId = rideSearchRequest.getHopId();
		if(hopId != null && hopId.length()>0){
			return null;
		}
		String postId = rideSearchRequest.getPostId();
		
		String hopCd = "2";
		String cscId = "000001";
		Long date = rideSearchRequest.getTravelTime();
		List<String> hopIdList = new ArrayList<String>();
		List<HopDto> hopDtoList = new ArrayList<HopDto>();
		
		//Get the time range
		String keyStart = hopCd+cscId+(subtractFromDate(date));
		String keyEnd = hopCd+cscId+(addToDate(date));
		
		Long start = new Long(keyStart);
		Long end = new Long(keyEnd);
		
		//prepare a key list with time range
		List<String> hopKeys = new ArrayList<String>();
		
		/*
		 * Get the list of all hopIds that the poster has already send a join trip request to.
		 * The List will also contain all hopIds that have sent a re
		 */
	    List<String> pickTripIds = redisConnector.getAll(postId+"Trip");
	    
	    
		for(long i=start; i<=end;i++){
			if(i%100==60){i=i+40;}
			//hopKeys.add(i+"");
			//TODO - Change it to do a single call to redis via pipeline
			List<String> list = redisConnector.getAll(i+"");
			hopIdList.addAll(list);
		}
		
		/*
		 * Make another redis call to get the complete hop details with hopId as the input
		 * fair to assume that redis will contain all the data and no call to db is required in case of failure.
		 * TODO - implement call to db in next iteration 
		 */
		List<HopDetails> hopDetailsList = (List<HopDetails>)(Object)redisConnector.getObjectValueList(hopIdList);
		/*
		 * 
		 * Convert a list of hop details to a list of hopDto
		 * which lies within the same source and same destination.
		 */
		hopDetailsList = searchHelper.applyDistanceFilter2(hopDetailsList, 
				rideSearchRequest.getSrcLat(), rideSearchRequest.getSrcLng(), rideSearchRequest.getDestLat(), rideSearchRequest.getDestLng());
		
		List<String> userIdList = new ArrayList<String>();
		for(HopDetails hopDetails: hopDetailsList){
			userIdList.add(hopDetails.getUserId());
		}
		List<UserProfile> userProfileList = (List<UserProfile>)(Object)redisConnector.getObjectValueList(userIdList);
		Map<String, UserProfile> userProfileListMap = new HashMap<String, UserProfile>();
		int i=0;
		for(UserProfile profile: userProfileList){
			String userId = userIdList.get(i);
			userProfileListMap.put(userId, profile);
			i++;
		}
		hopDtoList = searchHelper.transformToHopDto(hopDetailsList, userProfileListMap, pickTripIds);
		//TODO - sort is based on travel time and source distance
		return hopDtoList;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	private long subtractFromDate(long date){
		long hh = date/100;
		long mm = date%100;
		if(mm<15){
			mm=60-mm;
			hh--;
		}
		else{
			mm = mm-15;
		}
		return hh*100+mm;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	private long addToDate(long date){
		long hh = date/100;
		long mm = date%100;
		if(mm<45){
			mm=mm-45;
			hh++;
		}
		else{
			mm = mm+15;
		}
		return hh*100+mm;
	}
}
