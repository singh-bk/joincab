package com.bits.canvas.common.constant;

public enum PostStatus {

	POSTED(1),
	REQUEST_RECEIVED(2),
	REQUEST_CONFIRMED(3),
	POST_CANCELLED(4);
	
	private int code;

	private PostStatus(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}
	
}
