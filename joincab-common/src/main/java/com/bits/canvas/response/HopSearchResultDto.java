package com.bits.canvas.response;

import java.math.BigDecimal;

public class HopSearchResultDto {

	String firstName;
	String lastName;
	String name;
	String formattedAddress;
	String pickUpTime;
	String travelTime;
	Double trustIndex;
	String postId;
	String mapUrl;
	String cscId;
	private BigDecimal latitude;
	private BigDecimal longitude;
	
	public HopSearchResultDto(String firstName,String lastName,String pickUpTime,String cscId,String formattedAddress,String postId,BigDecimal longitude,BigDecimal latitude){
		
		this.firstName=firstName;
		this.lastName=lastName;
		this.pickUpTime=pickUpTime;
		this.cscId=cscId;
		this.formattedAddress=formattedAddress;
		this.postId=postId;
		this.longitude=longitude;
		this.latitude=latitude;
		this.name=this.firstName+" "+this.lastName;
		this.travelTime=pickUpTime;
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getName() {
		return this.firstName+" "+this.lastName;
	}
	
	public String getTravelTime() {
		String time =this.pickUpTime.toString();
		return time;
	}
	public Double getTrustIndex() {
		return trustIndex;
	}
	public void setTrustIndex(Double trustIndex) {
		this.trustIndex = trustIndex;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	public String getPickUpTime() {
		return pickUpTime;
	}
	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}
	public String getCscId() {
		return cscId;
	}

	public void setCscId(String cscId) {
		this.cscId = cscId;
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

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	
}
