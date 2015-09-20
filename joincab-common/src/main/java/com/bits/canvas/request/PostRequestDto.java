package com.bits.canvas.request;

import java.math.BigDecimal;


public class PostRequestDto {

	String cscId;
	String postLocation;
	String postDate;
	String postTime;
	String hopperCount;
	String genderPref;
	Integer postVehicleType;
	Integer vendorId;
	BigDecimal postLatitude;
	BigDecimal postLongitude;
	String userId;
	String joinMsgCode;
	Integer postStatus;
	String postFormattedAddress;
	String placeId;
	String transactionId;
	Integer share;
	Long distanceFromDest;
	
	
	public String getCscId() {
		return cscId;
	}
	public void setCscId(String cscId) {
		this.cscId = cscId;
	}

	public String getHopperCount() {
		return hopperCount;
	}
	public String getGenderPref() {
		return genderPref;
	}
	
	public Integer getVendorId() {
		return vendorId;
	}
	public void setHopperCount(String hopperCount) {
		this.hopperCount = hopperCount;
	}
	public void setGenderPref(String genderPref) {
		this.genderPref = genderPref;
	}
	
	public Integer getPostVehicleType() {
		return postVehicleType;
	}
	public void setPostVehicleType(Integer postVehicleType) {
		this.postVehicleType = postVehicleType;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getJoinMsgCode() {
		return joinMsgCode;
	}
	public void setJoinMsgCode(String joinMsgCode) {
		this.joinMsgCode = joinMsgCode;
	}
	public Integer getPostStatus() {
		return postStatus;
	}
	public String getPostFormattedAddress() {
		return postFormattedAddress;
	}
	public void setPostFormattedAddress(String postFormattedAddress) {
		this.postFormattedAddress = postFormattedAddress;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}
	public String getPostLocation() {
		return postLocation;
	}
	public void setPostLocation(String postLocation) {
		this.postLocation = postLocation;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	public BigDecimal getPostLatitude() {
		return postLatitude;
	}
	public void setPostLatitude(BigDecimal postLatitude) {
		this.postLatitude = postLatitude;
	}
	public BigDecimal getPostLongitude() {
		return postLongitude;
	}
	public void setPostLongitude(BigDecimal postLongitude) {
		this.postLongitude = postLongitude;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getShare() {
		return share;
	}
	public void setShare(Integer share) {
		this.share = share;
	}
	public Long getDistanceFromDest() {
		return distanceFromDest;
	}
	public void setDistanceFromDest(Long distanceFromDest) {
		this.distanceFromDest = distanceFromDest;
	}
	
}
