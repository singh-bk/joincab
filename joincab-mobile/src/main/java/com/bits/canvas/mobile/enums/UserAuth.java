package com.bits.canvas.mobile.enums;

public enum UserAuth {

	EXISTS(1),
	NOT_EXISTS(2),
	INCORRECT_PWD(3),
	ADDED(4);
	
    private final int userAuth;

    UserAuth(int userAuth) {
        this.userAuth = userAuth;
    }
    
    public int getUserAuth(){
    	return this.userAuth;
    }
}
