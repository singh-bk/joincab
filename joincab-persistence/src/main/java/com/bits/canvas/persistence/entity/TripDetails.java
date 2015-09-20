package com.bits.canvas.persistence.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;


@Entity
@Table(name="TRIP_DETAILS")
public class TripDetails implements Serializable,Persistable<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -739412693377798604L;

	@Id
	@Column(name="TRIP_ID")
	private String tripId;
	
	@Column(name="HOP_ID")
	private String	hopId;
	
	@Column(name="POST_ID")
	private String postId;
	
	//Indicates if it is join/pickup request
	@Column(name="OP_TYPE")
	private Integer opType;
	
	/*
	 * In case of join - price of poster
	 * in case of pick up - price of hopper
	 */
	@Column(name="PRICE")
	private Integer price;
	
	/*
	 * status of the trip
	 * 	Initiated-> Confirm/Deny->Started/Cancel
	 */
	@Column(name="STATUS")
	private Integer status;
	
	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
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

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getId() {
		return this.tripId;
	}
}
