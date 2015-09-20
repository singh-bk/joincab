package com.bits.canvas.transformer;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class LocationTransformer {

	Map<String, String> map;
	@PostConstruct
	public void loadLocations(){
		map = new HashMap<String, String>();
		map.put("BLR-KA-IN", "Bangalore, Karnataka, India");
		map.put("HYD-TL-IN", "Hyderabad, Telangana, India");
	}
	
	public String getLocationFromCscId(String cscId){
		String location = map.get(cscId);
		return location;
	}
}
