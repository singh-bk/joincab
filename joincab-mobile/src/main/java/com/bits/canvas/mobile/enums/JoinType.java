
package com.bits.canvas.mobile.enums;

public enum JoinType {


	JOIN(0),
	PICK(1);

	
	private int code;

	private JoinType(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}
}
