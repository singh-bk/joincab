package com.bits.canvas.mobile.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.common.utils.CommonUtils;
import com.bits.canvas.mobile.common.HopDto;
import com.bits.canvas.mobile.common.PostDto;
import com.bits.canvas.mobile.enums.HopStatus;
import com.bits.canvas.mobile.enums.PostStatus;
import com.bits.canvas.mobile.service.HopService;
import com.bits.canvas.mobile.service.PostService;
import com.bits.canvas.persistence.entity.HopDetails;
import com.bits.canvas.persistence.entity.PostDetails;
import com.bits.canvas.persistence.entity.UserProfile;

@Component
public class SearchHelper {

	
	@Autowired
	HopService hopService;
	
	@Autowired
	PostService postService;
	
	public void getAllHopAndPost(String cscId){
		List<HopDetails> hopList = 
				hopService.getHopByCscIdAndStatus(cscId, HopStatus.INITIATED.getCode());
		List<Integer> postStatusList = new ArrayList<Integer>();
		postStatusList.add(PostStatus.INITIATED.getCode());
		postStatusList.add(PostStatus.PARTIAL_JOIN.getCode());
		List<PostDetails> postList = 
				postService.getPostByCscIdAndStatus(cscId, postStatusList);
	}
	
	public PostDetails transform(String postDetailsStr){
		
		PostDetails postDetails = new PostDetails();
		StringTokenizer tokens = new StringTokenizer(postDetailsStr, "|");
		postDetails.setUserId(tokens.nextToken());
		postDetails.setSrcLat(new Float(tokens.nextToken()));
		postDetails.setSrcLng(new Float(tokens.nextToken()));
		postDetails.setSrcAddress(tokens.nextToken());
		postDetails.setSrcPlaceId(tokens.nextToken());
		postDetails.setDestLat(new Float(tokens.nextToken()));
		postDetails.setDestLng(new Float(tokens.nextToken()));
		postDetails.setDestAddress(tokens.nextToken());
		postDetails.setDestPlaceId(tokens.nextToken());
		postDetails.setPps(new Integer(tokens.nextToken()));
		postDetails.setSeatsOffered(new Integer(tokens.nextToken()));
		postDetails.setSeatsAvail(new Integer(tokens.nextToken()));
		postDetails.setReqCnt(new Integer(tokens.nextToken()));
		postDetails.setJoinCnt(new Integer(tokens.nextToken()));
		postDetails.setDenyCnt(new Integer(tokens.nextToken()));
		postDetails.setStatus(new Integer(tokens.nextToken()));
		postDetails.setTravelTime(new Long(tokens.nextToken()));

		return postDetails;
	}
	
	
	public HopDetails transformToHopDetails(String hopDetailsStr){
		
		HopDetails hopDetails = new HopDetails();
		StringTokenizer tokens = new StringTokenizer(hopDetailsStr, "|");
		hopDetails.setUserId(tokens.nextToken());
		hopDetails.setSrcLat(new Float(tokens.nextToken()));
		hopDetails.setSrcLng(new Float(tokens.nextToken()));
		hopDetails.setSrcAddress(tokens.nextToken());
		hopDetails.setSrcPlaceId(tokens.nextToken());
		hopDetails.setDestLat(new Float(tokens.nextToken()));
		hopDetails.setDestLng(new Float(tokens.nextToken()));
		hopDetails.setDestAddress(tokens.nextToken());
		hopDetails.setDestPlaceId(tokens.nextToken());
		hopDetails.setPps(new Integer(tokens.nextToken()));
		hopDetails.setStatus(new Integer(tokens.nextToken()));
		hopDetails.setReqCnt(new Integer(tokens.nextToken()));
		hopDetails.setDenyCnt(new Integer(tokens.nextToken()));
		hopDetails.setPickCnt(new Integer(tokens.nextToken()));
		hopDetails.setTravelTime(new Long(tokens.nextToken()));

		return hopDetails;
	}
	
	/**
	 * Filter out all data is not within a permissible range from the source location of the current user.
	 * From the results above filter out the ones that is not within permissible range from destination location of current user
	 * @param postDetailsList
	 * @param srcLat
	 * @param srcLng
	 * @param destLat
	 * @param destLng
	 * @return
	 */
	public List<PostDetails> applyDistanceFilter(List<PostDetails> postDetailsList, float srcLat, float srcLng, float destLat, float destLng ){
		List<PostDetails> postDetailsList2 = new ArrayList<PostDetails>();
		for(PostDetails postDetails: postDetailsList){
			//Do it for source location
			boolean isWithinLimits = CommonUtils.isLocationWithinLimit(srcLat, srcLng, postDetails.getSrcLat(), postDetails.getSrcLng(), 2500);
			if(isWithinLimits){
				postDetailsList2.add(postDetails);
			}
		}
		List<PostDetails> postDetailsList3 = new ArrayList<PostDetails>();
		for(PostDetails postDetails: postDetailsList2){
			//Do it for destination location
			boolean isWithinLimits = CommonUtils.isLocationWithinLimit(destLat, destLng, postDetails.getDestLat(), postDetails.getDestLng(), 2500);
			if(isWithinLimits){
				postDetailsList3.add(postDetails);
			}
		}
		return postDetailsList3;
	}
	
	public List<HopDetails> applyDistanceFilter2(List<HopDetails> hopDetailsList, float srcLat, float srcLng, float destLat, float destLng ){
		List<HopDetails> hopDetailsList2 = new ArrayList<HopDetails>();
		for(HopDetails hopDetails: hopDetailsList){
			//Do it for source location
			boolean isWithinLimits = CommonUtils.isLocationWithinLimit(srcLat, srcLng, hopDetails.getSrcLat(), hopDetails.getSrcLng(), 2500);
			if(isWithinLimits){
				hopDetailsList2.add(hopDetails);
			}
		}
		List<HopDetails> hopDetailsList3 = new ArrayList<HopDetails>();
		for(HopDetails hopDetails: hopDetailsList2){
			//Do it for destination location
			boolean isWithinLimits = CommonUtils.isLocationWithinLimit(destLat, destLng, hopDetails.getDestLat(), hopDetails.getDestLng(), 2500);
			if(isWithinLimits){
				hopDetailsList3.add(hopDetails);
			}
		}
		return hopDetailsList3;
	}
	
	public UserProfile transformToUserProfile(String userProfileStr){
		UserProfile userProfile = new UserProfile();
		StringTokenizer tokens = new StringTokenizer(userProfileStr, "|");
		userProfile.setUserId(tokens.nextToken());
		userProfile.setDisplayName(tokens.nextToken());
		userProfile.setGender(new Integer(tokens.nextToken()));
		userProfile.setMobNum(tokens.nextToken());
		userProfile.setVehNum(tokens.nextToken());
		userProfile.setVehType(new Integer(tokens.nextToken()));
		userProfile.setGcmRegId(tokens.nextToken());
		userProfile.setImgUrl(tokens.nextToken());
		
		return userProfile;
	}
	
	/**
	 * Convert list of postDetails and userProfile to a list of postDto
	 * @param postDetailsList
	 * @return
	 */
	public List<PostDto> transform(List<PostDetails> postDetailsList, Map<String, UserProfile> userProfileListMap, List<String> joinTripIds){
		List<PostDto> postDtoList = new ArrayList<PostDto>();
		for(PostDetails postDetails: postDetailsList){
			UserProfile profile = userProfileListMap.get(postDetails.getUserId());
			PostDto postDto = new PostDto();
			postDto.setUserId(profile.getUserId());
			postDto.setPostId(postDetails.getPostId());
			postDto.setTravelTime(postDetails.getTravelTime());
			postDto.setVehicleNo(profile.getVehNum());
			postDto.setVehicleType(profile.getVehType());
			postDto.setDisplayName(profile.getDisplayName());
			postDto.setUserImageUrl(profile.getImgUrl());
			postDto.setGender(profile.getGender());
			postDto.setPps(postDetails.getPps());
			postDto.setSrcAddress(postDetails.getSrcAddress());
			postDto.setSrcLat(postDetails.getSrcLat());
			postDto.setSrcLng(postDetails.getSrcLng());
			postDto.setSrcDist(100);//TODO - Get the distance between user source and current post
			postDto.setTravelTime(postDetails.getTravelTime());
			postDto.setDestAddress(postDetails.getDestAddress());
			postDto.setRating(5);//TODO - get rating at a later date
			
			//The posts that have already been requested by join by the hopper, should be marked
			if(joinTripIds != null && joinTripIds.contains(postDetails.getPostId())){
				postDto.setIsRequested(true);
			}else{
				postDto.setIsRequested(false);
			}
			
			postDtoList.add(postDto);
		}
		return postDtoList;
	}
	
	public List<HopDto> transformToHopDto(List<HopDetails> hopDetailsList, Map<String, UserProfile> userProfileListMap, List<String> pickTripIds){
		List<HopDto> hopDtoList = new ArrayList<HopDto>();
		for(HopDetails hopDetails: hopDetailsList){
			UserProfile profile = userProfileListMap.get(hopDetails.getUserId());
			HopDto hopDto = new HopDto();
			hopDto.setUserId(profile.getUserId());
			hopDto.setHopId(hopDetails.getHopId());
			hopDto.setTravelTime(hopDetails.getTravelTime());
			hopDto.setDisplayName(profile.getDisplayName());
			hopDto.setUserImageUrl(profile.getImgUrl());
			hopDto.setGender(profile.getGender());
			hopDto.setPps(hopDetails.getPps());
			hopDto.setSrcAddress(hopDetails.getSrcAddress());
			hopDto.setSrcLat(hopDetails.getSrcLat());
			hopDto.setSrcLng(hopDetails.getSrcLng());
			hopDto.setSrcDist(100);//TODO - Get the distance between user source and current hop
			hopDto.setTravelTime(hopDetails.getTravelTime());
			hopDto.setDestAddress(hopDetails.getDestAddress());
			hopDto.setRating(5);//TODO - get rating at a later date
			
			//The posts that have already been requested by join by the hopper, should be marked
			if(pickTripIds != null && pickTripIds.contains(hopDetails.getHopId())){
				hopDto.setIsRequested(true);
			}else{
				hopDto.setIsRequested(false);
			}
			
			hopDtoList.add(hopDto);
		}
		return hopDtoList;
	}


}
