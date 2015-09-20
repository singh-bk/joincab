package com.bits.canvas.mobile.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.enums.SourceChannel;
import com.bits.canvas.mobile.enums.UserAuth;
import com.bits.canvas.mobile.request.AuthRequest;
import com.bits.canvas.mobile.request.ProfileVerificationRequest;
import com.bits.canvas.mobile.service.AuthService;
import com.bits.canvas.mobile.transformer.AuthTransformer;
import com.bits.canvas.persistence.entity.ADUser;
import com.bits.canvas.persistence.entity.UserProfile;

@Component
public class AuthHelper {

	@Autowired
	AuthService authService;
	
	@Autowired
	AuthTransformer authTransformer;
	
	/**
	 * Check if the user exists in the ADUSER table.
	 * For people using FB/GOOG login - 
	 * 		If the user does not exists - add a new entry
	 * For people using CUSTOM login - validate the password.
	 * @param userId
	 * @param pwd
	 * @param srcChannel
	 * @return
	 */
	public UserAuth adUserExists(String userId, String pwd, Integer srcChannel){
		ADUser adUser = authService.fetchADUser(userId);
		if(srcChannel.equals(SourceChannel.CUSTOM.getSrcChannel())){
			if(adUser != null){
				if(adUser.getPassword().equals(pwd)){
					return UserAuth.EXISTS;
				}
				else{
					return UserAuth.INCORRECT_PWD;
				}
			}
			else{
				return UserAuth.NOT_EXISTS;
			}
		}else{
			if(adUser == null){
				adUser = new ADUser();
				adUser.setUserId(userId);
				authService.saveADUser(adUser);
			}
			return UserAuth.EXISTS;
		}
	}
	
	/**
	 * This will be called during sign up request 
	 * 		To be used only for CUSTOM sign in
	 * @param userId
	 * @param pwd
	 * @param srcChannel
	 * @return
	 */
	public UserAuth saveADUserNotExistsCustom(String userId, String pwd){
		ADUser adUser = authService.fetchADUser(userId);
		if(adUser == null){
			adUser = new ADUser();
			adUser.setUserId(userId);
			adUser.setPassword(pwd);;
			authService.saveADUser(adUser); //TODO
			return UserAuth.ADDED;
		}else{
			return UserAuth.EXISTS;
		}
	}
	/**
	 * Check if UserProfile is present for the user.
	 * If yes
	 * 		return the same
	 * If no
	 * 		If the request is via FB/GOOG Login
	 * 			save the UserProfile -set the isVerified as false
	 * 		else If the request is via CUSTOM
	 * 			return null
	 * @param authRequest
	 * @return
	 */
	public UserProfile getUserProfile(AuthRequest authRequest){
		UserProfile userProfile = authService.fetchUserProfile(authRequest.getEmail());
		int srcChannel = authRequest.getSourceChannel();
		if(userProfile == null && srcChannel != SourceChannel.CUSTOM.getSrcChannel()){
			userProfile = authTransformer.transform(authRequest);
			userProfile = authService.saveUserProfile(userProfile);
		}
		return userProfile;
	}
	
	/**
	 * Add extra details to the user profile and save it
	 * @param request
	 * @return
	 */
	public boolean populateUserProfile(ProfileVerificationRequest request){
		boolean updated = false;
		UserProfile userProfile = authService.fetchUserProfile(request.getUserId());
		userProfile = authTransformer.transform(request, userProfile);
		userProfile = authService.saveUserProfile(userProfile);
		updated = true;
		return updated;
	}
}
