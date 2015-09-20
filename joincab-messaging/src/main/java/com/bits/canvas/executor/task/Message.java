package com.bits.canvas.executor.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.communication.dto.MessageDto;
import com.bits.canvas.communication.send.SendSMS;

@Component
public class Message implements Runnable{

	MessageDto messageDto;
	
	@Autowired
	SendSMS sendSMS;
	
	public Message(){
		
	}
	
	public Message(MessageDto messageDto){
		this.messageDto = messageDto;
	}
	
	public void setMessageDto(MessageDto messageDto) {
		this.messageDto = messageDto;
	}

	public void run() {
		sendSMS.sendMessage(messageDto);
	}

}
