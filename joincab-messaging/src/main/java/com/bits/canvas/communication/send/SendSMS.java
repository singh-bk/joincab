package com.bits.canvas.communication.send;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.communication.dto.MessageDto;
import com.bits.canvas.communication.factory.IMessageTemplateFactory;
import com.bits.canvas.communication.msg.template.loader.MessageTemplate;

@Component
public class SendSMS{

	String accountSID;
	String authToken;
	String fromNumber;
	String messageAccountUser;
	String messageAccountPassword;
	String messageURL;

	@Autowired
	Properties messageProperties;

	@Autowired
	IMessageTemplateFactory iMessageTemplateFactory;

	public boolean sendMessage(MessageDto messageDto){

		boolean messageSent = false;
		MessageTemplate messageTemplate = iMessageTemplateFactory.getInstance(messageDto.getSmsType().name());
		String message = messageTemplate.getTemplate(messageDto);

		if(message != null){
			messageDto.setRecipientMessage(message);
			messageSent = doSend(messageDto);
		}
		return messageSent;
	}

	private boolean doSend(MessageDto messageDto){
		boolean messageSent = false;
		String rsp="";
		String retval = "";
		try {
			String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode("arjun.tomar13", "UTF-8");
			data += "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode("Welcome@2014", "UTF-8");
			data += "&" + URLEncoder.encode("to", "UTF-8") + "=" + URLEncoder.encode(messageDto.getRecipientNumber(), "UTF-8");
			data += "&" + URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(messageDto.getRecipientMessage(), "UTF-8");
			data += "&" + URLEncoder.encode("sid", "UTF-8") + "=" + URLEncoder.encode("WEBSMS", "UTF-8");
			data += "&" + URLEncoder.encode("fl", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
			data += "&" + URLEncoder.encode("gwid", "UTF-8") + "=" + URLEncoder.encode("2", "UTF-8");
			//Push the HTTP Request
			URL url = new URL("http://login.smsgatewayhub.com/smsapi/pushsms.aspx");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				retval += line;
			}
			wr.close();
			rd.close();
			rsp = retval;

			if(!rsp.equalsIgnoreCase("Failed#Invalid Mobile Numbers") && !rsp.equalsIgnoreCase("Failed#Insufficient Credits")){
				messageSent = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageSent;
	}
}