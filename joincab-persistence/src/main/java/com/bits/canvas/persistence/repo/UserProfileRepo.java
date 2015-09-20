package com.bits.canvas.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bits.canvas.persistence.entity.UserProfile;

public interface UserProfileRepo extends
		JpaRepository<UserProfile, String>,
		JpaSpecificationExecutor<UserProfile> {

	UserProfile findOne(String userId);
	
	@Query("SELECT profile.gcmRegId FROM UserProfile profile WHERE profile.userId=:userId")
	String getGcmRegId(@Param("userId") String userId);
}
