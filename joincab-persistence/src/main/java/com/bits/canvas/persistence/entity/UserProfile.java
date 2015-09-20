package com.bits.canvas.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name="USER_PROFILE")
public class UserProfile implements Serializable,Persistable<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1498166702668699766L;

	@Id
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="FIRSTNAME")
	private String firstName;
	
	@Column(name="LASTNAME")
	private String lastName;

	@Column(name="DISPLAYNAME")
	private String displayName;

	@Column(name="DOB")
	private String dob;
	
	@Column(name="GENDER")
	private Integer gender;
	
	@Column(name="MOB_NUM")
	private String mobNum;

	@Column(name="VEH_NUM")
	private String vehNum;

	@Column(name="VEH_TYPE")
	private Integer vehType;
	
	@Column(name="IS_OWNED")
	private Integer isOwned;
	
	@Column(name="IS_VERIFIED")
	private Integer isVerified;
	
	@Column(name="GCM_REGID")
	private String gcmRegId;
	
	@Column(name="IMG_URL")
	private String imgUrl;
	
	//@OneToMany(mappedBy="hopUser")
	//private List<HopDetails> hopList;
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getMobNum() {
		return mobNum;
	}

	public void setMobNum(String mobNum) {
		this.mobNum = mobNum;
	}

	public String getVehNum() {
		return vehNum;
	}

	public void setVehNum(String vehNum) {
		this.vehNum = vehNum;
	}

	public Integer getVehType() {
		return vehType;
	}

	public void setVehType(Integer vehType) {
		this.vehType = vehType;
	}

	public Integer getIsOwned() {
		return isOwned;
	}

	public void setIsOwned(Integer isOwned) {
		this.isOwned = isOwned;
	}

	public Integer getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}

	public String getGcmRegId() {
		return gcmRegId;
	}

	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/*
    public List<HopDetails> getHopList() {
		return hopList;
	}

	public void setHopList(List<HopDetails> hopList) {
		this.hopList = hopList;
	}

	public void addHopDetails(HopDetails hopDetails) {
        this.hopList.add(hopDetails);
        if (hopDetails.getHopUser() != this) {
        	hopDetails.setHopUser(this);
        }
    }
	*/
	public String getId() {
		return this.userId;
	}

	public boolean isNew() {
		return (null==this.userId);
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder("");
		str.append(this.getUserId()+"|");
		str.append(this.getDisplayName()+"|");
		str.append(this.getGender()+"|");
		str.append(this.getMobNum()+"|");
		str.append(this.getVehNum()+"|");
		str.append(this.getVehType()+"|");
		str.append(this.getGcmRegId()+"|");
		str.append(this.getImgUrl());
		return str.toString();
	}
}
