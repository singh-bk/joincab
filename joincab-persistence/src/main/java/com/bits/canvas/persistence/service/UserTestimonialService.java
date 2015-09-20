package com.bits.canvas.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.persistence.entity.UserTestimonail;
import com.bits.canvas.persistence.repo.UserTestimonialRepo;
import com.bits.canvas.response.TestimonialDto;

@Component
public class UserTestimonialService {

	@Autowired
	UserTestimonialRepo userTestimonialRepo;
	
	private static Logger LOG = Logger.getLogger(UserTestimonialService.class);
	
	public List<TestimonialDto> getTopFiveTestimonial(){
		List<TestimonialDto> testimonialDtos = new ArrayList<TestimonialDto>();
		try {
			testimonialDtos = userTestimonialRepo.getTestimonial();
		} catch (Exception e) {
			LOG.error("Exception occured while getting the testimonail ",e);
		}
		
		return testimonialDtos;
	}
	
	
	public boolean saveTestimonial(UserTestimonail userTestimonail){
		boolean isSaved = false;
		
		try {
			userTestimonialRepo.save(userTestimonail);
			isSaved = true;
		} catch (Exception e) {
			LOG.error("Exception occured while saving the testimonail ",e);
		}
		return isSaved;
	}
}
