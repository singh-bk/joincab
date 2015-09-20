package com.bits.canvas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bits.canvas.common.enums.SMSType;
import com.bits.canvas.communication.dto.MessageDto;
import com.bits.canvas.communication.factory.IMessageTemplateFactory;
import com.bits.canvas.communication.send.SendSMS;
import com.bits.canvas.persistence.entity.CabHopDetails;
import com.bits.canvas.persistence.entity.User;
import com.bits.canvas.persistence.service.HopAndPostService;
import com.bits.canvas.persistence.service.RepositoryUserDetailService;

@Controller
public class MessageController {

	@Autowired
	RepositoryUserDetailService repositoryUserDetailService;

	@Autowired
	IMessageTemplateFactory iMessageTemplateFactory;

	@Autowired
	HopAndPostService hopAndPostService;

	@Autowired
	SendSMS sendSMS;

	private static final Logger LOG = Logger.getLogger(MessageController.class);

	@RequestMapping(value = "/recieveMessage", method = RequestMethod.GET)
	public void recieveMessage(HttpServletRequest request, HttpServletResponse response){

		String fromNumber = request.getParameter("who");
		String fromMessage = request.getParameter("what");

		LOG.debug("Recived Message from : " + fromNumber);
		LOG.debug("Recived Message content : " + fromMessage);

		String joinKey = null;
		String userResponse = null;

		String [] messageTokens = fromMessage.split(" ");

		if(messageTokens.length == 3){
			joinKey = messageTokens[1];
			userResponse = messageTokens[2];

			try{
				if(userResponse.equals("1")){
					User postUserAccounts = repositoryUserDetailService.getUserAccountByMobileNUmber(fromNumber);
					if(postUserAccounts!=null){
						String postUserId = postUserAccounts.getEmail();
						CabHopDetails cabHopDetails = hopAndPostService.getHopDetailsByPostIdAndJoinMsgCode(postUserId,joinKey);
						if(cabHopDetails!=null){
							String hopUserId = cabHopDetails.getUserId();
							hopUserId = hopUserId.trim();
							User hopUserAccounts = repositoryUserDetailService.getUserAccountsByEmail(hopUserId);
							String hopperMobileNumber = hopUserAccounts.getPhoneNumber();

							//Send Message to Post user
							MessageDto postMessageDto = new MessageDto();
							postMessageDto.setPostUser(postUserId);
							postMessageDto.setHopUser(hopUserId);
							postMessageDto.setHopUserNumber(hopperMobileNumber);
							postMessageDto.setRecipientNumber(fromNumber);
							postMessageDto.setSmsType(SMSType.CONFIRM_TEMPLATE);
							sendSMS.sendMessage(postMessageDto);

							//Send Message to hop user
							MessageDto hopMessageDto = new MessageDto();
							hopMessageDto.setHopUser(postUserId);
							hopMessageDto.setPostUser(hopUserId);
							hopMessageDto.setPostUserNumber(fromNumber);
							hopMessageDto.setRecipientNumber(hopperMobileNumber);
							hopMessageDto.setSmsType(SMSType.CONFIRM_TEMPLATE);
							sendSMS.sendMessage(hopMessageDto);
						}
					}
				}else if(userResponse.equals("2")){

					User postUserAccounts = repositoryUserDetailService.getUserAccountByMobileNUmber(fromNumber);
					String postUserId = postUserAccounts.getEmail();
					CabHopDetails cabHopDetails = hopAndPostService.getHopDetailsByPostIdAndJoinMsgCode(postUserId,joinKey);
					String hopUserId = cabHopDetails.getUserId();
					hopUserId = hopUserId.trim();
					User hopUserAccounts = repositoryUserDetailService.getUserAccountsByEmail(hopUserId);
					String hopperMobileNumber = hopUserAccounts.getPhoneNumber();

					MessageDto hopMessageDto = new MessageDto();
					hopMessageDto.setHopUser(hopUserId);
					hopMessageDto.setPostUser(postUserId);
					hopMessageDto.setRecipientNumber(hopperMobileNumber);
					hopMessageDto.setSmsType(SMSType.REJECT_TEMPLATE);
					sendSMS.sendMessage(hopMessageDto);
				}
			}
			catch(Exception e){
				LOG.error("Exception occurred in Recieve/Sending Message reply to users: " + e.getMessage());
				e.printStackTrace();
			}
		}
		else{
			LOG.error("The incoming message from " + fromNumber + " is incorrect. The message is: " + fromMessage);
		}
	}
}