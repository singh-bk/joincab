package com.bits.canvas.transformer;

import org.springframework.stereotype.Component;

import com.bits.canvas.common.enums.SMSType;
import com.bits.canvas.communication.dto.MessageDto;
import com.bits.canvas.persistence.entity.CabPostDetails;
import com.bits.canvas.persistence.entity.User;
import com.bits.canvas.response.UserProfile;

@Component
public class MessageTransformer {

	public MessageDto prepareJoinMessageDto(User user, CabPostDetails cabPostDetails,UserProfile userProfile){

		MessageDto messageDto = new MessageDto();
		messageDto.setHopUser(userProfile.getFirstName()+" "+userProfile.getLastName());
		messageDto.setPostUser(user.getFirstName());
		messageDto.setHubNmber("");
		messageDto.setJoinMsgCode(cabPostDetails.getJoinMsgCode());
		messageDto.setRecipientNumber(user.getPhoneNumber().trim());
		messageDto.setHubNmber("9810805053");
		messageDto.setSmsType(SMSType.HOP_TEMPLATE);

		return messageDto;
	}
	
	public MessageDto prepareacceptMessageDtoForPoster(User postUser, User hopUser){

		MessageDto postMessageDto = new MessageDto();
		postMessageDto.setPostUser(postUser.getUserName());
		postMessageDto.setHopUser(hopUser.getUserName());
		postMessageDto.setHopUserNumber(hopUser.getPhoneNumber());
		postMessageDto.setRecipientNumber(postUser.getPhoneNumber());
		postMessageDto.setSmsType(SMSType.CONFIRM_TEMPLATE);

		return postMessageDto;
	}
	
	public MessageDto prepareacceptMessageDtoForHopper(User postUser, User hopUser){

		MessageDto hopMessageDto = new MessageDto();
		hopMessageDto.setHopUser(postUser.getUserName());
		hopMessageDto.setPostUser(hopUser.getUserName());
		hopMessageDto.setHopUserNumber(postUser.getPhoneNumber());
		hopMessageDto.setRecipientNumber(hopUser.getPhoneNumber());
		hopMessageDto.setSmsType(SMSType.CONFIRM_TEMPLATE);

		return hopMessageDto;
	}
	
	public MessageDto prepareRejectMessageDto(User user){

		MessageDto messageDto = new MessageDto();
		messageDto.setRecipientNumber(user.getPhoneNumber());
		messageDto.setHopUser(user.getUserName());
		messageDto.setSmsType(SMSType.REJECT_TEMPLATE);

		return messageDto;
	}

	public MessageDto prepareCancelByHopMessageDto(User user){

		MessageDto messageDto = new MessageDto();
		messageDto.setRecipientNumber(user.getPhoneNumber());
		messageDto.setHopUser(user.getUserName());
		messageDto.setSmsType(SMSType.CANCEL_BY_HOP_TEMPLATE);

		return messageDto;
	}

	public MessageDto prepareCancelByPostMessageDto(User user){

		MessageDto messageDto = new MessageDto();
		messageDto.setRecipientNumber(user.getPhoneNumber());
		messageDto.setHopUser(user.getUserName());
		messageDto.setSmsType(SMSType.CANCEL_BY_POST_TEMPLATE);

		return messageDto;
	}

	public MessageDto prepareForotPwdMessageDto(User user,String password){

		MessageDto messageDto = new MessageDto();
		messageDto.setRecipientNumber(user.getPhoneNumber());
		messageDto.setUser(user.getUserName());
		messageDto.setUserPassword(password);
		messageDto.setSmsType(SMSType.FORGOT_PWD_TEMPLATE);

		return messageDto;
	}
	
	public MessageDto preparePostMessageDto(User user){

		MessageDto messageDto = new MessageDto();
		messageDto.setRecipientNumber(user.getPhoneNumber());
		messageDto.setUser(user.getUserName());
		messageDto.setEmailId(user.getEmail());
		messageDto.setEmailUserName(user.getUserName());
		messageDto.setSmsType(SMSType.POST_TEMPLATE);
		return messageDto;
	}

	public MessageDto prepareBookMessageDto(User user, CabPostDetails cabPostDetails){

		MessageDto messageDto = new MessageDto();
		messageDto.setRecipientNumber(user.getPhoneNumber());
		messageDto.setUser(user.getUserName());
		messageDto.setEmailId(user.getEmail());
		messageDto.setEmailUserName(user.getUserName());
		messageDto.setJourneyTime(cabPostDetails.getPickUpTime());
		messageDto.setSmsType(SMSType.BOOK_TEMPLATE);
		return messageDto;
	}
}
