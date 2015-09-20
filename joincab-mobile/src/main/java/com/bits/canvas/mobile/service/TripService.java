package com.bits.canvas.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.persistence.repo.TripDetailsRepo;

@Component
public class TripService {

	
	@Autowired
	TripDetailsRepo tripDetailsRepo;
	
	public Integer updateTripStatus(List<String> tripIdList, Integer status){
		Integer count = tripDetailsRepo.updateTripStatus(tripIdList, status);
		return count;
	}
}
