package com.bits.canvas.web.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	
	private String addMinutesToTime(String date1) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm");
		Date date = sdf.parse(date1);
		Date afterAddingTenMins=new Date(date.getTime() + (30 * 60000));
		String modifiedDate = sdf.format(afterAddingTenMins); 
		
		return modifiedDate;
		 
	}
	
	private String subMinutesToTime(String date1) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm");
		Date date = sdf.parse(date1);
		Date afterAddingTenMins=new Date(date.getTime() - (30 * 60000));
		String modifiedDate = sdf.format(afterAddingTenMins); 
		
		return modifiedDate;
		 
	}
	
	public static void main(String[] args) throws ParseException {
		String inputdate = "2014-02-12 00:15";
		Test test = new Test();
		
		System.out.println("add : "+test.addMinutesToTime(inputdate));
		System.out.println("sun : "+test.subMinutesToTime(inputdate));
	}
}
