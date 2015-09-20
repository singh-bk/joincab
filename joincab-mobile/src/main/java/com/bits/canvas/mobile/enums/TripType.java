
package com.bits.canvas.mobile.enums;

public enum TripType {


	HOP(0),
	POST(1);

	
	private int code;

	private TripType(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}
}
