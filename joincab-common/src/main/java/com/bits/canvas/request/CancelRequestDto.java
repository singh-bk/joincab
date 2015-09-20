package com.bits.canvas.request;

public class CancelRequestDto {

	Integer bookId;
	
	Integer hopid;
	
	String postId;
	
	String hopUserId;
	
	String postUserId;
	
	boolean isShared;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getHopid() {
		return hopid;
	}

	public void setHopid(Integer hopid) {
		this.hopid = hopid;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getHopUserId() {
		return hopUserId;
	}

	public void setHopUserId(String hopUserId) {
		this.hopUserId = hopUserId;
	}

	public String getPostUserId() {
		return postUserId;
	}

	public void setPostUserId(String postUserId) {
		this.postUserId = postUserId;
	}

	public boolean isShared() {
		return isShared;
	}

	public void setShared(boolean isShared) {
		this.isShared = isShared;
	}
}
