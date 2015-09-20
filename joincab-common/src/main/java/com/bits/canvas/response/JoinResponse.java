package com.bits.canvas.response;


public class JoinResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2587892530910742808L;
	
	private boolean isSuccess;
	
	private String message;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
