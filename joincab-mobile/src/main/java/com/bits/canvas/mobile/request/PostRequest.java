package com.bits.canvas.mobile.request;

import java.io.Serializable;

public class PostRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -412990963943434094L;

	private String userId;
	
	private String cscId;
	
	private Float srcLat;
	
	private Float srcLng;
	
	private String srcAddress;
	
	private String srcPlaceId;
	
	private Float destLat;
	
	private Float destLng;
	
	private String destAddress;
	
	private String destPlaceId;
	
	private Integer pps;//TM
		
	private Long travelTime;
	
	private Integer seatsOffered;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCscId() {
		return cscId;
	}

	public void setCscId(String cscId) {
		this.cscId = cscId;
	}

	public Float getSrcLat() {
		return srcLat;
	}

	public void setSrcLat(Float srcLat) {
		this.srcLat = srcLat;
	}

	public Float getSrcLng() {
		return srcLng;
	}

	public void setSrcLng(Float srcLng) {
		this.srcLng = srcLng;
	}

	public String getSrcAddress() {
		return srcAddress;
	}

	public void setSrcAddress(String srcAddress) {
		this.srcAddress = srcAddress;
	}

	public Float getDestLat() {
		return destLat;
	}

	public void setDestLat(Float destLat) {
		this.destLat = destLat;
	}

	public Float getDestLng() {
		return destLng;
	}

	public void setDestLng(Float destLng) {
		this.destLng = destLng;
	}

	public String getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	public Integer getPps() {
		return pps;
	}

	public void setPps(Integer pps) {
		this.pps = pps;
	}

	public Long getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Long travelTime) {
		this.travelTime = travelTime;
	}

	public Integer getSeatsOffered() {
		return seatsOffered;
	}

	public void setSeatsOffered(Integer seatsOffered) {
		this.seatsOffered = seatsOffered;
	}

	public String getSrcPlaceId() {
		return srcPlaceId;
	}

	public void setSrcPlaceId(String srcPlaceId) {
		this.srcPlaceId = srcPlaceId;
	}

	public String getDestPlaceId() {
		return destPlaceId;
	}

	public void setDestPlaceId(String destPlaceId) {
		this.destPlaceId = destPlaceId;
	}

	
}
