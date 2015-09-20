package com.bits.canvas.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name="user_testimonial")
public class UserTestimonail implements Serializable,Persistable<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2851129764139651472L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "testimonial")
	private String testimonial;
	
	@Column(name = "is_viewable")
	private Integer isViewable;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTestimonial() {
		return testimonial;
	}
	public void setTestimonial(String testimonial) {
		this.testimonial = testimonial;
	}
	public Integer getIsViewable() {
		return isViewable;
	}
	public void setIsViewable(Integer isViewable) {
		this.isViewable = isViewable;
	}
	
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}
}