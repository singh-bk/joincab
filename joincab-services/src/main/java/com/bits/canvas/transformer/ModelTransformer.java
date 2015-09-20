package com.bits.canvas.transformer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.common.constant.PostStatus;
import com.bits.canvas.common.dto.HopJourneyDto;
import com.bits.canvas.common.dto.Location;
import com.bits.canvas.common.dto.PostJourneyDto;
import com.bits.canvas.common.utils.CommonUtils;
import com.bits.canvas.common.utils.UniqueKeyGenerator;
import com.bits.canvas.geo.service.GooglePlacesImplService;
import com.bits.canvas.persistence.dto.JourneyDetailsDto;
import com.bits.canvas.persistence.entity.CabPostDetails;
import com.bits.canvas.persistence.entity.User;
import com.bits.canvas.request.FacebookDto;
import com.bits.canvas.request.GooglePlusDto;
import com.bits.canvas.request.HopSearchRequestDto;
import com.bits.canvas.request.PostRequestDto;
import com.bits.canvas.response.UserProfile;
/**
 * 
 * @author vatsritu
 *
 */
@Component
public class ModelTransformer {

	@Autowired
	GooglePlacesImplService googlePlacesImplService;
	
	@Autowired
	LocationTransformer locationTransformer;


	/**
	 * prepare post request model if no poster found for hop to book and share the cab
	 * @param hopSearchRequestDto
	 * @return
	 */
	public CabPostDetails preparePostRequest(HopSearchRequestDto hopSearchRequestDto,HttpServletRequest request){
		Location location = prepareLocationDto(hopSearchRequestDto);
		UserProfile userProfile = CommonUtils.getProfileFromSession(request);
		CabPostDetails cabPostDetails = new CabPostDetails();
		cabPostDetails.setCscId(hopSearchRequestDto.getCscId());
		cabPostDetails.setUserId(userProfile.getEmail());
		cabPostDetails.setPickUpTime(CommonUtils.extractDateTime(hopSearchRequestDto.getHopDate(), hopSearchRequestDto.getHopTime()));
		cabPostDetails.setId(UniqueKeyGenerator.getUniqueKey());
		cabPostDetails.setHopperCount(1);
		cabPostDetails.setGenderPref(userProfile.getGender().getCode());
		cabPostDetails.setVehicleType(hopSearchRequestDto.getVehicleType());
		cabPostDetails.setLatitude(location.getLatitude());
		cabPostDetails.setLongitude(location.getLongitude());
		cabPostDetails.setPostFormattedAddress(location.getFormattedAddress());
		cabPostDetails.setJoinMsgCode(CommonUtils.getLocationCode(hopSearchRequestDto.getHopLocation()));
		return cabPostDetails;
	}


	/**
	 * prepare post request in the case user has book the cab by its own and looking for hopper
	 * @param postRequestDto
	 * @return
	 */
	public CabPostDetails preparePostRequest(PostRequestDto postRequestDto,HttpServletRequest request){
		Location location = prepareLocationDto(postRequestDto);
		UserProfile userProfile = CommonUtils.getProfileFromSession(request);
		CabPostDetails cabPostDetails = new CabPostDetails();
		cabPostDetails.setCscId(postRequestDto.getCscId());
		cabPostDetails.setUserId(userProfile.getEmail());
		cabPostDetails.setPickUpTime(CommonUtils.extractDateTime(postRequestDto.getPostDate(), postRequestDto.getPostTime()));
		cabPostDetails.setId(UniqueKeyGenerator.getUniqueKey());
		cabPostDetails.setHopperCount(0);
		cabPostDetails.setShare(postRequestDto.getShare());
		cabPostDetails.setSeatAvail(1);
		cabPostDetails.setGenderPref(Integer.parseInt(postRequestDto.getGenderPref()));
		cabPostDetails.setVehicleType(postRequestDto.getPostVehicleType());
		cabPostDetails.setPostFormattedAddress(location.getFormattedAddress());
		cabPostDetails.setLatitude(location.getLatitude());
		cabPostDetails.setLongitude(location.getLongitude());
		cabPostDetails.setJoinMsgCode(CommonUtils.getLocationCode(postRequestDto.getPostFormattedAddress()));
		cabPostDetails.setPostStatus(PostStatus.POSTED.getCode());
		return cabPostDetails;
	}

	/**
	 * prepare the location details for post request
	 * @param postRequestDto
	 * @return
	 */
	public Location prepareLocationDto(PostRequestDto postRequestDto){
		Location location = new Location();
		if(postRequestDto.getPostFormattedAddress() != null && postRequestDto.getPostFormattedAddress().length()>0){
			location = populateLocationFromDto(postRequestDto);
		}else{
			location = googlePlacesImplService.getSrcLocation(postRequestDto);
			postRequestDto.setPostFormattedAddress(location.getFormattedAddress());
		}
		return location;
	}

	/**
	 * prepare the location details for post in the case of  no poster available
	 * @param hopSearchRequestDto
	 * @return
	 */
	public Location prepareLocationDto(HopSearchRequestDto hopSearchRequestDto){
		Location location = new Location();
		String fullAddress = hopSearchRequestDto.getHopLocation();
		location.setCscId(hopSearchRequestDto.getCscId());
		location.setLocationName(fullAddress);
		googlePlacesImplService.populateLatLngInLocation(location);
		return location;
	}
	



	/**
	 * populating the user profile
	 * @param user
	 * @return
	 */
	public UserProfile getUserProfile(User user){
		String accessToken = RandomStringUtils.randomAlphanumeric(16);
		UserProfile userProfile = new UserProfile();
		userProfile.setEmail(user.getEmail());
		userProfile.setFirstName(user.getFirstName());
		userProfile.setLastName(user.getLastName());
		userProfile.setPhoneNumber(user.getPhoneNumber());
		userProfile.setAccessToken(accessToken);
		userProfile.setProvider("CUSTOM");
		userProfile.setProviderUid("undefined");
		if(user.getPhoneNumber() != null && !("").equalsIgnoreCase(user.getPhoneNumber())){
			userProfile.setVerified(true);
		}else{
			userProfile.setVerified(false);
		}
		return userProfile;
	}

	/**
	 * populating the user profile for facebook
	 * @param user
	 * @return
	 */
	public UserProfile getFacebookUserProfile(User user,FacebookDto facebookDto){
		String accessToken = RandomStringUtils.randomAlphanumeric(16);
		UserProfile userProfile = new UserProfile();
		userProfile.setEmail(user.getEmail());
		userProfile.setFirstName(user.getFirstName());
		userProfile.setLastName(user.getLastName());
		userProfile.setPhoneNumber(user.getPhoneNumber());
		userProfile.setAccessToken(accessToken);
		userProfile.setProvider("FACEBOOK");
		userProfile.setProviderUid(facebookDto.getId());
		if(user.getPhoneNumber() != null && !("").equalsIgnoreCase(user.getPhoneNumber())){
			userProfile.setVerified(true);
		}else{
			userProfile.setVerified(false);
		}
		return userProfile;
	}
	/**
	 * @param user
	 * @param googlePlusDto
	 * @return
	 */
	public UserProfile getGooglePlusUserProfile(User user,GooglePlusDto googlePlusDto){
		String accessToken = RandomStringUtils.randomAlphanumeric(16);
		UserProfile userProfile = new UserProfile();
		userProfile.setEmail(user.getEmail());
		userProfile.setFirstName(user.getFirstName());
		userProfile.setLastName(user.getLastName());
		userProfile.setPhoneNumber(user.getPhoneNumber());
		userProfile.setAccessToken(accessToken);
		userProfile.setProvider("GOOGLE");
		userProfile.setProviderUid(googlePlusDto.getId());
		userProfile.setImageUrl(googlePlusDto.getImageUrl());
		if(user.getPhoneNumber() != null && !("").equalsIgnoreCase(user.getPhoneNumber())){
			userProfile.setVerified(true);
		}else{
			userProfile.setVerified(false);
		}
		return userProfile;
	}

	/**
	 * prepare Location dto from input request
	 * @param hopSearchRequestDto
	 * @return
	 */
	/**
	 * prepare Location dto from input request
	 * @param hopSearchRequestDto
	 * @return
	 */
	public Location prepareLocationFromInput(HopSearchRequestDto hopSearchRequestDto){
		Location location = new Location();
		String cscId = hopSearchRequestDto.getCscId();
		String locationFromCscId = locationTransformer.getLocationFromCscId(cscId);
		String fullAddress = hopSearchRequestDto.getHopLocation()+","+locationFromCscId;
		location.setCscId(cscId);
		location.setLocationName(fullAddress);
		return location;
	}

	public Location prepareLocationFromInput(PostRequestDto postRequestDto){
		Location location = new Location();
		String cscId = postRequestDto.getCscId();
		String locationFromCscId = locationTransformer.getLocationFromCscId(cscId);
		String fullAddress = postRequestDto.getPostLocation()+","+locationFromCscId;
		location.setCscId(cscId);
		location.setLocationName(fullAddress);
		return location;
	}


	public List<HopJourneyDto> prepareHopHistoryResponse(List<JourneyDetailsDto> journeyDetailsDtos){
		List<HopJourneyDto> hopJourneyDtos = new ArrayList<HopJourneyDto>();
		for(JourneyDetailsDto journeyDetailsDto : journeyDetailsDtos){
			HopJourneyDto hopJourneyDto = new HopJourneyDto();
			hopJourneyDto.setDate(journeyDetailsDto.getPickupTime());
			hopJourneyDto.setHopLocation(journeyDetailsDto.getAddress());
			hopJourneyDto.setHopStatus(journeyDetailsDto.getStatus());
			hopJourneyDto.setPosterName(journeyDetailsDto.getFirstName()+","+journeyDetailsDto.getLastName());
			hopJourneyDto.setPostId(journeyDetailsDto.getId());
			hopJourneyDtos.add(hopJourneyDto);
		}
		return hopJourneyDtos;
	}

	public List<PostJourneyDto> preparePostHistoryResponse(List<JourneyDetailsDto> journeyDetailsDtos){
		List<PostJourneyDto> popJourneyDtos = new ArrayList<PostJourneyDto>();
		for(JourneyDetailsDto journeyDetailsDto : journeyDetailsDtos){
			PostJourneyDto postJourneyDto = new PostJourneyDto();
			postJourneyDto.setDate(journeyDetailsDto.getPickupTime());
			postJourneyDto.setPostLocation(journeyDetailsDto.getAddress());
			postJourneyDto.setPostStatus(journeyDetailsDto.getStatus());
			postJourneyDto.setHopperName(journeyDetailsDto.getFirstName()+","+journeyDetailsDto.getLastName());
			postJourneyDto.setPostId(journeyDetailsDto.getId());
			popJourneyDtos.add(postJourneyDto);
		}
		return popJourneyDtos;
	}

	public Location populateLocationFromDto(PostRequestDto postRequestDto){
		Location location = new Location();
		location.setFormattedAddress(postRequestDto.getPostFormattedAddress());
		location.setCscId(postRequestDto.getCscId());
		location.setIsAccurate(true);
		location.setLatitude(postRequestDto.getPostLatitude());
		location.setLongitude(postRequestDto.getPostLongitude());
		return location;
	}
	
	
	public Location populateLocationFromDto(HopSearchRequestDto hopSearchRequestDto){
		Location location = new Location();
		location.setFormattedAddress(hopSearchRequestDto.getHopFormattedAddress());
		location.setCscId(hopSearchRequestDto.getCscId());
		location.setIsAccurate(true);
		location.setLatitude(hopSearchRequestDto.getHopLatitude());
		location.setLongitude(hopSearchRequestDto.getHopLongitude());
		return location;
	}
	
	public PostRequestDto populateCabBookDetails(HopSearchRequestDto hopSearchRequestDto){
		PostRequestDto postRequestDto = new PostRequestDto();
		postRequestDto.setCscId(hopSearchRequestDto.getCscId());
		postRequestDto.setPostDate(hopSearchRequestDto.getHopDate());
		postRequestDto.setPostFormattedAddress(hopSearchRequestDto.getHopFormattedAddress());
		postRequestDto.setPostLatitude(hopSearchRequestDto.getHopLatitude());
		postRequestDto.setPostLongitude(hopSearchRequestDto.getHopLongitude());
		postRequestDto.setPostLocation(hopSearchRequestDto.getHopLocation());
		postRequestDto.setPostVehicleType(hopSearchRequestDto.getVehicleType());
		return postRequestDto;
	}

}
