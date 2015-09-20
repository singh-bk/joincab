package com.bits.canvas.communication.dto;

import com.bits.canvas.common.enums.EmailType;
import com.bits.canvas.common.enums.SMSType;

public class MessageDto {

	private String user;
	private String hopUser;
	private String postUser;
	private String hubNumber;
	private String verificationCode;
	private String joinMsgCode;
	private String hopUserNumber;
	private String postUserNumber;
	private String userPassword;
	
	public String recipientNumber;
	public String recipientMessage;
	
	private String emailId;
	private String emailUserName;
	private String emailSubject;
	private String emailMessage;
	private SMSType smsType;
	private EmailType emailType;
	
	//temp variable - change it
	private String bookEmailId;
	
	// For Vendors
	private String userAddress;
	private String dropPlace;
	private String vehicleType;
	private String journeyTime;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getHopUser() {
		return hopUser;
	}
	public void setHopUser(String hopUser) {
		this.hopUser = hopUser;
	}
	public String getPostUser() {
		return postUser;
	}
	public void setPostUser(String postUser) {
		this.postUser = postUser;
	}
	public String getHubNumber() {
		return hubNumber;
	}
	public void setHubNmber(String hubNumber) {
		this.hubNumber = hubNumber;
	}
	public String getRecipientNumber() {
		return recipientNumber;
	}
	public void setRecipientNumber(String recipientNumber) {
		this.recipientNumber = recipientNumber;
	}
	public String getRecipientMessage() {
		return recipientMessage;
	}
	public void setRecipientMessage(String recipientMessage) {
		this.recipientMessage = recipientMessage;
	}
	public String getJoinMsgCode() {
		return joinMsgCode;
	}
	public void setJoinMsgCode(String joinMsgCode) {
		this.joinMsgCode = joinMsgCode;
	}
	public String getHopUserNumber() {
		return hopUserNumber;
	}
	public void setHopUserNumber(String hopUserNumber) {
		this.hopUserNumber = hopUserNumber;
	}
	public String getPostUserNumber() {
		return postUserNumber;
	}
	public void setPostUserNumber(String postUserNumber) {
		this.postUserNumber = postUserNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEmailUserName() {
		return emailUserName;
	}
	public void setEmailUserName(String emailUserName) {
		this.emailUserName = emailUserName;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getEmailMessage() {
		return emailMessage;
	}
	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}
	public SMSType getSmsType() {
		return smsType;
	}
	public void setSmsType(SMSType smsType) {
		this.smsType = smsType;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public EmailType getEmailType() {
		return emailType;
	}
	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getDropPlace() {
		return dropPlace;
	}
	public void setDropPlace(String dropPlace) {
		this.dropPlace = dropPlace;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getJourneyTime() {
		return journeyTime;
	}
	public void setJourneyTime(String journeyTime) {
		this.journeyTime = journeyTime;
	}
	public String getBookEmailId() {
		return bookEmailId;
	}
	public void setBookEmailId(String bookEmailId) {
		this.bookEmailId = bookEmailId;
	}
}