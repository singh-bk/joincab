package com.bits.canvas.communication.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bits.canvas.communication.email.template.loader.EmailTemplate;

@Component
public class IEmailFactory {

	@Autowired
	@Qualifier("hopEmailTemplate")
	EmailTemplate hopEmailTemplate;
	
	@Autowired
	@Qualifier("confirmEmailTemplate")
	EmailTemplate confirmEmailTemplate;
	
	@Autowired
	@Qualifier("welcomeEmailTemplate")
	EmailTemplate welcomeEmailTemplate;
	
	@Autowired
	@Qualifier("postEmailTemplate")
	EmailTemplate postEmailTemplate;
	
	@Autowired
	@Qualifier("utaxiEmailTemplate")
	EmailTemplate utaxiEmailTemplate;
	
	@Autowired
	@Qualifier("bookEmailTemplate")
	EmailTemplate bookEmailTemplate;
	
	@Autowired
	@Qualifier("forgotPwdEmailTemplate")
	EmailTemplate forgotPwdEmailTemplate;

	public EmailTemplate getInstance(String name){
		EmailTemplate emailTemplate = null;
		if(name.equals("HOP_EMAIL_TEMPLATE")){
			emailTemplate = hopEmailTemplate;
		}else if(name.equals("CONFIRM_EMAIL_TEMPLATE")){
			emailTemplate = confirmEmailTemplate;
		}else if(name.equals("WELCOME_EMAIL_TEMPLATE")){
			emailTemplate = welcomeEmailTemplate;
		}else if(name.equals("POST_EMAIL_TEMPLATE")){
			emailTemplate = postEmailTemplate;
		}else if(name.equals("UTAXI_EMAIL_TEMPLATE")){
			emailTemplate = utaxiEmailTemplate;
		}else if(name.equals("BOOK_EMAIL_TEMPLATE")){
			emailTemplate = bookEmailTemplate;
		}else if(name.equals("FORGOT_EMAIL_TEMPLATE")){
			emailTemplate = forgotPwdEmailTemplate;
		}
		return emailTemplate;
	}
}