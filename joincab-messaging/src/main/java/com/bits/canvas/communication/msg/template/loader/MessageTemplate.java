package com.bits.canvas.communication.msg.template.loader;

import com.bits.canvas.communication.dto.MessageDto;

public interface MessageTemplate {

	public String getTemplate(MessageDto messageDto);
	
}
