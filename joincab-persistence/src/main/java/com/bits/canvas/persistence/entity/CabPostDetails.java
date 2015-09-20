/**
 * 
 */
package com.bits.canvas.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;


/**
 * @author vatsritu
 * 
 *
 */

@Entity
@Table(name="CAB_POST_DETAILS")
public class CabPostDetails implements Serializable,Persistable<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9035403236572835638L;

	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="CSC_ID")
	private String cscId;
	
	@Column(name="PICKUP_TIME")
	private String pickUpTime;
	
	@Column(name="HOPPER_COUNT")
	private Integer hopperCount;
	
	@Column(name="SEAT_AVAILABLE")
	private Integer seatAvail;
	
	@Column(name="GENDER_PREF")
	private Integer genderPref;
	
	@Column(name="VEHICLE_TYPE")
	private Integer vehicleType;
	
	@Column(name="VENDOR_ID")
	private Integer vendorId;
	
	
	@Column(name="FORMATTED_ADDRESS")
	private String postFormattedAddress;
	
	@Column(name="LATITUDE")
	private BigDecimal latitude;
	
	@Column(name="LONGITUDE")
	private BigDecimal longitude;
	
	@Column(name = "JOIN_MSG_CODE")
	private String joinMsgCode;
	
	@Column(name = "JOIN_STATUS")
	private Integer postStatus;
	
	@Column(name = "SHARE")
	private Integer share;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	public BigDecimal getLatitude() {
		return latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}


	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the pickUpTime
	 */
	public String getPickUpTime() {
		return pickUpTime;
	}

	/**
	 * @param pickUpTime the pickUpTime to set
	 */
	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	/**
	 * @return the hopperCount
	 */
	public Integer getHopperCount() {
		return hopperCount;
	}

	/**
	 * @param hopperCount the hopperCount to set
	 */
	public void setHopperCount(Integer hopperCount) {
		this.hopperCount = hopperCount;
	}

	/**
	 * @return the seatAvail
	 */
	public Integer getSeatAvail() {
		return seatAvail;
	}

	/**
	 * @param seatAvail the seatAvail to set
	 */
	public void setSeatAvail(Integer seatAvail) {
		this.seatAvail = seatAvail;
	}

	/**
	 * @return the genderPref
	 */
	public Integer getGenderPref() {
		return genderPref;
	}

	/**
	 * @param genderPref the genderPref to set
	 */
	public void setGenderPref(Integer genderPref) {
		this.genderPref = genderPref;
	}

	/**
	 * @return the vehicleType
	 */
	public Integer getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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


	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}


	public String getCscId() {
		return cscId;
	}


	public void setCscId(String cscId) {
		this.cscId = cscId;
	}


	public String getPostFormattedAddress() {
		return postFormattedAddress;
	}


	public void setPostFormattedAddress(String postFormattedAddress) {
		this.postFormattedAddress = postFormattedAddress;
	}


	public Integer getShare() {
		return share;
	}


	public void setShare(Integer share) {
		this.share = share;
	}


	/**
	 * @return the isOwner
	 */
//	public Integer getIsOwner() {
//		return isOwner;
//	}
//
//	/**
//	 * @param isOwner the isOwner to set
//	 */
//	public void setIsOwner(Integer isOwner) {
//		this.isOwner = isOwner;
//	}

	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
