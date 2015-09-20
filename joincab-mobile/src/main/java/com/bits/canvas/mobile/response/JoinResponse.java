package com.bits.canvas.mobile.response;

import com.bits.canvas.mobile.common.AbstractDto;

public class JoinResponse extends AbstractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6344541546670720998L;

	private String tripId;
	
	private boolean success;
	

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
