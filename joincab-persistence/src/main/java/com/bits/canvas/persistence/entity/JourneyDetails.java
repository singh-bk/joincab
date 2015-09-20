package com.bits.canvas.persistence.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

/**
 * @author vatsritu
 * {@link Date}
 *
 */
@Entity
@Table(name="JOURNEY_DETAILS")
public class JourneyDetails implements Serializable,Persistable<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -739412693377798604L;

	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="PostId")
	private String	postId;
	
	@Column(name="HopId")
	private String hopId;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the postId
	 */
	public String getPostId() {
		return postId;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(String postId) {
		this.postId = postId;
	}

	/**
	 * @return the hopId
	 */
	public String getHopId() {
		return hopId;
	}

	/**
	 * @param hopId the hopId to set
	 */
	public void setHopId(String hopId) {
		this.hopId = hopId;
	}

	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}
}
