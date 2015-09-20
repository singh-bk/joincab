/**
 * Services for cab post request
 */
package com.bits.canvas.persistence.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.common.constant.PostStatus;
import com.bits.canvas.common.utils.CommonUtils;
import com.bits.canvas.persistence.dto.JourneyDetailsDto;
import com.bits.canvas.persistence.entity.CabHopDetails;
import com.bits.canvas.persistence.entity.CabPostDetails;
import com.bits.canvas.persistence.repo.CabHopDetailsRepo;
import com.bits.canvas.persistence.repo.CabPostDetailsRepo;
import com.bits.canvas.request.HopSearchRequestDto;
import com.bits.canvas.response.HopSearchResultDto;

/**
 * @author vatsritu
 * May 24, 2014
 */
@Component
public class HopAndPostService {

	private static final Logger LOG = Logger.getLogger(HopAndPostService.class);

	@Autowired
	CabPostDetailsRepo cabPostDetailsRepo;


	@Autowired
	CabHopDetailsRepo cabHopDetailsRepo;



	public CabPostDetails savePostRequest(CabPostDetails cabPostDetails){
		CabPostDetails cabPostDetails2 = null;
		try {
			cabPostDetails2 = cabPostDetailsRepo.save(cabPostDetails);
		} catch (Exception e) {
			LOG.error("Exception occured while saving the post request",e);
		}
		return cabPostDetails2;
	}


	public List<JourneyDetailsDto> getPostJourneyByUserId(String id){

		List<JourneyDetailsDto> journeyDetailsDtos = null;

		try{
			List<JourneyDetailsDto> journeyDetailsList = cabPostDetailsRepo.findByUserIdAndStatus(id, PostStatus.POSTED.getCode());
			List<JourneyDetailsDto> journeyDetailsList1 = cabPostDetailsRepo.findByUserIdAndStatus(id, PostStatus.REQUEST_RECEIVED.getCode(),PostStatus.REQUEST_CONFIRMED.getCode());
			journeyDetailsDtos = new ArrayList<JourneyDetailsDto>();
			journeyDetailsDtos.addAll(journeyDetailsList);
			journeyDetailsDtos.addAll(journeyDetailsList1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return journeyDetailsDtos;
	}


	public List<JourneyDetailsDto> getHopJourneyByUserId(String id){

		List<JourneyDetailsDto> journeyDetailsList1 = null;
		try{
			journeyDetailsList1 = cabHopDetailsRepo.findAllHopDetailsByUserId(id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return journeyDetailsList1;
	}


	public CabPostDetails getPostDetailsById(String id){
		CabPostDetails cabPostDetails = null;
		try {
			cabPostDetails = cabPostDetailsRepo.findOne(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cabPostDetails;
	}



	public boolean saveHopRequest(CabHopDetails cabHopDetails){
		boolean isSaved = false;
		try {
			CabHopDetails cabHopDetails2 = cabHopDetailsRepo.save(cabHopDetails);
			if(cabHopDetails2 != null)isSaved=true;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return isSaved;
	}

	public CabHopDetails getHopDetailsByPostId(String id){
		CabHopDetails cabHopDetails = null;
		try {
			cabHopDetails = cabHopDetailsRepo.findByPostId(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cabHopDetails;
	}
	
	public CabHopDetails getHopDetailsById(Integer id){
		CabHopDetails cabHopDetails = null;
		try {
			cabHopDetails = cabHopDetailsRepo.findOne(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cabHopDetails;
	}
	
	public CabHopDetails getHopDetailsByPostIdAndStatus(String id,Integer status){
		CabHopDetails cabHopDetails = null;
		try {
			cabHopDetails = cabHopDetailsRepo.findByPostIdAndStatus(id, status);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cabHopDetails;
	}


	/**
	 * 
	 * @param hopSearchRequestDto
	 * @return
	 * @throws ParseException 
	 */
	public List<HopSearchResultDto> getAllPostRequest(HopSearchRequestDto hopSearchRequestDto) throws ParseException{

		String hopDate = hopSearchRequestDto.getHopDate();
		String cscId = hopSearchRequestDto.getCscId();
		String hopTime = hopSearchRequestDto.getHopTime();
		String startDateTime =CommonUtils.subMinutesToTime(CommonUtils.extractDateTime(hopDate, hopTime));
		String endDateTime = CommonUtils.addMinutesToTime(CommonUtils.extractDateTime(hopDate, hopTime));
		int vehicleType =0;
		vehicleType = hopSearchRequestDto.getVehicleType();

		List<HopSearchResultDto> hopSearchResultDtos = cabPostDetailsRepo.findByCscIdAndStartDateAndEndDate(cscId,startDateTime, endDateTime,vehicleType);
		return  hopSearchResultDtos;
	}

	public CabHopDetails getHopDetailsByPostIdAndJoinMsgCode(String postId,String joinMsgCode){

		CabHopDetails cabHopDetails = null;

		try{
			cabHopDetails = cabHopDetailsRepo.findByPostIdAndJoinMsgCode(postId,joinMsgCode);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return cabHopDetails;
	}
}
