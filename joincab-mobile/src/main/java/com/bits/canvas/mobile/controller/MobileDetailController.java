package com.bits.canvas.mobile.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bits.canvas.mobile.common.CommonConstants;
import com.bits.canvas.mobile.common.HopDto;
import com.bits.canvas.mobile.common.PostDto;
import com.bits.canvas.mobile.common.TripDto;
import com.bits.canvas.mobile.transformer.HopTransformer;
import com.bits.canvas.mobile.transformer.PostTransformer;
import com.bits.canvas.persistence.entity.HopDetails;
import com.bits.canvas.persistence.entity.PostDetails;
import com.bits.canvas.persistence.entity.UserProfile;
import com.bits.canvas.redis.connector.RedisConnector;

/**
 * 
 * @author Birendra K Singh
 *
 */
@Controller
@RequestMapping(value=CommonConstants.MOBILE_DETAIL_PREFIX)
public class MobileDetailController {

	public static final Logger LOG = Logger.getLogger(MobileDetailController.class);
	
	@Autowired
	RedisConnector redisConnector;
	
	@Autowired
	HopTransformer hopTransformer;
	
	@Autowired
	PostTransformer postTransformer;
	
	/**
	 * Get the hop item details from the cache - key is hopId
	 * Check if the hop and post are joined or requested for join/pick
	 * 		fetch from the redis cache - <hopId>Trip.
	 * 		if the value contains postId, set status as fetched.
	 * @param itemId
	 * @param userId
	 * @param opType
	 * @return
	 */
	@RequestMapping(value="/hop",method=RequestMethod.GET)
	@ResponseBody
	public HopDto loadHopDetail(@RequestParam String hopId, @RequestParam String postId) {
		
		HopDetails hopDetails = (HopDetails)redisConnector.getObjectValue(hopId);
		HopDto hopDto = hopTransformer.transform(hopDetails);
		//get user profile from redis
		UserProfile userProfile = (UserProfile)redisConnector.getObjectValue(hopDetails.getUserId());
		//populate missing data in hotDto from userProfile
		hopDto = hopTransformer.appendUserProfile(hopDto, userProfile);
		
		List<TripDto> tripDtoList = (List)redisConnector.getAllObjects(hopId+"Trip");
		for(TripDto tripDto: tripDtoList){
			if(tripDto.getItemId().equals(postId)){
				hopDto.setTripStatus(tripDto.getStatus());
				break;
			}
		}
		
		return hopDto;
	}
	
	/**
	 * Get the post item details from the cache - key is postId
	 * Check if the hop and post are joined or requested for join/pick
	 * 		fetch from the redis cache - <postId>Trip.
	 * 		if the value contains hopId, set status as fetched.
	 * @param postId
	 * @param hopId
	 * @return
	 */
	@RequestMapping(value="/post",method=RequestMethod.GET)
	@ResponseBody
	public PostDto loadPostDetail(@RequestParam String postId, @RequestParam String hopId) {
		
		PostDetails postDetails = (PostDetails)redisConnector.getObjectValue(postId);
		PostDto postDto = postTransformer.transform(postDetails);
		//get user profile from redis
		UserProfile userProfile = (UserProfile)redisConnector.getObjectValue(postDetails.getUserId());
		//populate missing data in hotDto from userProfile
		postDto = postTransformer.appendUserProfile(postDto, userProfile);
		
		List<TripDto> tripDtoList = (List)redisConnector.getAllObjects(postId+"Trip");
		for(TripDto tripDto: tripDtoList){
			if(tripDto.getItemId().equals(hopId)){
				postDto.setTripStatus(tripDto.getStatus());
				break;
			}
		}
		
		return postDto;
	}
	/**
	 * itemId is the hop/post Id
	 * type indicates whether hop or post id.
	 * 		0- hop, 1-post
	 * If it is a postId
	 * 		If the status of postId is JOINED
	 * 			show all the hops confirmed
	 * 		If the post status is PARTIAL_JOIN or INITIATED
	 * 			show all the hops confirmed
	 * 			show all the hops pending
	 * 				show hop requested by the user - with an option to delete
	 * 				show join requested to this user by other hop users - with an option to confirm/deny
	 * If it is a hopId
	 * 		If the status of hopId is JOINED
	 * 			show the postId against which it is joined.
	 * 		If the status is INITIATED
	 * 			show all the requests
	 * @param itemId
	 * @param type
	 */
	@RequestMapping(value="/journey",method=RequestMethod.GET)
	@ResponseBody
	public void loadJourneyDetail(@RequestParam String itemId, @RequestParam Integer type){
		
	}
}
