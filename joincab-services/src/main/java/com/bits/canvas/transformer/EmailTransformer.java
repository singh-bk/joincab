package com.bits.canvas.transformer;

import org.springframework.stereotype.Component;

import com.bits.canvas.common.constant.CommonConstants;
import com.bits.canvas.common.enums.EmailType;
import com.bits.canvas.common.enums.SMSType;
import com.bits.canvas.common.enums.VehicleType;
import com.bits.canvas.communication.dto.MessageDto;
import com.bits.canvas.persistence.entity.CabPostDetails;
import com.bits.canvas.persistence.entity.User;
import com.bits.canvas.response.UserProfile;

@Component
public class EmailTransformer {

	public MessageDto prepareUTaxiEmailDto(User user, CabPostDetails cabPostDetails){

		MessageDto messageDto = new MessageDto();

		messageDto.setPostUserNumber(user.getPhoneNumber());
		messageDto.setUserAddress(cabPostDetails.getPostFormattedAddress());
		messageDto.setPostUser(user.getUserName());
		messageDto.setDropPlace("Bengaluru Airport");
		messageDto.setVehicleType(VehicleType.findByValue(cabPostDetails.getVehicleType()).name());
		messageDto.setBookEmailId(user.getEmail());
		messageDto.setEmailId("info@thehytt.com");
		messageDto.setJourneyTime(cabPostDetails.getPickUpTime());

		messageDto.setEmailType(EmailType.UTAXI_EMAIL_TEMPLATE);
		messageDto.setEmailSubject(CommonConstants.UTAXI_EMAIL_TYPE);

		return messageDto;
	}

	public MessageDto prepareBookEmailDto(User user, CabPostDetails cabPostDetails){

		MessageDto messageDto = new MessageDto();

		messageDto.setPostUserNumber(user.getPhoneNumber());
		messageDto.setUserAddress(cabPostDetails.getPostFormattedAddress());
		messageDto.setPostUser(user.getUserName());
		messageDto.setDropPlace("Bengaluru Airport");
		messageDto.setVehicleType(VehicleType.findByValue(cabPostDetails.getVehicleType()).name());
		messageDto.setBookEmailId(user.getEmail());
		messageDto.setEmailId(user.getEmail());
		messageDto.setJourneyTime(cabPostDetails.getPickUpTime());

		messageDto.setEmailType(EmailType.BOOK_EMAIL_TEMPLATE);
		messageDto.setEmailSubject(CommonConstants.BOOK_EMAIL_TYPE);

		return messageDto;
	}

	public MessageDto prepareJoinEmailDto(User user, CabPostDetails cabPostDetails,UserProfile userProfile){

		MessageDto messageDto = new MessageDto();

		messageDto.setHopUser(userProfile.getFirstName()+" "+userProfile.getLastName());
		messageDto.setPostUser(user.getFirstName());
		messageDto.setEmailType(EmailType.HOP_EMAIL_TEMPLATE);
		messageDto.setEmailSubject(CommonConstants.HOP_EMAIL_TYPE);
		messageDto.setEmailId(user.getEmail());
		messageDto.setEmailUserName(user.getUserName());

		return messageDto;
	}

	public MessageDto prepareForgotEmailDto(User user,String password){

		MessageDto messageDto = new MessageDto();

		messageDto.setUser(user.getUserName());
		messageDto.setUserPassword(password);
		messageDto.setEmailType(EmailType.FORGOT_EMAIL_TEMPLATE);
		messageDto.setEmailSubject(CommonConstants.FORGOT_EMAIL_TEMPLATE);
		messageDto.setEmailId(user.getEmail());

		return messageDto;
	}
	
	public MessageDto prepareWelcomeEmailDto(User user){

		MessageDto messageDto = new MessageDto();
		messageDto.setUser(user.getUserName());
		messageDto.setEmailId(user.getEmail());
		messageDto.setEmailUserName(user.getUserName());
		messageDto.setEmailType(EmailType.WELCOME_EMAIL_TEMPLATE);
		return messageDto;
	}
}