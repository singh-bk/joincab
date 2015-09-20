package com.bits.canvas.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name="HOP_DETAILS")
public class HopDetails implements Serializable,Persistable<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1498166702668699766L;

	@Id
	@Column(name = "HOP_ID")
	private String hopId;//use guid
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="CSC_ID")
	private String cscId;
	
	@Column(name="SRC_LAT")
	private Float srcLat;
	
	@Column(name="SRC_LNG")
	private Float srcLng;
	
	@Column(name="SRC_ADDR")
	private String srcAddress;//max of 500 chars
	
	@Column(name="SRC_PLACE_ID")
	private String srcPlaceId;
	
	@Column(name="DEST_LAT")
	private Float destLat;
	
	@Column(name="DEST_LNG")
	private Float destLng;
	
	@Column(name="DEST_ADDR")
	private String destAddress;//max of 500 chars
	
	@Column(name="DEST_PLACE_ID")
	private String destPlaceId;
	
	@Column(name="PPS")
	private Integer pps;//TM - price per seat
		
	@Column(name="TRAVEL_TIME")
	private Long travelTime;// keep epoch time
	
	@Column(name="HOP_STATUS")
	private Integer status;

	/*
	 * no of join requests sent by the user. 
	 * Should be less than some max value
	 */
	@Column(name="REQ_CNT")
	private Integer reqCnt;
	
	/*
	 * keep track no of pending pick up requests for the this post. 
	 * Should not be more than a max value. 
	 * Higher the number, lower should be its visibility in hop search results.
	 */
	@Column(name="PICK_CNT")
	private Integer pickCnt; 
	
	/*
	 * number of denied pick requests for this post.
	 * Greater the number, lower the visibility in post search results. 
	 */
	@Column(name="DENY_CNT")
	private Integer denyCnt;
	
	//@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="HOP_USER")
	//private UserProfile hopUser;
	
	public String getHopId() {
		return hopId;
	}

	public void setHopId(String hopId) {
		this.hopId = hopId;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReqCnt() {
		return reqCnt;
	}

	public void setReqCnt(Integer reqCnt) {
		this.reqCnt = reqCnt;
	}

	public Integer getPickCnt() {
		return pickCnt;
	}

	public void setPickCnt(Integer pickCnt) {
		this.pickCnt = pickCnt;
	}

	public Integer getDenyCnt() {
		return denyCnt;
	}

	public void setDenyCnt(Integer denyCnt) {
		this.denyCnt = denyCnt;
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

   /*
	public void setUserProfile(UserProfile user) {
        this.hopUser = user;
        if (!user.getHopList().contains(this)) {
        	user.getHopList().add(this);
        }
    }
	
	public UserProfile getHopUser() {
		return hopUser;
	}

	public void setHopUser(UserProfile hopUser) {
		this.hopUser = hopUser;
	}
	*/

	public String getId() {
		return this.hopId;
	}

	public boolean isNew() {
		return (null==this.userId);
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder("");
		str.append(this.getUserId()+"|");
		str.append(this.getSrcLat()+"|");
		str.append(this.getSrcLng()+"|");
		str.append(this.getSrcAddress()+"|");
		str.append(this.getSrcPlaceId()+"|");
		str.append(this.getDestLat()+"|");
		str.append(this.getDestLng()+"|");
		str.append(this.getDestAddress()+"|");
		str.append(this.getDestPlaceId()+"|");
		str.append(this.getPps()+"|");
		str.append(this.getStatus()+"|");
		str.append(this.getReqCnt()+"|");
		str.append(this.getDenyCnt()+"|");
		str.append(this.getPickCnt()+"|");
		str.append(this.getTravelTime());
		return str.toString();
	}
}
