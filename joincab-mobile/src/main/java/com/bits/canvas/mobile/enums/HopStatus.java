package com.bits.canvas.mobile.enums;

public enum HopStatus {

	/**
	 * Flow of status
	 * 1. Initiated -> Joined -> Started ->Completed 
	 * 						  -> Canceled
	 * 				-> Canceled
	 * 				-> Lapsed
	 */

	INITIATED(1),
	JOINED(2),
	STARTED(3),
	COMPLETED(4),
	CANCELLED(5),
	LAPSED(6);

	
	private int code;

	private HopStatus(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}
}
