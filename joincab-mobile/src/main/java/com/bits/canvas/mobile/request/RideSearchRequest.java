package com.bits.canvas.mobile.request;

import com.bits.canvas.mobile.common.AbstractDto;

public class RideSearchRequest extends AbstractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1467366821928771911L;

	private String cscId;
	
	private Float srcLat;
	
	private Float srcLng;
	
	private String srcFrmtAddr;
	
	private String srcPlaceId;
	
	private Float destLat;
	
	private Float destLng;
	
	private String destFrmtAddr;
	
	private String destPlaceId;
	
	private Long travelTime;
	
	private Integer pps;
	
	//If the user has already made a hop/post request, pass the hopId and postId in the request as well.
	private String hopId;
	
	private String postId;

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

	public String getSrcFrmtAddr() {
		return srcFrmtAddr;
	}

	public void setSrcFrmtAddr(String srcFrmtAddr) {
		this.srcFrmtAddr = srcFrmtAddr;
	}

	public String getSrcPlaceId() {
		return srcPlaceId;
	}

	public void setSrcPlaceId(String srcPlaceId) {
		this.srcPlaceId = srcPlaceId;
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

	public String getDestFrmtAddr() {
		return destFrmtAddr;
	}

	public void setDestFrmtAddr(String destFrmtAddr) {
		this.destFrmtAddr = destFrmtAddr;
	}

	public String getDestPlaceId() {
		return destPlaceId;
	}

	public void setDestPlaceId(String destPlaceId) {
		this.destPlaceId = destPlaceId;
	}

	public Long getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Long travelTime) {
		this.travelTime = travelTime;
	}

	public Integer getPps() {
		return pps;
	}

	public void setPps(Integer pps) {
		this.pps = pps;
	}

	public String getHopId() {
		return hopId;
	}

	public void setHopId(String hopId) {
		this.hopId = hopId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

}
