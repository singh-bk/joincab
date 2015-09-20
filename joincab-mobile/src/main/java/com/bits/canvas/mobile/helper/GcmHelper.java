package com.bits.canvas.mobile.helper;

import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.request.GcmRequest;
import com.bits.canvas.mobile.test.JSONConvertor;

@Component
public class GcmHelper {

	public String createGcmRequest(String gcmRegId) {
		
		GcmRequest gcmRequest = new GcmRequest();
		//gcmRequest.addRegistrationId("APA91bFqnQzp0z5IpXWdth1lagGQZw1PTbdBAD13c-UQ0T76BBYVsFrY96MA4SFduBW9RzDguLaad-7l4QWluQcP6zSoX1HSUaAzQYSmI93hMJQUYEdRLpBOpmAGkjckuoVFt6icRjgXKuOpqZEedWUvVsKOqRruXuAe3mDbkimcNAMpc7XMF8M");
		//gcmRequest.setTo("APA91bFqnQzp0z5IpXWdth1lagGQZw1PTbdBAD13c-UQ0T76BBYVsFrY96MA4SFduBW9RzDguLaad-7l4QWluQcP6zSoX1HSUaAzQYSmI93hMJQUYEdRLpBOpmAGkjckuoVFt6icRjgXKuOpqZEedWUvVsKOqRruXuAe3mDbkimcNAMpc7XMF8M");
		gcmRequest.setTo(gcmRegId);
		String gcmReqJson = null;
		try{
			gcmReqJson = JSONConvertor.objectToJson(gcmRequest);
		}catch(Exception ex){
			//Do nothing
		}
		return gcmReqJson;
	}
}
