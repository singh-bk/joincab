package com.bits.canvas.mobile.request;

import com.bits.canvas.mobile.common.AbstractDto;

public class JourneyDetailsRequest extends AbstractDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3640335671452512744L;

	private String userId;
	private String itemId; // can be either hop or post
	private Integer opType; // 0 - indicates that user has requested hop 1 - indicates user has requested post
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getOpType() {
		return opType;
	}
	public void setOpType(Integer opType) {
		this.opType = opType;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
}
