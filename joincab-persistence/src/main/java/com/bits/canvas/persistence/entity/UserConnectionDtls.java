package com.bits.canvas.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name="userconnection")
public class UserConnectionDtls implements Serializable,Persistable<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6293119964580407619L;

	@Id
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="PROVIDER_ID")
	private String providerId;
	
	@Column(name="PROVIDER_USER_ID")
	private String providerUserId;
	
	@Column(name="RANK")
	private Integer rank;
	
	@Column(name="DISPLAY_NAME")
	private String displayName;
	
	@Column(name="PROVIDER_URL")
	private String providerUrl;
	
	@Column(name="IMAGE_URL")
	private String imageUrl;
	
	@Column(name="ACCESS_TOKEN")
	private String accessToken;
	
	@Column(name="SECRET")
	private String secret;
	
	@Column(name="REFRESH_TOKEN")
	private String refreshToken;
	
	@Column(name="EXPIRE_TOKEN")
	private String expireToken;
	
	@Column(name="MOBILE_NUMBER")
	private Integer mobileNumber;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpireToken() {
		return expireToken;
	}

	public void setExpireToken(String expireToken) {
		this.expireToken = expireToken;
	}

	public Integer getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Integer mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return getUserId();
	}

	public boolean isNew() {
		// TODO Auto-generated method stub
		return null == getUserId();
	}
}
