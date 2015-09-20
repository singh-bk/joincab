package com.bits.canvas.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bits.canvas.common.constant.CommonConstants;
import com.bits.canvas.common.enums.EmailType;
import com.bits.canvas.common.utils.CommonUtils;
import com.bits.canvas.communication.dto.MessageDto;
import com.bits.canvas.executor.task.Mail;
import com.bits.canvas.executor.task.Message;
import com.bits.canvas.persistence.entity.CabPostDetails;
import com.bits.canvas.persistence.entity.User;
import com.bits.canvas.persistence.service.UserService;
import com.bits.canvas.request.CancelRequestDto;
import com.bits.canvas.request.HopRequestDto;
import com.bits.canvas.request.PostRequestDto;
import com.bits.canvas.request.RegistrationForm;
import com.bits.canvas.response.AcceptResponse;
import com.bits.canvas.response.CancelResponse;
import com.bits.canvas.response.JoinResponse;
import com.bits.canvas.response.UserProfile;
import com.bits.canvas.services.BookAndShareService;
import com.bits.canvas.services.HopAndBookService;
import com.bits.canvas.transformer.EmailTransformer;
import com.bits.canvas.transformer.MessageTransformer;

/**
 * 18-DEC-2014
 * @author vatsritu
 *
 */
@Controller
public class BookAndShareController {

	@Autowired
	BookAndShareService bookAndShareService;
	
	@Autowired
	HopAndBookService hopAndBookService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TaskExecutor taskExecutor;
	
	@Autowired
	MessageTransformer messageTransformer;
	
	@Autowired
	EmailTransformer emailTransformer;
	
	@Autowired
	Message message;
	
	@Autowired
	Mail mail;
	/**
	 * Method to post the cab details to get the hoppers. 
	 * @param postRequestDto
	 * @return
	 */
	@RequestMapping(value = "/postCab.htm" , method = RequestMethod.POST)
	public ModelAndView postCab(@ModelAttribute(value = "postRequestDto")PostRequestDto postRequestDto, HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		boolean isSaved =CommonUtils.setAttributesToSession(CommonConstants.POST_TRANSCATION_ID, postRequestDto.getTransactionId(), request);
		if(isSaved){
			postRequestDto.setShare(1);
			CabPostDetails cabPostDetails = bookAndShareService.submitPostRequest(postRequestDto,request);
			if(cabPostDetails != null){
				User user = userService.getUserAccountsByEmail(cabPostDetails.getUserId());
				MessageDto messageDto = messageTransformer.preparePostMessageDto(user);
				
				message.setMessageDto(messageDto);
				//send Message
				taskExecutor.execute(message);
				
				//boolean isMessageSent = sendSMS.sendMessage(messageDto);
				
				messageDto.setEmailType(EmailType.POST_EMAIL_TEMPLATE);
				messageDto.setEmailSubject(CommonConstants.POST_EMAIL_TYPE);
				
				mail.setMessageDto(messageDto);
				//send Email
				taskExecutor.execute(mail);
				
				//sendEmail.sendEmailNotification(messageDto);
				modelAndView.addObject("registerationForm", new RegistrationForm());
				modelAndView.setViewName(CommonConstants.PROFILE_PAGE);
			}
		}else{
			modelAndView.addObject("registerationForm", new RegistrationForm());
			modelAndView.setViewName(CommonConstants.PROFILE_PAGE);
		}
		
		return modelAndView;
	}
	
	/**
	 * Book and share the cab
	 * @param hopSearchDto
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value="/bookCab.htm",method =  RequestMethod.POST)
	public ModelAndView bookCab(@ModelAttribute(value = "postRequestDto")PostRequestDto postRequestDto,HttpServletRequest httpServletRequest){
		
		ModelAndView modelAndView = new ModelAndView();
		boolean isSaved =CommonUtils.setAttributesToSession(CommonConstants.BOOK_TRANSCATION_ID, postRequestDto.getTransactionId(), httpServletRequest);
		if(isSaved){
			postRequestDto.setShare(1);
			CabPostDetails cabPostDetails = bookAndShareService.submitPostRequest(postRequestDto,httpServletRequest);
			if(cabPostDetails != null){
				User user = userService.getUserAccountsByEmail(cabPostDetails.getUserId());
				MessageDto messageDto = emailTransformer.prepareUTaxiEmailDto(user, cabPostDetails);
				
				mail.setMessageDto(messageDto);
				//send Mail
				taskExecutor.execute(mail);
				//boolean isEmailSent = sendEmail.sendEmailNotification(messageDto);
			
				//if(isEmailSent){
				MessageDto bookMessageDto = messageTransformer.prepareBookMessageDto(user,cabPostDetails);
				
				message.setMessageDto(bookMessageDto);
				//send Message
				taskExecutor.execute(message);
				//sendSMS.sendMessage(bookMessageDto);
				
				MessageDto bookEmailDto = emailTransformer.prepareBookEmailDto(user, cabPostDetails);
				
				mail.setMessageDto(bookEmailDto);
				//send Mail
				taskExecutor.execute(mail);
				//sendEmail.sendEmailNotification(bookEmailDto);
				
				//}
				/*List<JourneyDetailsDto> journeyDetailsList = bookAndShareService.getJourneyDetailsForUser(CommonUtils.getProfileFromSession(request).getEmail());		
				modelAndView.addObject("journeyDetailsList", journeyDetailsList);*/
				modelAndView.addObject("registerationForm", new RegistrationForm());
				modelAndView.setViewName(CommonConstants.PROFILE_PAGE);
			}
		}else{
			modelAndView.addObject("registerationForm", new RegistrationForm());
			modelAndView.setViewName(CommonConstants.PROFILE_PAGE);
		}
		
		return modelAndView;
	}
	
	/**
	 * Join cab if the hop search result found
	 * @param hopRequestDto
	 * @return
	 */
	@RequestMapping(value = "/joinCab" , method = RequestMethod.POST)
	@ResponseBody
	public JoinResponse joinCabRequest(@ModelAttribute(value = "HopRequestDto")HopRequestDto hopRequestDto,HttpServletRequest request){
		JoinResponse joinResponse = new JoinResponse();
		UserProfile userProfile = CommonUtils.getProfileFromSession(request);
		boolean isSubmitted = bookAndShareService.submitJoinRequest(hopRequestDto);
		CabPostDetails cabPostDetails = bookAndShareService.getPostDetailsByPostId(hopRequestDto.getPostId());
		User user = userService.getUserAccountsByEmail(cabPostDetails.getUserId());
		boolean isSent = false;
		if(isSubmitted){
			MessageDto messageDto = messageTransformer.prepareJoinMessageDto(user, cabPostDetails,userProfile);
		
			message.setMessageDto(messageDto);
			//send Message
			taskExecutor.execute(message);
			//isSent = sendSMS.sendMessage(messageDto);
			
			//Send Email
			MessageDto hopEmailDto = emailTransformer.prepareJoinEmailDto(user, cabPostDetails,userProfile);
			
			mail.setMessageDto(hopEmailDto);
			taskExecutor.execute(mail);
			//sendEmail.sendEmailNotification(hopEmailDto);
		}
		if(isSent){
			joinResponse.setSuccess(true);
			joinResponse.setMessage("Your requested has been sent to the poster. We will notify you once the user confirms.");
		}else{
			joinResponse.setSuccess(false);
			joinResponse.setMessage("Sorry unable to process the request.Please try again..");
		}
		return joinResponse;
	}
	
	/**
	 * Canceling the post request
	 * @param cancelRequestDto
	 * @return
	 */
	@RequestMapping(value = "/cancelPost" , method = RequestMethod.POST)
	@ResponseBody
	public CancelResponse cancelPostRequest(@RequestParam("postId") String postId){
		CancelResponse cancelResponse = new CancelResponse();
		boolean isCancelled = bookAndShareService.cancelPostReqest(postId);
		if(isCancelled){
			cancelResponse.setSuccess(isCancelled);
			cancelResponse.setMessage("Your requested has been processed successfully.");
		}else{
			cancelResponse.setSuccess(isCancelled);
			cancelResponse.setMessage("Sorry unable to process the request.Please try again..");
		}
		
		return cancelResponse;
	}
	
	/**
	 * Accept the hop request
	 * @param cancelRequestDto
	 * @return
	 */
	@RequestMapping(value = "/confirmHop" , method = RequestMethod.POST)
	@ResponseBody
	public AcceptResponse acceptHopRequest(@RequestParam("postId") String postId){
		AcceptResponse acceptResponse = new AcceptResponse();
		boolean isAccepted = bookAndShareService.acceptHopReqest(postId);
		if(isAccepted){
			acceptResponse.setSuccess(isAccepted);
			acceptResponse.setMessage("Your requested has been processed successfully.");
		}else{
			acceptResponse.setSuccess(isAccepted);
			acceptResponse.setMessage("Sorry unable to process the request.Please try again..");
		}
		
		return acceptResponse;
	}
	
	/**
	 * canceling the hop request
	 * @param cancelRequestDto
	 * @return
	 */
	@RequestMapping(value = "/cancelHop" , method = RequestMethod.POST)
	@ResponseBody
	public CancelResponse cancelHopRequest(@RequestParam("postId") String postId){
		CancelResponse cancelResponse = new CancelResponse();
		boolean isCancelled = bookAndShareService.cancelHopReqest(postId);
		if(isCancelled){
			cancelResponse.setSuccess(isCancelled);
			cancelResponse.setMessage("Your requested has been processed successfully.");
		}else{
			cancelResponse.setSuccess(isCancelled);
			cancelResponse.setMessage("Sorry unable to process the request.Please try again..");
		}
		
		return cancelResponse;
	}
	
	/**
	 * canceling the cab booking request
	 * @param cancelRequestDto
	 * @return
	 */
	@RequestMapping(value = "/cancelBooking.htm" , method = RequestMethod.POST)
	@ResponseBody
	public CancelResponse cancelBookingRequest(@ModelAttribute(value = "cancelRequest")CancelRequestDto cancelRequestDto){
		CancelResponse cancelResponse = new CancelResponse();
		boolean isCancelled = bookAndShareService.cancelCabBookRequest(cancelRequestDto);
		if(isCancelled){
			cancelResponse.setSuccess(isCancelled);
			cancelResponse.setMessage("Your requested has been processed successfully.");
		}else{
			cancelResponse.setSuccess(isCancelled);
			cancelResponse.setMessage("Sorry unable to process the request.Please try again..");
		}
		
		return cancelResponse;
	}

	 /**
     * canceling the hop request
     * @param cancelRequestDto
     * @return
     */
    @RequestMapping(value = "/hopDeny" , method = RequestMethod.POST)
    @ResponseBody
    public CancelResponse hopRequestDeny(@RequestParam("postId") String postId){
        CancelResponse cancelResponse = new CancelResponse();
        boolean isCancelled = bookAndShareService.denyHopReqest(postId);
        if(isCancelled){
            cancelResponse.setSuccess(isCancelled);
            cancelResponse.setMessage("Your requested has been processed successfully.");
        }else{
            cancelResponse.setSuccess(isCancelled);
            cancelResponse.setMessage("Sorry unable to process the request.Please try again..");
        }
        
        return cancelResponse;
    }
}
