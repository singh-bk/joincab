package com.bits.canvas.common.constant;

public enum BookStatus {

	REQUESTED(1),
	CONFIRMED(2),
	CACELLED(3);
	
	private int code;

	private BookStatus(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}
}
