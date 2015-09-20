package com.bits.canvas.mobile.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bits.canvas.mobile.common.CommonConstants;
import com.bits.canvas.mobile.enums.TripStatus;
import com.bits.canvas.mobile.helper.GcmHelper;
import com.bits.canvas.mobile.helper.HopHelper;
import com.bits.canvas.mobile.helper.JoinHelper;
import com.bits.canvas.mobile.helper.PostHelper;
import com.bits.canvas.mobile.request.ConfirmRequest;
import com.bits.canvas.mobile.request.HopRequest;
import com.bits.canvas.mobile.request.JoinRequest;
import com.bits.canvas.mobile.request.PostRequest;
import com.bits.canvas.mobile.response.ConfirmResponse;
import com.bits.canvas.mobile.response.GcmResponse;
import com.bits.canvas.mobile.response.HopResponse;
import com.bits.canvas.mobile.response.JoinResponse;
import com.bits.canvas.mobile.response.PostResponse;
import com.bits.canvas.mobile.service.AuthService;
import com.bits.canvas.mobile.service.GcmService;
import com.bits.canvas.mobile.service.HopService;
import com.bits.canvas.mobile.service.JoinService;
import com.bits.canvas.mobile.service.PickService;
import com.bits.canvas.mobile.service.PostService;
import com.bits.canvas.persistence.entity.HopDetails;
import com.bits.canvas.persistence.entity.PostDetails;
import com.bits.canvas.persistence.entity.TripDetails;
import com.bits.canvas.redis.connector.RedisConnector;

@Controller
@RequestMapping(value=CommonConstants.MOBILE_REQUEST_PREFIX)
public class MobileReqController {

	@Autowired
	HopHelper hopHelper;
	
	@Autowired
	HopService hopService;

	@Autowired
	PostHelper postHelper;
	
	@Autowired
	PostService postService;
	
	@Autowired
	JoinHelper joinHelper;
	
	@Autowired
	JoinService joinService;
	
	@Autowired
	PickService pickService;
	
	@Autowired
	GcmService gcmService;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	GcmHelper gcmUtil;
	
	@Autowired
	RedisConnector redisConnector;
	
	public static final Logger LOG = Logger.getLogger(MobileReqController.class);

	/**
	 * Add the post request to the redis cache as well
	 * 1. Add the post data to a cache with key as postid and value as postdetails
	 * 2. Append the postid to a list of post for that particular time and cscId
	 * 		Ex: key - 1000001201507221000
	 * 					1-Post
	 * 					000001 - CSCID for BLR-KA-IN
	 * 					1507221000 - stands for 2015-07-22-10:00 date and time
	 * @param postRequest
	 * @return
	 */
	@RequestMapping(value="/post",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PostResponse postRide(@RequestBody PostRequest postRequest) {
		PostResponse postResponse = new PostResponse();
		PostDetails postDetails = postHelper.populatePostDetails(postRequest);
		postService.savePostDetails(postDetails);
		postResponse.setPostId(postDetails.getPostId());
		String key = "1000001"+postRequest.getTravelTime();
		String postId = postDetails.getPostId();
		redisConnector.addToList(key, postId);
		//TODO - send gcm response as well
		return postResponse;
	}


	/**
	 * Add the hop request to the redis cache as well
	 * 1. Add the hop data to a cache with key as hopid and value as hopdetails
	 * 2. Append the hopid to a list of hop for that particular time and cscId
	 * 		Ex: key - 2000001201507221000
	 * 					1-indicates hop
	 * 					000001 - CSCID for BLR-KA-IN
	 * 					1507221000 - stands for 2015-07-22-10:00 date and time
	 * @param hopRequest
	 * @return
	 */
	@RequestMapping(value="/hop",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HopResponse hopRide(@RequestBody HopRequest hopRequest) {
		HopResponse hopResponse = new HopResponse();
		HopDetails hopDetails = hopHelper.populateHopDetails(hopRequest);
		hopService.saveHopDetails(hopDetails);
		String key = "2000001"+hopRequest.getTravelTime();
		String value = hopDetails.getHopId();
		redisConnector.addToList(key, value);

		hopResponse.setHopId(hopDetails.getHopId());
		//TODO - send gcm response as well
		return hopResponse;
	}


	/**
	 * API is called when a hopper sends a request to a poster
	 * 	In such a scenario the price quoted by poster will be valid
	 * 	
	 * 	Steps
	 * 		1. Check if the poster status is still Initiated/Partial
	 * 		2. If yes
	 * 				make a new entry into the journey table
	 * 				send a gcm notification to the poster for confirm/deny
	 * 				send a message to the hopper - "Request sent successfully".
	 * 		3. If No
	 * 				send a denial response - "not able to join. try another post"
	 * @param joinRequest
	 * @return
	 */
	@RequestMapping(value="/join",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JoinResponse joinRide(@RequestBody JoinRequest joinRequest) {
		//Use the postid and hopid to update the status in the DB/cache. 
		JoinResponse joinResponse = new JoinResponse();
		TripDetails tripDetails = joinHelper.populateTripDetails(joinRequest, TripStatus.JOIN_REQUEST.getCode());
		joinResponse = joinService.completeJoinRequest(tripDetails);
		boolean success = joinResponse.isSuccess();
		/*
		 * Trigger a notification via gcm to the poster for confirm/deny
		 */
		if(success){
			String gcmRegId = authService.getGcmRegId(joinRequest.getRecipientid());
			String gcmReqJson = gcmUtil.createGcmRequest(gcmRegId);
			GcmResponse gcmResponse = gcmService.sendGcmNotification(CommonConstants.GCM_URL, gcmReqJson);
			joinResponse.setSuccess(true);
		}
		else{
			joinResponse.setSuccess(false);
		}
		return joinResponse;
	}
	
	/**
	 *  API is called when a poster sends a request to a hopper
	 * 	In such a scenario the price quoted by hopper will be valid
	 * 	
	 * 	Steps
	 * 		1. Check if the hopper status is still Initiated/Partial
	 * 		2. If yes
	 * 				make a new entry into the journey table
	 * 				send a gcm notification to the hopper for confirm/deny
	 * 				send a message to the poster - "Request sent successfully".
	 * 		3. If No
	 * 				send a denial response - "not able to join. try another hop"
	 * @param joinRequest
	 * @return
	 */
	@RequestMapping(value="/pick",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JoinResponse shareRide(@RequestBody JoinRequest joinRequest) {
		JoinResponse joinResponse = new JoinResponse();
		TripDetails tripDetails = joinHelper.populateTripDetails(joinRequest, TripStatus.PICK_REQUEST.getCode());
		joinResponse = pickService.completePickRequest(tripDetails);
		boolean success = joinResponse.isSuccess();
		if(success){
			/*
			 * Trigger a notification via gcm to the hopper for confirm/deny 
			 */
			String gcmRegId = authService.getGcmRegId(joinRequest.getRecipientid());
			String gcmReqJson = gcmUtil.createGcmRequest(gcmRegId);
			GcmResponse gcmResponse = gcmService.sendGcmNotification(CommonConstants.GCM_URL, gcmReqJson);
			joinResponse.setSuccess(true);
		}else{
			joinResponse.setSuccess(false);
		}
		return joinResponse;
	}
	
	
	/**
	 * 
	 * @param confirmRequest
	 * @return
	 */
	@RequestMapping(value="/confirm",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ConfirmResponse confirmRequest(@RequestBody ConfirmRequest confirmRequest) {
		ConfirmResponse confirmResponse = new ConfirmResponse();
		confirmResponse.setSuccess(true);
		return confirmResponse;
	}
	
	/**
	 * 
	 * @param confirmRequest
	 * @return
	 */
	@RequestMapping(value="/deny",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ConfirmResponse denyRequest(@RequestBody ConfirmRequest confirmRequest) {
		ConfirmResponse confirmResponse = new ConfirmResponse();
		confirmResponse.setSuccess(true);
		return confirmResponse;
	}
}
