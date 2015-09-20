package com.bits.canvas.mobile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.persistence.entity.ADUser;
import com.bits.canvas.persistence.entity.UserProfile;
import com.bits.canvas.persistence.repo.ADUserRepo;
import com.bits.canvas.persistence.repo.UserProfileRepo;

@Component
public class AuthService {

	@Autowired
	ADUserRepo aDUserRepo;
	
	@Autowired
	UserProfileRepo userProfileRepo;
	
	public ADUser fetchADUser(String userId){
		ADUser adUser = null;
		adUser = aDUserRepo.findOne(userId);
		return adUser;
	}
	
	public ADUser saveADUser(ADUser adUser){
		adUser = aDUserRepo.save(adUser);
		return adUser;
	}
	
	public UserProfile fetchUserProfile(String userId){
		UserProfile userProfile = userProfileRepo.findOne(userId);
		return userProfile;
	}
	
	public UserProfile saveUserProfile(UserProfile userProfile){
		userProfile = userProfileRepo.save(userProfile);
		return userProfile;
	}
	
	public String getGcmRegId(String userId){
		String gcmRegId = userProfileRepo.getGcmRegId(userId);
		return gcmRegId;
	}
}
