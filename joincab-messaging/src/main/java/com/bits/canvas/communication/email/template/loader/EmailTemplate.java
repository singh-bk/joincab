package com.bits.canvas.communication.email.template.loader;

import com.bits.canvas.communication.dto.MessageDto;

public interface EmailTemplate {

	public String getEmailTemplate(MessageDto messageDto);
	
}
