package com.bits.canvas.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.common.dto.HopJourneyDto;
import com.bits.canvas.common.dto.Location;
import com.bits.canvas.common.dto.PostJourneyDto;
import com.bits.canvas.common.utils.CommonUtils;
import com.bits.canvas.persistence.dto.JourneyDetailsDto;
import com.bits.canvas.persistence.service.HopAndPostService;
import com.bits.canvas.request.HopSearchRequestDto;
import com.bits.canvas.response.HopSearchResultDto;
import com.bits.canvas.transformer.ModelTransformer;

@Component
public class HopAndBookService {

	
	@Autowired
	HopAndPostService hopAndPostService;
	
	@Autowired
	ModelTransformer modelTransformer;
	
	
	
	/**
	 * getting all post request filtered by csc id
	 * @param hopSearchRequestDto
	 * @return
	 */
	public List<HopSearchResultDto> getHopSearchResults(HopSearchRequestDto hopSearchRequestDto){

		List<HopSearchResultDto> hopSearchResults = new ArrayList<HopSearchResultDto>();
		try {
			hopSearchResults = hopAndPostService.getAllPostRequest(hopSearchRequestDto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  hopSearchResults;
	}
	
	
	/**
	 * 
	 * @param hopSearchResultDtoList
	 * @param location
	 * @return
	 */
	public List<HopSearchResultDto> applyDestinatonFilter(List<HopSearchResultDto> hopSearchResultDtoList, Location location){
		List<HopSearchResultDto> hopSearchResultDtoList2 = new ArrayList<HopSearchResultDto>();
		BigDecimal srcLat = location.getLatitude();
		BigDecimal srcLng = location.getLongitude();
		for(HopSearchResultDto hopSearchResultDto: hopSearchResultDtoList){
			boolean isWithinLimits = CommonUtils.isDestWithinLimit(srcLat, srcLng, hopSearchResultDto.getLatitude(), hopSearchResultDto.getLongitude(), 2500);
			if(isWithinLimits){
				hopSearchResultDtoList2.add(hopSearchResultDto);
			}
		}
		return hopSearchResultDtoList2;
	}

	/**
	 * getting all the hop journey history of user to show the data in user profile page
	 * @param userId
	 * @return
	 */
	public List<HopJourneyDto> getHopHistoryForUser(String userId){
		List<JourneyDetailsDto> journeyList = hopAndPostService.getHopJourneyByUserId(userId);
		List<HopJourneyDto> hopJourneys = modelTransformer.prepareHopHistoryResponse(journeyList);
		return hopJourneys;
	}
	
	
	/**
	 * getting all the post journey history of user to show the data in user profile page
	 * @param userId
	 * @return
	 */
	public List<PostJourneyDto> getPostHistoryForUser(String userId){
		List<JourneyDetailsDto> journeyList = hopAndPostService.getPostJourneyByUserId(userId);
		List<PostJourneyDto> postJourneys = modelTransformer.preparePostHistoryResponse(journeyList);
		return postJourneys;
	}
	
	
}
