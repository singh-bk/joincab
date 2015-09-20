package com.bits.canvas.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bits.canvas.common.constant.CommonConstants;
import com.bits.canvas.response.UserProfile;

public class CommonUtils {

	
	public static String extractDateTime(String dateStr, String timeStr) {

		try {
			SimpleDateFormat sdfSource = new SimpleDateFormat("mm/dd/yyyy");
			Date date = sdfSource.parse(dateStr);
			SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-mm-dd");
			dateStr = sdfDestination.format(date);
			if (timeStr.contains("am")) {
				timeStr = timeStr.replace("am", "");
			} else if (timeStr.contains("pm")) {
				timeStr = timeStr.replace("pm", "");
			}
		} catch (Exception ex) {
			//TO-DO
		}
		return dateStr.trim() + " " + timeStr.trim();

	}
	
	public static List<BigInteger> getAllNeighboringLocationId(String neighborsStr){
		List<BigInteger> neighbors = new ArrayList<BigInteger>();
		StringTokenizer tokens = new StringTokenizer(neighborsStr, ",");
		while(tokens.hasMoreTokens()){
			neighbors.add(new BigInteger(tokens.nextToken()));
		}
		return neighbors;
	}
	
	public static String prepareMapUrl(String srcLandMark,String srcLocation,BigDecimal longitide, BigDecimal lattitude){
		String mapUrl = "https://www.google.com/maps/embed/v1/directions?key=AIzaSyBZTfYnlykYSrfdmNIPleMG7Iq3kGO9R5Q";
		mapUrl = mapUrl+"&origin="+srcLandMark+","+srcLocation+"&destination="+lattitude+","+longitide;
		return mapUrl;
	}
	
	public static String getLocationCode(String location){
		Random rand = new Random();
	    int randomNum = rand.nextInt((9999 - 1001) + 1) + 1001;

		String locationCode = location.replaceAll("[^\\w\\s\\-_]", "").replaceAll("\\s", "").substring(0,3).toUpperCase() + randomNum;
		return locationCode;
	}
	
	public static UserProfile getProfileFromSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
		return userProfile;
	}
	
	public static String uriBuilderForPlace(String address) throws Exception{
		StringBuilder uri = new StringBuilder("");
		uri.append(CommonConstants.GOOGLE_PLACE_TEXT_URI);
		uri.append(URLEncoder.encode(address, "UTF-8"));
		uri.append("&key="+CommonConstants.GOOGLE_API_KEY);
		return uri.toString();
	}
	
	public static boolean isDestWithinLimit(BigDecimal srclat,BigDecimal srcLng, BigDecimal destLat, BigDecimal destLng,Integer limit) {
		boolean isDestWithinLimit = false;
		double dLng = Math.toRadians((destLng.doubleValue() - srcLng.doubleValue()));
		double dLat = Math.toRadians((destLat.doubleValue() - srclat.doubleValue()));
		
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLng/2) * Math.sin(dLng/2) * Math.cos(Math.toRadians(srclat.doubleValue())) * Math.cos(Math.toRadians(destLat.doubleValue())); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = 6367000 * c;
		if(d<=limit){
			isDestWithinLimit = true;
		}
		return isDestWithinLimit;
	}
	
	public static boolean isLocationWithinLimit(float srclat,float srcLng, float destLat, float destLng,int limit) {
		boolean isDestWithinLimit = false;
		double dLng = Math.toRadians((destLng - srcLng));
		double dLat = Math.toRadians((destLat - srclat));
		
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLng/2) * Math.sin(dLng/2) * Math.cos(Math.toRadians(srclat)) * Math.cos(Math.toRadians(destLat)); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = 6367000 * c;
		if(d<=limit){
			isDestWithinLimit = true;
		}
		return isDestWithinLimit;
	}
	
	
	public static String getVerificationCode(){
		Random rand = new Random();
	    String randomNum = (rand.nextInt((9999 - 1001) + 1) + 1001)+"";
	    return randomNum;
	}
	
	public static boolean setAttributesToSession(String name,String value,HttpServletRequest request){
		boolean isSaved = false;
		HttpSession session = request.getSession(false);
		String value1 = (String) session.getAttribute(name);
		if(value1 == null || ("").equals(value1)){
			session.setAttribute(name, value);
			isSaved = true;
		}else if(!value1.equals(value)){
			isSaved = true;
		}
		else if(value1.equals(value)){
			isSaved = false;
		}
		return isSaved;
	}
	
	public static boolean validateAccessToken(UserProfile userProfile ,HttpServletRequest httpServletRequest){
		boolean isValid = false;
		String requestAccessToken = httpServletRequest.getHeader("accessToken");
		if(userProfile.getAccessToken().equals(requestAccessToken)){
			isValid = true;
		}else{
			throw new RuntimeException("some one trying to be smart");
		}
		return isValid;
	}
	
	public static String addMinutesToTime(String date1) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm");
		Date date = sdf.parse(date1);
		Date afterAddingTenMins=new Date(date.getTime() + (30 * 60000));
		String modifiedDate = sdf.format(afterAddingTenMins); 
		
		return modifiedDate;
		 
	}
	
	public static String subMinutesToTime(String date1) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm");
		Date date = sdf.parse(date1);
		Date afterAddingTenMins=new Date(date.getTime() - (30 * 60000));
		String modifiedDate = sdf.format(afterAddingTenMins); 
		
		return modifiedDate;
		 
	}
}
