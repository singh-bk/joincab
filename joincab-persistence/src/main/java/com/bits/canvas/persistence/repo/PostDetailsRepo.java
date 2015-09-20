package com.bits.canvas.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bits.canvas.persistence.entity.PostDetails;

public interface PostDetailsRepo extends JpaRepository<PostDetails, String>,
		JpaSpecificationExecutor<PostDetails> {

	@Query("SELECT post.status FROM PostDetails post WHERE post.postId = :postId")
	public Integer getStatusForPostId(@Param("postId") String postId);
	
	@Query("SELECT post From PostDetails post where post.cscId=:cscId And post.status IN :status")
	List<PostDetails> findByCscIdAndStatus(@Param("cscId") String cscId, @Param("status") List<Integer> status);

}
