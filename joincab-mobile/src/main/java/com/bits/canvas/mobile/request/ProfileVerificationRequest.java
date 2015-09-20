package com.bits.canvas.mobile.request;

import com.bits.canvas.mobile.common.AbstractDto;

public class ProfileVerificationRequest extends AbstractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1207536993127665524L;

	private String userId;
	
	private String imageByte;
	
	private long imageSize;
	
	private Integer vehicleType; // Change it to vehicleName
	
	private String vehicleNumber;//TODO - changed
	
	private Integer ownedVehicle;

	private Integer gender;
	
	private String gcmRegId;
	
	public String getUserId() {
		return userId;
	}

	public String getGcmRegId() {
		return gcmRegId;
	}

	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImageByte() {
		return imageByte;
	}

	public void setImageByte(String imageByte) {
		this.imageByte = imageByte;
	}

	public long getImageSize() {
		return imageSize;
	}

	public void setImageSize(long imageSize) {
		this.imageSize = imageSize;
	}

	public Integer getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Integer getOwnedVehicle() {
		return ownedVehicle;
	}

	public void setOwnedVehicle(Integer ownedVehicle) {
		this.ownedVehicle = ownedVehicle;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
}
