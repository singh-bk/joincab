package com.bits.canvas.mobile.test;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConvertor {

	public static Object jsonToObject(String json, Class objClass) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		Object obj = mapper.readValue(json,  objClass);
		return obj;
	}
	
	public static String objectToJson(Object object) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, object);
		return writer.getBuffer().toString();
	}
}
