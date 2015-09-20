package com.bits.canvas.communication.msg.template.loader;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bits.canvas.communication.dto.MessageDto;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component(value="rejectMsgTemplate")
public class RejectMsgTemplateLoader implements MessageTemplate{

private static final Logger LOG = Logger.getLogger(RejectMsgTemplateLoader.class);
	
	public String getTemplate(MessageDto messageDto){

		String message = null;

		Configuration cfg = new Configuration();
		try {
			Template template = cfg.getTemplate("rejectMessageTemplate.ftl");

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("hopUser", messageDto.getHopUser());

			Writer out = new StringWriter();
			template.process(data, out);
			message = out.toString();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("IO Exception occurred in reading the reject FTL file : " + e.getMessage());
		} catch (TemplateException e) {
			e.printStackTrace();
			LOG.error("Template Exception occurred while creating the reject message : " + e.getMessage());
		}
		return message;
	}
}