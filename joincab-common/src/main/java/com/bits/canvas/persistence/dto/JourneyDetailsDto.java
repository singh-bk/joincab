package com.bits.canvas.persistence.dto;

public class JourneyDetailsDto {

	private String id;

	private String pickupTime;

	private String address;

	private String firstName;

	private String lastName;

	private Integer status;

	public JourneyDetailsDto(String id, String pickupTime, String address,
			String firstName,String lastName, Integer status) {

		super();

		this.id = id;

		this.pickupTime = pickupTime;

		this.address = address;

		this.firstName = firstName;

		this.lastName = lastName;
		
		this.status = status;

	}
	
	public JourneyDetailsDto(String id, String pickupTime, String address, Integer status) {

		super();

		this.id = id;

		this.pickupTime = pickupTime;

		this.address = address;

		this.status = status;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
}