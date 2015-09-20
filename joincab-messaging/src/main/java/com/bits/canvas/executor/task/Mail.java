package com.bits.canvas.executor.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.communication.dto.MessageDto;
import com.bits.canvas.communication.send.SendEmail;

@Component
public class Mail implements Runnable{
	
	@Autowired
	SendEmail sendEmail;
	
	private MessageDto messageDto;
	
	public Mail(){
		
	}
	
	public void setMessageDto(MessageDto messageDto) {
		this.messageDto = messageDto;
	}

	public Mail(MessageDto messageDto){
		this.messageDto = messageDto;
	}

	public void run() {
		sendEmail.sendEmailNotification(messageDto);
	}

}
