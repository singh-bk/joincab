package com.bits.canvas.redis.connector;

import java.io.Serializable;

public class Temp implements Serializable{

	private String name;
	private Integer age;
	
	public Temp(int age, String name){
		this.age = age;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
