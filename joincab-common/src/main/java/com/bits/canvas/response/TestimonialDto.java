package com.bits.canvas.response;

public class TestimonialDto {

	 String message;
	
	 String imageUrl;
	
	 String firstName;
	 
	 String lastName;

	public TestimonialDto(String fname,String lname,String imageUrl,String message){
		this.imageUrl = imageUrl;
		this.message = message;
		this.firstName = fname;
		this.lastName = lname;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	
}
