package com.bits.canvas.mobile.enums;

public enum PostStatus {

	/**
	 * Flow of status
	 * 1. Initiated -> Joined(only when all seats are confirmed as joined) -> Started ->Completed 
	 * 						  											   -> Canceled
	 * 				-> PartialJoined -> Started -> Completed
	 * 								 -> Canceled
	 * 				-> Canceled
	 * 				-> Lapsed
	 */

	INITIATED(1),
	PARTIAL_JOIN(2),
	JOINED(3),
	STARTED(4),
	COMPLETED(5),
	CANCELLED(6),
	LAPSED(7);
	
	private int code;

	private PostStatus(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}
	
}
