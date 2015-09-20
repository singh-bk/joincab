package com.bits.canvas.common.dto;

/**
 * @author vatsritu
 *
 * Jan 11, 2015
 */
public class HopJourneyDto {

	private Integer hopId;
	
	private String postId;
	
	private String hopLocation;
	
	private String date;
	
	private String posterName;
	
	private Integer hopStatus;

	public Integer getHopId() {
		return hopId;
	}

	public void setHopId(Integer hopId) {
		this.hopId = hopId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getHopLocation() {
		return hopLocation;
	}

	public void setHopLocation(String hopLocation) {
		this.hopLocation = hopLocation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPosterName() {
		return posterName;
	}

	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}

	public Integer getHopStatus() {
		return hopStatus;
	}

	public void setHopStatus(Integer hopStatus) {
		this.hopStatus = hopStatus;
	}
}
