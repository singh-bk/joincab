package com.bits.canvas.mobile.transformer;

import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.enums.Gender;
import com.bits.canvas.mobile.enums.VerificationStatus;
import com.bits.canvas.mobile.request.AuthRequest;
import com.bits.canvas.mobile.request.ProfileVerificationRequest;
import com.bits.canvas.mobile.response.AuthResponse;
import com.bits.canvas.persistence.entity.UserProfile;

@Component
public class AuthTransformer {

	/**
	 * 
	 * @param authRequest
	 * @return
	 */
	public UserProfile transform(AuthRequest authRequest){
		UserProfile userProfile = new UserProfile();
		userProfile.setUserId(authRequest.getEmail());
		userProfile.setDisplayName(authRequest.getDisplayName());
		userProfile.setDob(authRequest.getDob()+"");
		userProfile.setFirstName(authRequest.getFirstName());
		userProfile.setLastName(authRequest.getLastName());
		userProfile.setGcmRegId(authRequest.getGcmRegId());
		
		if(authRequest.getGender() != null && 
				authRequest.getGender().equalsIgnoreCase("MALE")){
			userProfile.setGender(Gender.MALE.getGender());
		}else if(authRequest.getGender() != null && 
				authRequest.getGender().equalsIgnoreCase("FEMALE")){
			userProfile.setGender(Gender.FEMALE.getGender());
		}else{
			userProfile.setGender(Gender.OTHERS.getGender());
		}
		
		userProfile.setMobNum(authRequest.getMobileNumber());
		userProfile.setIsVerified(VerificationStatus.NOT_VERIFIED.getVerified());
		return userProfile;
	}
	
	/**
	 * TODO - check for the bytes
	 * @param request
	 * @return
	 */
	public UserProfile transform(ProfileVerificationRequest request, UserProfile userProfile){
		userProfile.setVehType(request.getVehicleType());
		userProfile.setVehNum(request.getVehicleNumber());
		userProfile.setIsOwned(request.getOwnedVehicle());
		userProfile.setIsVerified(VerificationStatus.VERIFIED.getVerified());
		userProfile.setGcmRegId(request.getGcmRegId());
		if(userProfile.getGender() == null){
			userProfile.setGender(request.getGender());
		}
		
		return userProfile;
	}
	
	public AuthResponse transform(UserProfile userProfile, AuthResponse response){
		response.setFirstName(userProfile.getFirstName());
		response.setLastName(userProfile.getLastName());
		response.setDisplayName(userProfile.getDisplayName());
		response.setGender(userProfile.getGender());
		response.setMobileNumber(userProfile.getMobNum());
		response.setDob(userProfile.getDob());
		response.setImagePath(userProfile.getImgUrl());
		response.setImageBytes("");//TODO - 
		response.setCompanyName("ASPS"); // TODO - 
		response.setOwned(userProfile.getIsOwned());
		response.setVehicleName("Hynduai i10"); //TODO
		response.setVehicleNumber(userProfile.getVehNum());
		response.setVerified(userProfile.getIsVerified());
		return response;
		
	}
}
