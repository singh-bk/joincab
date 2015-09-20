package com.bits.canvas.mobile.response;

import java.util.List;

import com.bits.canvas.mobile.common.AbstractDto;
import com.bits.canvas.mobile.common.HopDto;
import com.bits.canvas.mobile.common.PostDto;

public class RideSearchResponse extends AbstractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8330159252088338782L;

	private List<HopDto> hopList;
	
	private List<PostDto> postList;

	public List<HopDto> getHopList() {
		return hopList;
	}

	public void setHopList(List<HopDto> hopList) {
		this.hopList = hopList;
	}

	public List<PostDto> getPostList() {
		return postList;
	}

	public void setPostList(List<PostDto> postList) {
		this.postList = postList;
	}
}
