package com.bits.canvas.mobile.helper;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.request.PostRequest;
import com.bits.canvas.mobile.transformer.PostTransformer;
import com.bits.canvas.persistence.entity.PostDetails;

@Component
public class PostHelper {

	@Autowired
	PostTransformer postTransformer;

	/**
	 * 
	 * @param postRequest
	 * @return
	 */
	public PostDetails populatePostDetails(PostRequest postRequest){
		PostDetails postDetails = new PostDetails();
		postDetails.setPostId(UUID.randomUUID().toString());
		postDetails = postTransformer.transform(postDetails, postRequest);
		return postDetails;
	}
}
