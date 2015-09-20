package com.bits.canvas.services;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.bits.canvas.common.constant.HopStatus;
import com.bits.canvas.common.constant.PostStatus;
import com.bits.canvas.communication.dto.MessageDto;
import com.bits.canvas.executor.task.Message;
import com.bits.canvas.persistence.entity.CabHopDetails;
import com.bits.canvas.persistence.entity.CabPostDetails;
import com.bits.canvas.persistence.entity.User;
import com.bits.canvas.persistence.service.HopAndPostService;
import com.bits.canvas.persistence.service.UserService;
import com.bits.canvas.request.CancelRequestDto;
import com.bits.canvas.request.HopRequestDto;
import com.bits.canvas.request.PostRequestDto;
import com.bits.canvas.transformer.MessageTransformer;
import com.bits.canvas.transformer.ModelTransformer;

/**
 * 18-DEC-2014
 * @author vatsritu
 *
 */
@Component
public class BookAndShareService {

	@Autowired
	HopAndPostService hopAndPostService;

	@Autowired
	ModelTransformer modelTransformer;
	
	@Autowired
	MessageTransformer messageTransformer;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TaskExecutor taskExecutor;
	
	@Autowired
	Message message;
	
	/**
	 * saving the request in the case of user is having the cab and looking for hopper
	 * @param postRequestDto
	 * @return
	 */
	public CabPostDetails submitPostRequest(PostRequestDto postRequestDto,HttpServletRequest request){
		CabPostDetails cabPostDetails = modelTransformer.preparePostRequest(postRequestDto,request);
		cabPostDetails = hopAndPostService.savePostRequest(cabPostDetails);
		return cabPostDetails;
	}

	/**
	 * submitting the join request for hopper
	 * @param hopRequestDto
	 * @return
	 */
	public boolean submitJoinRequest(HopRequestDto hopRequestDto){
		boolean isRequested = false;
		boolean isSubmitted = submitHopRequest(hopRequestDto);
		if(isSubmitted){
			isRequested = updatePostDetailsForJoin(hopRequestDto);
		}
		return isRequested;
	}

	/**
	 * submitting the hop request details
	 * @param hopRequestDto
	 * @return
	 */
	public boolean submitHopRequest(HopRequestDto hopRequestDto){
		boolean isSaved =false;
		try {
			CabHopDetails cabHopDetails = new CabHopDetails();
			BeanUtils.copyProperties(cabHopDetails, hopRequestDto);
			cabHopDetails.setStatus(HopStatus.REQUESTED.getCode());
			cabHopDetails.setHopperCount(1);
			isSaved = hopAndPostService.saveHopRequest(cabHopDetails);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSaved;
	}
	
	/**
	 * updating the post status in the case of request received
	 * @param hopRequestDto
	 * @return
	 */
	public boolean updatePostDetailsForJoin(HopRequestDto hopRequestDto){
		CabPostDetails cabPostDetails = hopAndPostService.getPostDetailsById(hopRequestDto.getPostId());
		cabPostDetails.setPostStatus(PostStatus.REQUEST_RECEIVED.getCode());
		cabPostDetails = hopAndPostService.savePostRequest(cabPostDetails);
		return cabPostDetails!=null;
	}
	
	/**
	 * poster canceling their request
	 * @param cancelRequestDto
	 * @return
	 */
	public boolean cancelPostReqest(String postId){
		CabPostDetails cabPostDetails = hopAndPostService.getPostDetailsById(postId);
		if(cabPostDetails.getPostStatus() == PostStatus.REQUEST_CONFIRMED.getCode() ){
			CabHopDetails cabHopDetails = hopAndPostService.getHopDetailsByPostIdAndStatus(postId,HopStatus.JOINED.getCode());
			cabHopDetails.setStatus(HopStatus.WATCH.getCode());
			hopAndPostService.saveHopRequest(cabHopDetails);
			User user = userService.getUserAccountsByEmail(cabHopDetails.getUserId());
			MessageDto messageDto = messageTransformer.prepareCancelByPostMessageDto(user);
			message.setMessageDto(messageDto);
			taskExecutor.execute(message);
		}else if(cabPostDetails.getPostStatus() == PostStatus.REQUEST_RECEIVED.getCode()){
			CabHopDetails cabHopDetails = hopAndPostService.getHopDetailsByPostIdAndStatus(postId,HopStatus.REQUESTED.getCode());
			cabHopDetails.setStatus(HopStatus.WATCH.getCode());
			hopAndPostService.saveHopRequest(cabHopDetails);
			User user = userService.getUserAccountsByEmail(cabHopDetails.getUserId());
			MessageDto messageDto = messageTransformer.prepareCancelByPostMessageDto(user);
			message.setMessageDto(messageDto);
			taskExecutor.execute(message);
		}
		cabPostDetails.setPostStatus(PostStatus.POST_CANCELLED.getCode());
		cabPostDetails = hopAndPostService.savePostRequest(cabPostDetails);
		return cabPostDetails!=null;
	}
	
	/**
	 * poster accepting their request
	 * @param cancelRequestDto
	 * @return
	 */
	public boolean acceptHopReqest(String postId){
		CabPostDetails cabPostDetails = hopAndPostService.getPostDetailsById(postId);
			CabHopDetails cabHopDetails = hopAndPostService.getHopDetailsByPostIdAndStatus(postId,HopStatus.REQUESTED.getCode());
			cabHopDetails.setStatus(HopStatus.JOINED.getCode());
			hopAndPostService.saveHopRequest(cabHopDetails);
			
		User postUser = userService.getUserAccountsByEmail(cabPostDetails.getUserId());
		User hopUser = userService.getUserAccountsByEmail(cabHopDetails.getUserId());
		cabPostDetails.setPostStatus(PostStatus.REQUEST_CONFIRMED.getCode());
		cabPostDetails = hopAndPostService.savePostRequest(cabPostDetails);
		
		//Send Message to Post user
		MessageDto postMessageDto = messageTransformer.prepareacceptMessageDtoForPoster(postUser, hopUser);
		message.setMessageDto(postMessageDto);
		taskExecutor.execute(message);

		//Send Message to hop user
		MessageDto hopMessageDto = messageTransformer.prepareacceptMessageDtoForHopper(postUser, hopUser);
		message.setMessageDto(hopMessageDto);
		taskExecutor.execute(message);
		return cabPostDetails!=null;
	}
	
	
	/**
	 * Hopper canceling the hop request
	 * @param cancelRequestDto
	 * @return
	 */
	public boolean cancelHopReqest(String postId){
		CabHopDetails cabHopDetails = hopAndPostService.getHopDetailsByPostId(postId);
		if(cabHopDetails.getStatus() == HopStatus.JOINED.getCode()){
			CabPostDetails cabPostDetails = hopAndPostService.getPostDetailsById(postId);
			cabPostDetails.setPostStatus(PostStatus.POSTED.getCode());
			hopAndPostService.savePostRequest(cabPostDetails);
			User user = userService.getUserAccountsByEmail(cabPostDetails.getUserId());
			MessageDto messageDto = messageTransformer.prepareCancelByHopMessageDto(user);

			message.setMessageDto(messageDto);
			taskExecutor.execute(message);
		}
		cabHopDetails.setStatus(HopStatus.CANCELLED.getCode());
		boolean isSaved = hopAndPostService.saveHopRequest(cabHopDetails);
		return isSaved;
	}
	
	
	/**
	 * canceling the cab booking as well as post and hop if it is shared
	 * @param cancelRequestDto
	 * @return
	 */
	public boolean cancelCabBookRequest(CancelRequestDto cancelRequestDto){
		boolean isCancelled = false;
		if(cancelRequestDto.isShared()){
		isCancelled = cancelPostReqest(cancelRequestDto.getPostId());
		}
		if(isCancelled){
			/*//CabBook cabBook = hopAndPostService.getCabBookingDetailsById(cancelRequestDto.getBookId());
			cabBook.setBookingStatus(BookStatus.CACELLED.getCode());
			cabBook = hopAndPostService.saveBookingRequest(cabBook);
			if(cabBook==null)isCancelled = false;*/
		}
		return isCancelled;
	}
	
	/**
	 * getting the post details for communication
	 * @param postId
	 * @return
	 */
	public CabPostDetails getPostDetailsByPostId(String postId){
		CabPostDetails cabPostDetails = null;
		try {
			cabPostDetails = hopAndPostService.getPostDetailsById(postId);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return cabPostDetails;
	}
	
	/**
     * poster deny their request
     * @param cancelRequestDto
     * @return
     */
	 public boolean denyHopReqest(String postId){
	        CabPostDetails cabPostDetails = hopAndPostService.getPostDetailsById(postId);
	            CabHopDetails cabHopDetails = hopAndPostService.getHopDetailsByPostIdAndStatus(postId,HopStatus.REQUESTED.getCode());
	            cabHopDetails.setStatus(HopStatus.CANCELLED.getCode());
	            hopAndPostService.saveHopRequest(cabHopDetails);
	            
	        User hopUser = userService.getUserAccountsByEmail(cabHopDetails.getUserId());
	        cabPostDetails.setPostStatus(PostStatus.POSTED.getCode());
	        cabPostDetails = hopAndPostService.savePostRequest(cabPostDetails);
	        

	        //Send Message to hop user
	        MessageDto hopMessageDto = messageTransformer.prepareRejectMessageDto(hopUser);
	        message.setMessageDto(hopMessageDto);
	        taskExecutor.execute(message);
	        return cabPostDetails!=null;
	    }
}