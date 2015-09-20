package com.bits.canvas.mobile.test;

import java.io.IOException;

import com.bits.canvas.mobile.request.HopRequest;
import com.bits.canvas.mobile.request.JoinRequest;
import com.bits.canvas.mobile.request.PostRequest;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class Test {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		PostRequest req = new PostRequest();
		req.setDestAddress("Hewlett-Packard HP Avenue, 39/40, Hosur RoadOpp. Crowne Plaza, Electronics City Konappana Agrahara, Electronic CityBengaluru, Karnataka 560100");
		req.setDestLat(12.852476f);
		req.setDestLng(77.661487f);
		req.setPps(200);
		req.setSeatsOffered(3);
		req.setSrcAddress("National Games Village Complex KHB Games Village, Ejipura Bengaluru, Karnataka 560095");
		req.setSrcLat(12.946653f);
		req.setSrcLng(77.622825f);
		req.setTravelTime(1429631392l);
		req.setUserId("lalbabu@gmail.com");
		
		System.out.println(JSONConvertor.objectToJson(req));
		
		HopRequest hop = new HopRequest();
		hop.setDestAddress("Hewlett-Packard HP Avenue, 39/40, Hosur RoadOpp. Crowne Plaza, Electronics City Konappana Agrahara, Electronic CityBengaluru, Karnataka 560100");
		hop.setDestLat(12.852476f);
		hop.setDestLng(77.661487f);
		hop.setPps(200);
		hop.setSrcAddress("National Games Village Complex KHB Games Village, Ejipura Bengaluru, Karnataka 560095");
		hop.setSrcLat(12.946653f);
		hop.setSrcLng(77.622825f);
		hop.setTravelTime(1429631392l);
		hop.setUserId("lalbabu@gmail.com");
		
		System.out.println(JSONConvertor.objectToJson(hop));
		
		JoinRequest join = new JoinRequest();
		join.setRecipientid("abcd1234post");
		join.setRequestorId("abcd1234hop");
		join.setRequestType(1);
		
		System.out.println(JSONConvertor.objectToJson(join));
	}
}
