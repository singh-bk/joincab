package com.bits.canvas.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum VehicleType {

	AC_SEDAN(1),
	NON_AC_SEDAN(2),
	AC_MINI(3),
	NON_AC_MINI(4),
	ANY(5);

	private int code;

	private static final Map<Integer, VehicleType> enumMap = new HashMap<Integer, VehicleType>();

	static {
		for (VehicleType s : EnumSet.allOf(VehicleType.class))
			enumMap.put(s.getCode(), s);
	}
	
	private VehicleType(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public static VehicleType findByValue(int val) {
		return enumMap.get(val);
	}

}
