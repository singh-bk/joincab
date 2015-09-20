package com.bits.canvas.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name="ADUSER")
public class ADUser implements Serializable,Persistable<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1498166702668699766L;

	@Id
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="PWD")
	private String password;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return this.userId;
	}

	public boolean isNew() {
		return (null==this.userId);
	}
}
