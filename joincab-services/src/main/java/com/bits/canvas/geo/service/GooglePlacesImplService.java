package com.bits.canvas.geo.service;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.common.dto.Location;
import com.bits.canvas.common.utils.CommonUtils;
import com.bits.canvas.google.model.GooglePlacesResponse;
import com.bits.canvas.google.model.Result;
import com.bits.canvas.request.HopSearchRequestDto;
import com.bits.canvas.request.PostRequestDto;
import com.bits.canvas.transformer.ModelTransformer;
import com.google.gson.Gson;

@Component
public class GooglePlacesImplService {

	@Autowired
	ModelTransformer modelTransformer;
	
	public Location populateLatLngInLocation(Location location){

		String address = location.getLocationName();
		try{
			GooglePlacesResponse response = getPlaceDetails(address);
			if(response != null && response.getStatus().equalsIgnoreCase("OK")){
				List<Result> resultList = response.getResults(); 
				if(resultList.size() >=1){
					location.setIsAccurate(true);
					
					Result result = resultList.get(0);
					location.setFormattedAddress(result.getFormattedAddress());
					BigDecimal lat = new BigDecimal(result.getGeometry().location.lat);
					lat = lat.setScale(16, BigDecimal.ROUND_UP);
					location.setLatitude(lat);
					BigDecimal lng = new BigDecimal(result.getGeometry().location.lng);
					lng = lng.setScale(16, BigDecimal.ROUND_UP);
					location.setLongitude(lng);
					location.setPlaceId(result.getPlaceId());
				}
				else{
					location.setIsAccurate(false);
				}
			}
		}catch(Exception ex){
			//TODO
		}
		return location;
	}
	

	
	public GooglePlacesResponse getPlaceDetails(String address) throws Exception{

		String uri = CommonUtils.uriBuilderForPlace(address);
		GooglePlacesResponse response = new GooglePlacesResponse();
		HttpURLConnection conn = null;
	    StringBuilder jsonResults = new StringBuilder();	
	    try{ 
	    	URL url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
			System.out.println(jsonResults);
			Gson gson = new Gson();
			response = 
					gson.fromJson(jsonResults.toString(), GooglePlacesResponse.class);
			System.out.println(response);
	    }catch(Exception ex){
	    	//Catch Exception
	    }
	    return response;
	}
	
	
	/**
	 * NEW METHOD - USING GOOGLE PLACES
	 * @param hopSearchRequestDto
	 * @param modelAndView
	 */
	public Location getSrcLocation(HopSearchRequestDto hopSearchRequestDto){
		Location location = modelTransformer.prepareLocationFromInput(hopSearchRequestDto);
		location = populateLatLngInLocation(location);
		return location;
	}
	
	public Location getSrcLocation(PostRequestDto postRequestDto){
		Location location = modelTransformer.prepareLocationFromInput(postRequestDto);
		location = populateLatLngInLocation(location);
		return location;
	}
}
