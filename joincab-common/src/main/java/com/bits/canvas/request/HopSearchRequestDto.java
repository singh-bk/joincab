package com.bits.canvas.request;

import java.math.BigDecimal;


public class HopSearchRequestDto {

	private String hopLocation;
	private String hopDate;
	private String hopTime;
	private Integer vehicleType;
	private String cscId;
	private String userId;
	private boolean shareReq;
	private String hopFormattedAddress;
	private BigDecimal hopLatitude;
	private BigDecimal hopLongitude;
	private String hopLatLng;
	private Integer vendorId;
	private Integer genderPref;
	
	public Integer getGenderPref() {
		return genderPref;
	}
	public void setGenderPref(Integer genderPref) {
		this.genderPref = genderPref;
	}
	public String getHopLocation() {
		return hopLocation;
	}
	public void setHopLocation(String hopLocation) {
		this.hopLocation = hopLocation;
	}
	public String getHopDate() {
		return hopDate;
	}
	public void setHopDate(String hopDate) {
		this.hopDate = hopDate;
	}
	public String getHopTime() {
		return hopTime;
	}
	public void setHopTime(String hopTime) {
		this.hopTime = hopTime;
	}
	public Integer getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getCscId() {
		return cscId;
	}
	public void setCscId(String cscId) {
		this.cscId = cscId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isShareReq() {
		return shareReq;
	}
	public void setShareReq(boolean shareReq) {
		this.shareReq = shareReq;
	}
	public String getHopFormattedAddress() {
		return hopFormattedAddress;
	}
	public void setHopFormattedAddress(String hopFormattedAddress) {
		this.hopFormattedAddress = hopFormattedAddress;
	}
	public BigDecimal getHopLatitude() {
		return hopLatitude;
	}
	public void setHopLatitude(BigDecimal hopLatitude) {
		this.hopLatitude = hopLatitude;
	}
	public BigDecimal getHopLongitude() {
		return hopLongitude;
	}
	public void setHopLongitude(BigDecimal hopLongitude) {
		this.hopLongitude = hopLongitude;
	}
	public String getHopLatLng() {
		return hopLatLng;
	}
	public void setHopLatLng(String hopLatLng) {
		this.hopLatLng = hopLatLng;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
}
