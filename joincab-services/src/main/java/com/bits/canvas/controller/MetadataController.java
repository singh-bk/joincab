package com.bits.canvas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bits.canvas.common.constant.CommonConstants;
import com.bits.canvas.common.dto.HopJourneyDto;
import com.bits.canvas.common.dto.PostJourneyDto;
import com.bits.canvas.common.utils.CommonUtils;
import com.bits.canvas.persistence.entity.UserTestimonail;
import com.bits.canvas.persistence.repo.UserTestimonialRepo;
import com.bits.canvas.persistence.service.UserTestimonialService;
import com.bits.canvas.request.HopSearchRequestDto;
import com.bits.canvas.request.PostRequestDto;
import com.bits.canvas.request.RegistrationForm;
import com.bits.canvas.response.CommonResponse;
import com.bits.canvas.response.TestimonialDto;
import com.bits.canvas.response.UserProfile;
import com.bits.canvas.services.BookAndShareService;
import com.bits.canvas.services.HopAndBookService;

/**
 * 18-DEC-2014
 * @author vatsritu
 *
 */
@Controller
public class MetadataController {

	@Autowired
	BookAndShareService bookAndShareService;
	
	@Autowired
	HopAndBookService hopAndBookService;
	
	@Autowired
	UserTestimonialRepo userTestimonialRepo;
	
	@Autowired
	UserTestimonialService userTestimonialService;
	
	private static final Logger LOG = Logger.getLogger(MetadataController.class);
	
	@RequestMapping(value="/profile" ,method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView viewProfile(HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("registerationForm", new RegistrationForm());
		modelAndView.addObject("userProfile", CommonUtils.getProfileFromSession(httpServletRequest));
		modelAndView.setViewName(CommonConstants.PROFILE_PAGE);
		return modelAndView;
	}
	
	@RequestMapping(value="/instantBook" ,method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getInstantBooking(HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("registerationForm", new RegistrationForm());
		modelAndView.addObject("hopSearchRequestDto", new HopSearchRequestDto());
		modelAndView.addObject("registerationForm", new RegistrationForm());
		modelAndView.addObject("postRequestDto", new PostRequestDto());
		modelAndView.setViewName(CommonConstants.INSTANT_BOOK);
		return modelAndView;
	}
	
	@RequestMapping(value="/home" ,method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getHomePage() {
		List<TestimonialDto> testimonialDtos = userTestimonialService.getTopFiveTestimonial();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("hopSearchRequestDto", new HopSearchRequestDto());
		modelAndView.addObject("registerationForm", new RegistrationForm());
		modelAndView.addObject("postRequestDto", new PostRequestDto());
		modelAndView.addObject("testimonialList", testimonialDtos);
		modelAndView.setViewName(CommonConstants.HOME_PAGE);
		return modelAndView;
	}
	
	@RequestMapping(value="/getHopHistory" ,method = RequestMethod.GET)
	@ResponseBody
	public List<HopJourneyDto> getHopHistory(HttpServletRequest httpServletRequest) {
		UserProfile userProfile = CommonUtils.getProfileFromSession(httpServletRequest);
		List<HopJourneyDto> hopJourneyDtos = hopAndBookService.getHopHistoryForUser(userProfile.getEmail());
		return hopJourneyDtos;
	}
	
	@RequestMapping(value="/getPostHistory" ,method = RequestMethod.GET)
	@ResponseBody
	public List<PostJourneyDto> getPostHistory(HttpServletRequest httpServletRequest) {
		UserProfile userProfile = CommonUtils.getProfileFromSession(httpServletRequest);

		List<PostJourneyDto> postJourneyDtos = hopAndBookService.getPostHistoryForUser(userProfile.getEmail());
		return postJourneyDtos;
	}
	
	@RequestMapping(value="/submitTestimonial" ,method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse submitTestimonial(@RequestParam("text") String testimonial, HttpServletRequest httpServletRequest){
		
		UserProfile userProfile = CommonUtils.getProfileFromSession(httpServletRequest);
		String email = userProfile.getEmail();
		
		UserTestimonail userTestimonail = new UserTestimonail();
		userTestimonail.setEmail(email);
		userTestimonail.setTestimonial(testimonial);
		userTestimonail.setIsViewable(0);
		
		boolean isSaved = userTestimonialService.saveTestimonial(userTestimonail);
		CommonResponse commonResponse = new CommonResponse();
		if(!isSaved){
			commonResponse.setSuccess(isSaved);
			commonResponse.setMessage("Unable to post testimonial. Please try again.");
		}else{
			commonResponse.setSuccess(isSaved);
			commonResponse.setMessage("Thank you for your feedback.");
		}
		
		return commonResponse;
	}
	
}
