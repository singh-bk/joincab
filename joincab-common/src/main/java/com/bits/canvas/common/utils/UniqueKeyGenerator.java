package com.bits.canvas.common.utils;

import java.util.UUID;

public class UniqueKeyGenerator {

	public static String getUniqueKey(){
		
		StringBuilder builder = new StringBuilder();

        builder.append(UUID.randomUUID().toString().toUpperCase());

        return builder.toString();
	}
	
}
