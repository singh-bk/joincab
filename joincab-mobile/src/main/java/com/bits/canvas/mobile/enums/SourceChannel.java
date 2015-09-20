package com.bits.canvas.mobile.enums;

public enum SourceChannel {

	FACEBOOK(1),
	GOOGlE(2),
	CUSTOM(3);
	
    private final int srcChannel;

    SourceChannel(int srcChannel) {
        this.srcChannel = srcChannel;
    }
    
    public int getSrcChannel(){
    	return this.srcChannel;
    }
}
