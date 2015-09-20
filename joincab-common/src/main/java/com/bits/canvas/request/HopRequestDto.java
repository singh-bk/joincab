package com.bits.canvas.request;


public class HopRequestDto {

	private String userId;
	
	private Integer hopperCount;
	
	private String hopFormattedAddress;
	
	private String postId;
	
	private Integer status;
	
	private String cscId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCscId() {
		return cscId;
	}

	public void setCscId(String cscId) {
		this.cscId = cscId;
	}

	public Integer getHopperCount() {
		return hopperCount;
	}

	public void setHopperCount(Integer hopperCount) {
		this.hopperCount = hopperCount;
	}

	public String getHopFormattedAddress() {
		return hopFormattedAddress;
	}

	public void setHopFormattedAddress(String hopFormattedAddress) {
		this.hopFormattedAddress = hopFormattedAddress;
	}

}
