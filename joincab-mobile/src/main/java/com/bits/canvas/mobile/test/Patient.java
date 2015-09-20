package com.bits.canvas.mobile.test;

import java.util.HashMap;
import java.util.Map;

public class Patient{

	private String patientId;
	private String businessKey;

	private Map<String, Object> map = new HashMap<String, Object>();
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
}
