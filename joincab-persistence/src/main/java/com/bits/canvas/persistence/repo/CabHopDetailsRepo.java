package com.bits.canvas.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bits.canvas.persistence.entity.CabHopDetails;
/**
 * Date: 24/5/14
 * @author vatsritu
 *
 */

public interface CabHopDetailsRepo extends JpaRepository<CabHopDetails, Integer>,
		JpaSpecificationExecutor<CabHopDetails> {

	@Query(value = "select h from CabHopDetails h,CabPostDetails p where h.postId = p.id and p.userId = ?1 and p.joinMsgCode = ?2")
	CabHopDetails findByPostIdAndJoinMsgCode(String postId, String joinMsgCode);
	
	CabHopDetails findByPostIdAndStatus(String postId,Integer status);
	CabHopDetails findByPostId(String postId);
	
	@Query("SELECT new com.bits.canvas.persistence.dto.JourneyDetailsDto(POST.id, POST.pickUpTime,  POST.postFormattedAddress, user.firstName, user.lastName, HOP.status) FROM CabPostDetails POST, CabHopDetails HOP, User user WHERE user.email = HOP.userId AND POST.id = HOP.postId AND HOP.userId = ?1")
	List<com.bits.canvas.persistence.dto.JourneyDetailsDto> findAllHopDetailsByUserId(String userId);
}
