package com.bits.canvas.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.persistence.entity.PostDetails;
import com.bits.canvas.persistence.entity.UserProfile;
import com.bits.canvas.persistence.repo.PostDetailsRepo;
import com.bits.canvas.persistence.repo.UserProfileRepo;
import com.bits.canvas.redis.connector.RedisConnector;

@Component
public class PostService {

	@Autowired
	PostDetailsRepo postDetailsRepo;
	
	@Autowired
	UserProfileRepo userProfileRepo;
	
	@Autowired
	RedisConnector redisConnector;
	
	/**
	 * Save the post details to redis
	 * Check if the user profile is present in the cache
	 * 		if not, fetch from db and put it in cache.
	 * 
	 * Following values goes into the cachce
	 * 		1. key-postId, value-postDetails
	 * 		2. key-postId appended with Stat, value - status(here it will initiated/1)
	 * 		3. key-emailid, value-userprofile
	 * @param postDetails
	 */
	public void savePostDetails(PostDetails postDetails){
		//TODO - do the below two steps via pipelineing.
		redisConnector.set(postDetails.getPostId(), postDetails);
		redisConnector.set(postDetails.getPostId()+"Stat", postDetails.getSeatsAvail());
		UserProfile profile = (UserProfile)redisConnector.getObjectValue(postDetails.getUserId());
		if(profile == null){
			profile = userProfileRepo.findOne(postDetails.getUserId());
			redisConnector.set(postDetails.getUserId(), profile);
		}
		//TODO - move this save to the DB in an async way
		postDetailsRepo.save(postDetails);
	}
	
	public List<PostDetails> getPostByCscIdAndStatus(String cscId, List<Integer> status){
		List<PostDetails> postList = postDetailsRepo.findByCscIdAndStatus(cscId, status);
		return postList;
	}
}
