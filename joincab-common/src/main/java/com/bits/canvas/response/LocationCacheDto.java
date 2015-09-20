package com.bits.canvas.response;

import java.math.BigInteger;

public class LocationCacheDto {

	//public String cscId;
	
	public BigInteger locationId;
	
	public String locationName;

	public String neighbors;
	
	
	public LocationCacheDto(BigInteger locationId, String locationName, String neighbors) {
		this.locationId = locationId;
		this.locationName = locationName;
		this.neighbors = neighbors;
	}

	public BigInteger getLocationId() {
		return locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationId(BigInteger locationId) {
		this.locationId = locationId;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public String getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(String neighbors) {
		this.neighbors = neighbors;
	}

	@Override
	public String toString(){
		return this.locationId+","+this.locationName+","+this.neighbors;
	}
}
