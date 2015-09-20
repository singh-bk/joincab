/**
 * 
 */
package com.bits.canvas.common.dto;

/**
 * @author vatsritu
 *
 * Jan 11, 2015
 */
public class PostJourneyDto {

	private Integer hopId;
	
	private String postId;
	
	private String postLocation;
	
	private String date;
	
	private String hopperName;
	
	private Integer postStatus;

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

	public String getPostLocation() {
		return postLocation;
	}

	public void setPostLocation(String postLocation) {
		this.postLocation = postLocation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHopperName() {
		return hopperName;
	}

	public void setHopperName(String hopperName) {
		this.hopperName = hopperName;
	}

	public Integer getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}
}
