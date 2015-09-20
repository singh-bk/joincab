package com.bits.canvas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bits.canvas.common.constant.CommonConstants;
import com.bits.canvas.common.dto.Location;
import com.bits.canvas.geo.service.GoogleDirectionsMatrixService;
import com.bits.canvas.geo.service.GooglePlacesImplService;
import com.bits.canvas.request.HopSearchRequestDto;
import com.bits.canvas.request.PostRequestDto;
import com.bits.canvas.request.RegistrationForm;
import com.bits.canvas.response.HopSearchResultDto;
import com.bits.canvas.services.HopAndBookService;
import com.bits.canvas.transformer.ModelTransformer;

@Controller
public class SearchController {

	@Autowired
	HopAndBookService hopAndBookService;
	
	@Autowired
	GooglePlacesImplService googlePlacesImplService;
	
	@Autowired
	ModelTransformer modelTransformer;
	
	@Autowired
	GoogleDirectionsMatrixService googleDirectionsMatrixService;
	
	/**
	 * this method will get all the value for UI for search criterion.
	 * @param hopSearchDto
	 * @return
	 */
	@RequestMapping(value = "/getCabsForHop.htm" , method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView searchHopAvailability(@ModelAttribute("hopSearchRequestDto") HopSearchRequestDto hopSearchRequestDto,BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		Location location = new Location();
		if(hopSearchRequestDto.getHopFormattedAddress() != null && hopSearchRequestDto.getHopFormattedAddress().length()>0){
			location = modelTransformer.populateLocationFromDto(hopSearchRequestDto);
		}else{
			location = googlePlacesImplService.getSrcLocation(hopSearchRequestDto);
			hopSearchRequestDto.setHopFormattedAddress(location.getFormattedAddress());
			hopSearchRequestDto.setHopLatitude(location.getLatitude());
			hopSearchRequestDto.setHopLongitude(location.getLongitude());
		}
		
		List<HopSearchResultDto> hopResultList = hopAndBookService.getHopSearchResults(hopSearchRequestDto);
		if(location.getIsAccurate() == null || !location.getIsAccurate()){
			modelAndView.addObject("hopSearchRequestDto", new HopSearchRequestDto());
			modelAndView.addObject("registerationForm", new RegistrationForm());
			modelAndView.addObject("postRequestDto", new PostRequestDto());
			modelAndView.setViewName(CommonConstants.HOME_PAGE);
		}
		else{
			hopResultList = hopAndBookService.applyDestinatonFilter(hopResultList, location);
			if (hopResultList.size() > 0) {
				modelAndView.addObject("hopResultList", hopResultList);
				modelAndView.addObject("srcLocation", location);
				modelAndView.setViewName(CommonConstants.SEARCH_RESULT_PAGE);
			} else {
				PostRequestDto postAndRequestDto = modelTransformer.populateCabBookDetails(hopSearchRequestDto);
				String[] origin = {location.getLatitude()+","+location.getLongitude()};
				//hard coded for Bangalore Airport.
				String[] bangaloreAirport = {"13.204492,77.707691"};
				Long distance = googleDirectionsMatrixService.calculateDrivingDistance(origin, bangaloreAirport);
				postAndRequestDto.setDistanceFromDest(distance);
				modelAndView.addObject("postRequestDto", postAndRequestDto);
				modelAndView.addObject("hopSearchDto", hopSearchRequestDto);
				modelAndView.setViewName(CommonConstants.BOOKING_PAGE);
			}
		}
		modelAndView.addObject(CommonConstants.REGISTRATION_FORM, new RegistrationForm());
		return modelAndView;
	}

}
