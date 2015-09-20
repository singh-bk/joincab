package com.bits.canvas.mobile.enums;

public enum TripStatus {

	/**
	 * Flow of status
	 * 1. Requested -> Confirmed -> Started ->Completed 
	 * 						     -> Canceled
	 * 				-> Denied
	 * 				-> Canceled
	 * 				-> Lapsed
	 */

	JOIN_REQUEST(1), // Join will always requested by hop user to post user
	PICK_REQUEST(2), //pick will always be requested by post user to hop user
	CONFIRMED(3),
	DENIED(4),
	STARTED(5),
	COMPLETED(6),
	CANCELLED(7),
	LAPSED(8);
	
	private int code;

	private TripStatus(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}
	
}
