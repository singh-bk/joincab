package com.bits.canvas.mobile.service;

import com.bits.canvas.mobile.common.CommonConstants;
import com.bits.canvas.mobile.request.GcmRequest;
import com.bits.canvas.mobile.response.GcmResponse;
import com.bits.canvas.mobile.test.JSONConvertor;

public class GcmServiceTest {

	public static void main(String[] args) throws Exception {
		GcmService gcmService = new GcmService();
		String gcmUrl = CommonConstants.GCM_URL;
		GcmRequest gcmRequest = new GcmRequest();
		//gcmRequest.addRegistrationId("APA91bFqnQzp0z5IpXWdth1lagGQZw1PTbdBAD13c-UQ0T76BBYVsFrY96MA4SFduBW9RzDguLaad-7l4QWluQcP6zSoX1HSUaAzQYSmI93hMJQUYEdRLpBOpmAGkjckuoVFt6icRjgXKuOpqZEedWUvVsKOqRruXuAe3mDbkimcNAMpc7XMF8M");
		gcmRequest.setTo("APA91bFqnQzp0z5IpXWdth1lagGQZw1PTbdBAD13c-UQ0T76BBYVsFrY96MA4SFduBW9RzDguLaad-7l4QWluQcP6zSoX1HSUaAzQYSmI93hMJQUYEdRLpBOpmAGkjckuoVFt6icRjgXKuOpqZEedWUvVsKOqRruXuAe3mDbkimcNAMpc7XMF8M");
		String gcmReqJson = JSONConvertor.objectToJson(gcmRequest);		

		GcmResponse response = gcmService.sendGcmNotification(gcmUrl, gcmReqJson);
		System.out.println(response);
		System.out.println(response.getResults().get(0).getError());
	}
}
