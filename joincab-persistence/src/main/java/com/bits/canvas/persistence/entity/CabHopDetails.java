package com.bits.canvas.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;
/**
 * @author vatsritu
 * {@link Date}
 *
 */

@Entity
@Table(name="CAB_HOP_DETAILS")
public class CabHopDetails implements Serializable,Persistable<Integer>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5824602660413193294L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="CSC_ID")
	private String cscId;
	
	
	@Column(name="HOPPER_COUNT")
	private Integer hopperCount;
	
	@Column(name="FORMATTED_ADDRESS")
	private String hopFormattedAddress;
	
	@Column(name="POST_ID")
	private String postId;
	
	@Column(name="STATUS")
	private Integer status;
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
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
	public String getCscId() {
		return cscId;
	}
	public void setCscId(String cscId) {
		this.cscId = cscId;
	}
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}
}
