package com.bits.canvas.common.dto;

import java.math.BigDecimal;

/**
 * 
 * @author bk
 * 23-Aug-2014
 */
public class Location {
	private String cscId;
	private String locationName;
	private String formattedAddress;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String placeId;
	private Boolean isAccurate;
	public String getCscId() {
		return cscId;
	}
	public void setCscId(String cscId) {
		this.cscId = cscId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public Boolean getIsAccurate() {
		return isAccurate;
	}
	public void setIsAccurate(Boolean isAccurate) {
		this.isAccurate = isAccurate;
	}
	
}
