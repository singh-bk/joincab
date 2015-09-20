package com.bits.canvas.common.constant;

public enum HopStatus {

	REQUESTED(1),
	JOINED(2),
	WATCH(3),
	CANCELLED(4);
	
	private int code;

	private HopStatus(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}
}
