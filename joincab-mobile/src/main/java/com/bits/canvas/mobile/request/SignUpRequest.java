package com.bits.canvas.mobile.request;

public class SignUpRequest extends ProfileVerificationRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7703951922469486457L;

	private String displayName;
	
	private String email;
		
	private String password;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
