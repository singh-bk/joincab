package com.bits.canvas.mobile.enums;

public enum Gender {

	MALE(1),
	FEMALE(2),
	OTHERS(3);
	
    private final int gender;

    Gender(int gender) {
        this.gender = gender;
    }
    
    public int getGender(){
    	return this.gender;
    }
}
