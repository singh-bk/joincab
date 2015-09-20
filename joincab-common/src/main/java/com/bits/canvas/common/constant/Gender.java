package com.bits.canvas.common.constant;

public enum Gender {
	MALE(1),
	FEMALE(2),
	OTHERS(3);

	private int code;

	private Gender(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}
}
