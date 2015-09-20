package com.bits.canvas.communication.email.template.loader;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import com.bits.canvas.communication.dto.MessageDto;

@Component(value="forgotPwdEmailTemplate")
public class ForgotPwdEmailTemplateLoader implements EmailTemplate{

private static final Logger LOG = Logger.getLogger(ForgotPwdEmailTemplateLoader.class);
	
	public String getEmailTemplate(MessageDto messageDto) {

		String message = null;
		try{
			VelocityEngine ve = new VelocityEngine();
			ve.init();

			Map<String,String> map = new HashMap<String,String>();
			map.put("userName", messageDto.getUser());
			map.put("newPassword", messageDto.getUserPassword());

			VelocityContext context = new VelocityContext();
			context.put("forgotEmail", map);

			Template t = ve.getTemplate( "forgotPwdEmailTemplate.vm" );
			StringWriter writer = new StringWriter();
			t.merge(context, writer);
			message = writer.toString();
		}
		catch(Exception e){
			e.printStackTrace();
			LOG.error("Exception while loading email template for forgot password Email : " + e.getMessage());
		}
		return message;
	}
}