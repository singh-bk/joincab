package com.bits.canvas.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bits.canvas.persistence.entity.CabPostDetails;
import com.bits.canvas.response.HopSearchResultDto;

/**
 * @author vatsritu
 * May 24, 2014
 */
public interface CabPostDetailsRepo extends JpaRepository<CabPostDetails, String>,
		JpaSpecificationExecutor<CabPostDetails> {

	@Query("SELECT new com.bits.canvas.persistence.dto.JourneyDetailsDto(POST.id, POST.pickUpTime,  POST.postFormattedAddress, user.firstName, user.lastName, POST.postStatus) FROM CabPostDetails POST, User user WHERE user.email = POST.userId AND POST.userId = ?1 AND POST.postStatus = ?2")
	List<com.bits.canvas.persistence.dto.JourneyDetailsDto> findByUserIdAndStatus(String userId,Integer status);

	@Query("SELECT new com.bits.canvas.persistence.dto.JourneyDetailsDto(POST.id, POST.pickUpTime,  POST.postFormattedAddress, user.firstName, user.lastName, POST.postStatus) FROM CabPostDetails POST, CabHopDetails HOP, User user WHERE user.email = HOP.userId AND POST.id = HOP.postId AND POST.userId = ?1 AND POST.postStatus in (?2, ?3) and HOP.status in(1,2)")
	List<com.bits.canvas.persistence.dto.JourneyDetailsDto> findByUserIdAndStatus(String userId, Integer status1, Integer status2);
	
	@Query(value = "SELECT new com.bits.canvas.response.HopSearchResultDto(user.firstName,user.lastName,DATE_FORMAT(cab.pickUpTime,'%d-%b-%Y  \\@\\ %h:%i %p'),cab.cscId,cab.postFormattedAddress,cab.id as postId,cab.longitude,cab.latitude) FROM CabPostDetails cab,User user WHERE cab.cscId = ?1 AND cab.pickUpTime >= ?2 and cab.pickUpTime <= ?3 and cab.vehicleType= ?4 and user.email=cab.userId and cab.postStatus=1 and cab.share=1")
	List<HopSearchResultDto> findByCscIdAndStartDateAndEndDate(String cscId, String startTime, String endTime, Integer seatAvailable);
	
	public List<CabPostDetails> findAllByUserId(String userId);

}
