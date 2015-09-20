/**
 * 
 */
package com.bits.canvas.request;

/**
 * @author vatsritu
 * Jun 30, 2014
 */
public class CabBookRequestDto {

	private String hopLocation;
	private String hopDate;
	private String hopTime;
	private String hoppersCount;
	private Integer vehicleType;
	private Long locationId;
	private String neighbors;
	private String cscId;
	private String hopLandmark;
	private String userId;
	
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
	public String getHoppersCount() {
		return hoppersCount;
	}
	public void setHoppersCount(String hoppersCount) {
		this.hoppersCount = hoppersCount;
	}
	public Integer getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(String neighbors) {
		this.neighbors = neighbors;
	}
	public String getCscId() {
		return cscId;
	}
	public void setCscId(String cscId) {
		this.cscId = cscId;
	}
	public String getHopLandmark() {
		return hopLandmark;
	}
	public void setHopLandmark(String hopLandmark) {
		this.hopLandmark = hopLandmark;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
