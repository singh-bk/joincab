package com.bits.canvas.mobile.common;

import java.io.Serializable;

/**
 * 
 * @author Birendra K Singh
 *This class is used to populate data in the cache.
 *When a join request is made by the hop user to a post user
 *	 cache is updated with (<hopId>Trip, <existingList>+postId)
 *					  and (<postId>Trip, <existingList>+hopId)
 *Similar case happens in case of pick request
 */
public class TripDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8679437931523860353L;

	private String itemId;
	
	private Integer status;//status is populated from TripStatus

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
