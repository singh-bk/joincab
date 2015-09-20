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

@Component(value="bookEmailTemplate")
public class BookEmailTemplateLoader implements EmailTemplate{

	private static final Logger LOG = Logger.getLogger(BookEmailTemplateLoader.class);
	
	public String getEmailTemplate(MessageDto messageDto) {

		String message = null;
		try{
			VelocityEngine ve = new VelocityEngine();
			ve.init();

			Map<String,String> map = new HashMap<String,String>();
			map.put("userNumber", messageDto.getPostUserNumber());
			map.put("userAddress", messageDto.getUserAddress());
			map.put("userName", messageDto.getPostUser());
			map.put("dropPlace", messageDto.getDropPlace());
			map.put("vehicleType", messageDto.getVehicleType());
			map.put("userEmail", messageDto.getBookEmailId());
			map.put("userJourneyTime", messageDto.getJourneyTime());

			VelocityContext context = new VelocityContext();
			context.put("utaxiEmail", map);

			Template t = ve.getTemplate( "bookEmailTemplate.vm" );
			StringWriter writer = new StringWriter();
			t.merge(context, writer);
			message = writer.toString();
		}
		catch(Exception e){
			e.printStackTrace();
			LOG.error("Exception while loading email template for Book Email to user : " + e.getMessage());
		}
		return message;
	}
}