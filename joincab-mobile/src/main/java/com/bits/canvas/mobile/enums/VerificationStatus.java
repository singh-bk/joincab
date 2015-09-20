package com.bits.canvas.mobile.enums;

public enum VerificationStatus {
	
	VERIFIED(1), 
	NOT_VERIFIED(2);

	private final int verified;

	VerificationStatus(int verified) {
		this.verified = verified;
	}

	public int getVerified() {
		return this.verified;
	}

}
