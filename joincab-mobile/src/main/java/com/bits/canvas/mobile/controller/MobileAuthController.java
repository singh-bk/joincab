package com.bits.canvas.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bits.canvas.mobile.common.CommonConstants;
import com.bits.canvas.mobile.enums.UserAuth;
import com.bits.canvas.mobile.enums.VerificationStatus;
import com.bits.canvas.mobile.helper.AuthHelper;
import com.bits.canvas.mobile.request.AuthRequest;
import com.bits.canvas.mobile.request.ProfileVerificationRequest;
import com.bits.canvas.mobile.request.SignUpRequest;
import com.bits.canvas.mobile.response.AuthResponse;
import com.bits.canvas.mobile.response.ProfileVerificationResponse;
import com.bits.canvas.mobile.transformer.AuthTransformer;
import com.bits.canvas.persistence.entity.UserProfile;

@Controller
@RequestMapping(value=CommonConstants.MOBILE_AUTH_PREFIX)
public class MobileAuthController {

	
	@Autowired
	AuthHelper authHelper;
	
	@Autowired
	AuthTransformer authTransformer;
	
	
	/**
	 * Check if the user exists
	 * 		if user exists
	 * 			check if profile is verified
	 * 				If there is no entry in user profile table
	 * 					make an entry and return isVerified as false;
	 * 				else
	 * 					if profile is verified, return isVerified as true
	 * 					if profile is not verified, return isVerified as false;
	 * 		if user does not exists
	 * 			return exists as not exists;
	 * 		if user/pwd combo is incorrect
	 * 			return exists as incorrect pwd.
	 * @param authRequest
	 * @return
	 */
	@RequestMapping(value="/signin",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setUserId(authRequest.getEmail());
		UserAuth userAuth = authHelper.adUserExists(authRequest.getEmail(), 
				authRequest.getPwd(), authRequest.getSourceChannel());
		if(userAuth.equals(UserAuth.EXISTS)){
			authResponse.setStatus(userAuth.getUserAuth());
			UserProfile userProfile = authHelper.getUserProfile(authRequest);
			if(userProfile == null){
				authResponse.setVerified(VerificationStatus.NOT_VERIFIED.getVerified());
			}
			else{
				authResponse = authTransformer.transform(userProfile, authResponse);
			}
		}else if(userAuth.equals(UserAuth.NOT_EXISTS) || userAuth.equals(UserAuth.INCORRECT_PWD)){
			authResponse.setStatus(userAuth.getUserAuth());
			authResponse.setVerified(VerificationStatus.NOT_VERIFIED.getVerified());
		}
		
		return authResponse;
	}
	
	/**
	 * Get the profile details and update the user profile table
	 * @param profileVerficationRequest
	 * @return
	 */
	@RequestMapping(value="/profiledetails",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ProfileVerificationResponse saveProfileDetails(@RequestBody ProfileVerificationRequest profileVerficationRequest){
		ProfileVerificationResponse profileVerificationResponse = new ProfileVerificationResponse();
		boolean updated = authHelper.populateUserProfile(profileVerficationRequest);
		profileVerificationResponse.setUserId(profileVerficationRequest.getUserId());	
		profileVerificationResponse.setVerified(updated);
		
		return profileVerificationResponse;
	}

	/**
	 * For sign up using FB/GOOG- the signin service should be called.
	 * For sign up using custom - insert data into ADUser table and proceed.
	 * 	set verified as false.
	 * 
	 * If the email already exists - send the status as 1, For newly added send it as 4.
	 * @param signUpRequest
	 * @return
	 */
	@RequestMapping(value="/signup",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AuthResponse registerUser(@RequestBody SignUpRequest signUpRequest){
		AuthResponse authResponse = new AuthResponse();
		authResponse.setUserId(signUpRequest.getEmail());
		UserAuth userAuth = authHelper.saveADUserNotExistsCustom(signUpRequest.getEmail(), signUpRequest.getPassword());
		authResponse.setStatus(userAuth.getUserAuth());		
		authResponse.setVerified(VerificationStatus.NOT_VERIFIED.getVerified());//Return false if the profile details is yet to be completed.
		return authResponse;
	}
}
