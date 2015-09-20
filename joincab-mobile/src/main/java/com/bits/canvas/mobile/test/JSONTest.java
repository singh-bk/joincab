package com.bits.canvas.mobile.test;

public class JSONTest {

	public static void main(String[] args) throws Exception{
		String json1 = "{\"clientId\":\"patientId\",\"vendorId\":\"businessKey\"}";
		String json2 = "{\"patientId\":\"1234\",\"businessKey\":\"abcd\"}";
		Client client = (Client) JSONConvertor.jsonToObject(json1, Client.class);
		Patient patient = (Patient) JSONConvertor.jsonToObject(json2, Patient.class);
		
	}
}
