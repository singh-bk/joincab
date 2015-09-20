package com.bits.canvas.mobile.common;

public class SearchDto extends AbstractDto{

	private static final long serialVersionUID = 8537337222566132928L;

	private String displayName;
	
	private String userImageUrl;
		
	private Float srcLat;
	
	private Float srcLng;
	
	private String srcAddress;

	private String destAddress;

	private Integer pps;//TM
	
	private Integer rating;
	
	private Long travelTime;
	
	private Integer srcDist;
	
	private String userId;//This will be the email id
	
	private Integer gender;
	
	//True - if the user has alreay requested a join/pick request.
	private Boolean isRequested; //TODO - maynot be needed. tripStatus can be enough
	
	//Indicates the status of the post - HopStatus/PostStatus
	private Integer status;
	
	//Indicates whether the hopId has joined or request is still pending with the given user
	/*
	 * Indicates whether the postId has joined or request is still pending with the given user.
	 *  to be used only for Journey details page to display proper actionable button
	 *  Either a post and a hop is joined or the request can be still pending.
	 */
	private Integer tripStatus;

	public Boolean getIsRequested() {
		return isRequested;
	}

	public void setIsRequested(Boolean isRequested) {
		this.isRequested = isRequested;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public String getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
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

	public Integer getPps() {
		return pps;
	}

	public void setPps(Integer pps) {
		this.pps = pps;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Long getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Long travelTime) {
		this.travelTime = travelTime;
	}

	public Integer getSrcDist() {
		return srcDist;
	}

	public void setSrcDist(Integer srcDist) {
		this.srcDist = srcDist;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(Integer tripStatus) {
		this.tripStatus = tripStatus;
	}
	
}
