package com.bits.canvas.mobile.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.common.CommonConstants;
import com.bits.canvas.mobile.response.GcmResponse;
import com.bits.canvas.mobile.test.JSONConvertor;

//https://developers.google.com/cloud-messaging/http
@Component
public class GcmService {

	public GcmResponse sendGcmNotification(String gcmUrl, String gcmReqJson)  {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(gcmUrl);
		HttpEntity entity = new StringEntity(gcmReqJson,
				ContentType.create(CommonConstants.APPLICATION_JSON));
		httpPost.setEntity(entity);
		httpPost.setHeader(CommonConstants.AUTHORIZATION, CommonConstants.AUTH_KEY_GCM);
		httpPost.setHeader(CommonConstants.CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
		GcmResponse gcmResponse  = null;
		try{
			HttpResponse response = httpClient.execute(httpPost);
	        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        String op = "";
	        String str = "";
	        while((str = rd.readLine()) != null){
	        	op = op+str;
	        }
	        System.out.println(op);
	        gcmResponse = (GcmResponse)JSONConvertor.jsonToObject(op, GcmResponse.class);
		}catch(Exception ex){
			//TODO 
		}
        return gcmResponse;
	}

}
