package com.bits.canvas.mobile.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bits.canvas.mobile.enums.SourceChannel;
import com.bits.canvas.mobile.request.AuthRequest;
import com.bits.canvas.mobile.request.RideSearchRequest;
import com.bits.canvas.mobile.response.RideSearchResponse;

public class Test2 {

	public static void main(String[] args) throws Exception {
		URL url = new URL("http://localhost:8080/thehytt/search/rides");
		//URL url = new URL("http://localhost:8080/thehytt/auth/signin");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		try {
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setChunkedStreamingMode(0);
			
			String reqBody = "{\"srcLat\": 12.946653,\"srcLng\": 77.622825,\"srcFrmtAddr\": \"National Games Village Complex KHB Games Village, Ejipura Bengaluru, Karnataka 560095\","
					+"\"srcPlaceId\": \"abcdef1234\","
					+"\"destLat\": 12.852476,"
					+"\"destLng\": 12.852476,"
					+"\"destFrmtAddr\": \"National Games Village Complex KHB Games Village, Ejipura Bengaluru, Karnataka 560095\","
					+"\"destPlaceId\": \"abcdfgh4321\","
					+"\"travelTime\": 1429631392,"
					+"\"pps\": 100"
					+ "}";
		
			//String reqBody2 = "{\"sourceChannel\":3,\"email\":\"singh.bk@gmail.com\",\"pwd\":\"Welcome@2015\"}"; 
			//String reqBody2 = prepareInput1();
			String reqBody2 = prepareInput2();
			//System.out.println(reqBody);
			//byte[] outputInBytes = reqBody.getBytes("UTF-8");
			byte[] outputInBytes = reqBody2.getBytes("UTF-8");
			OutputStream out = new BufferedOutputStream(conn.getOutputStream());
			out.write(outputInBytes);
			out.close();
			conn.connect();
			//conn.setDoInput(true);
			// Start the query
			
			InputStream in = new BufferedInputStream(conn.getInputStream());
			String str = readStream(in);
			System.out.println(str);
			RideSearchResponse resp = (RideSearchResponse)JSONConvertor.jsonToObject(str, RideSearchResponse.class);
			
		} finally {
			conn.disconnect();
		}
	}
	
	private static String readStream(InputStream stream){
		StringBuilder resp = new StringBuilder();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			String line = "";
			while((line = reader.readLine()) != null){
				resp.append(line);
			}
		}catch(Exception ex){
			System.out.println(ex);
		}
		return resp.toString();
	}
	
	private static String prepareInput1() {

		String json = "";
		try {
			AuthRequest req = new AuthRequest();
			req.setSourceChannel(SourceChannel.CUSTOM.getSrcChannel());
			req.setEmail("singh.bk@gmail.com");
			req.setPwd("Welcome@2015");
			json = JSONConvertor.objectToJson(req);
		} catch (Exception ex) {

		}
		return json;
	}
	
	private static String prepareInput2() {

		String json = "";
		try {
			RideSearchRequest req = new RideSearchRequest();
			req.setSrcLat(Float.valueOf("12.946653"));
			req.setSrcLng(Float.valueOf("12.946653"));
			req.setSrcFrmtAddr("National Games Village Complex KHB Games Village, Ejipura Bengaluru, Karnataka 560095");
			req.setSrcPlaceId("abcdef1234");
			req.setDestLat(Float.valueOf("12.946653"));
			req.setDestLng(Float.valueOf("12.946653"));
			req.setDestFrmtAddr("National Games Village Complex KHB Games Village, Ejipura Bengaluru, Karnataka 560095");
			req.setDestPlaceId("abcdef1234");
			req.setTravelTime(1429631392l);
			req.setPps(150);
			json = JSONConvertor.objectToJson(req);
		} catch (Exception ex) {

		}
		return json;
	}
	
	
}
