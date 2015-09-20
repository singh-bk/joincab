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
import com.bits.canvas.mobile.request.JourneyActionRequest;
import com.bits.canvas.mobile.request.JourneyStartRequest;
import com.bits.canvas.mobile.response.JourneyActionResponse;
import com.bits.canvas.mobile.response.JourneyStartResponse;
import com.bits.canvas.mobile.service.TripService;

@Controller
@RequestMapping(value=CommonConstants.MOBILE_REQUEST_PREFIX)
public class MobileJourneyController {

	@Autowired
	TripService tripService;
	
	public static final Logger LOG = Logger.getLogger(MobileJourneyController.class);

	/**
	 * Will be initiated by the post user - to start the journey and notify all hoppers to confirm the same.
	 * Steps
	 * 	1. get the confirmed joined hoppers for the given postid
	 * 		Get this data from redis cache - use postId for the purpose.
	 *  2. update DB status for the all
	 *  	a. - change post details to started
	 *  	b. - change hop details to started
	 *  	c. - change trip details to started
	 *  3. send a gcm notification to the hoppers to start
	 *  	the notification will contain buttons to confirm and cancel
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/start",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JourneyStartResponse startJourney(@RequestBody JourneyStartRequest request) {
		JourneyStartResponse response = new JourneyStartResponse();
		Integer count = 
				tripService.updateTripStatus(request.getTripIdList(), TripStatus.STARTED.getCode());
		
		//TODO - send gcm response to all the hoppers and the post user
		return response;
	}
	
	/**
	 * The hopper receives a gcm request with confirm and cancel
	 * 1. on click of confirm
	 * 		a. update the hop details to started
	 * 		b. send a response to the post user
	 * @param postRequest
	 * @return
	 */
	@RequestMapping(value="/confirmStart",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JourneyActionResponse confirmStartJourney(@RequestBody JourneyActionRequest request) {
		JourneyActionResponse response = new JourneyActionResponse();
		response.setStatus(TripStatus.STARTED.getCode());
		//TDODO - send gcm response
		return response;
	}
	
	/**
	 * The hopper receives a gcm request with confirm and cancel
	 * 1. on click of cancel
	 * 		a. update the hop details to canceled
	 * 		b. send a response to the post user
	 * @param postRequest
	 * @return
	 */
	@RequestMapping(value="/cancelStart",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JourneyActionResponse cancelStartJourney(@RequestBody JourneyActionRequest request) {
		JourneyActionResponse response = new JourneyActionResponse();
		response.setStatus(TripStatus.CANCELLED.getCode());
		//TODO - send gcm response
		return response;
	}
	
	/**
	 * The hopper receives a gcm request with confirm and cancel
	 * 1. on click of cancel
	 * 		a. update the hop details to canceled
	 * 		b. send a response to the post user
	 * @param postRequest
	 * @return
	 */
	@RequestMapping(value="/refreshStatus",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JourneyActionResponse refreshJourneyStatus(@RequestBody JourneyActionRequest request) {
		JourneyActionResponse response = new JourneyActionResponse();
		//TODO - get the status from DB and populate response
		return response;
	}
}
