package com.bits.canvas.communication.send;

import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.communication.dto.MessageDto;
import com.bits.canvas.communication.email.template.loader.EmailTemplate;
import com.bits.canvas.communication.factory.IEmailFactory;

@Component
public class SendEmail {

	private static final Logger LOG = Logger.getLogger(SendEmail.class);

	@Autowired
	IEmailFactory iEmailFactory;

	public boolean sendEmailNotification(MessageDto messageDto){

		boolean emailSent = false;
		EmailTemplate emailTemplate = iEmailFactory.getInstance(messageDto.getEmailType().name());

		//TODO Read the email subject using the emailType from the properties file
		String emailSubject = messageDto.getEmailSubject();

		String emailMessage = emailTemplate.getEmailTemplate(messageDto);

		if(emailMessage != null){
			messageDto.setEmailMessage(emailMessage);
			messageDto.setEmailSubject(emailSubject);
			emailSent = doSend(messageDto);
		}
		return emailSent;
	}

	public boolean doSend(MessageDto messageDto){

		boolean emailSent = false;

		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.office365.com");
		LOG.debug("sending email to : " + messageDto.getEmailId());
		email.setSmtpPort(587);
		email.setStartTLSEnabled(true);
		email.setAuthentication("info@thehytt.com", "Welcome@2015");

		try{
			email.addTo(messageDto.getEmailId(), messageDto.getEmailUserName());
			email.setFrom("info@thehytt.com","The Hytt");
			email.setSubject(messageDto.getEmailSubject());
			email.setHtmlMsg(messageDto.getEmailMessage());
			email.setTextMsg("Your email client does not support HTML messages");
			email.send();
			LOG.debug("Email sent to : " + messageDto.getEmailId());
			emailSent = true;
		}
		catch(Exception e){
			e.printStackTrace();
			LOG.error("Error while sending email to : " + messageDto.getEmailId() + " : Exception : " + e.getMessage());
		}
		return emailSent;
	}
}