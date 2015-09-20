package com.bits.canvas.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bits.canvas.persistence.entity.TripDetails;

public interface TripDetailsRepo extends JpaRepository<TripDetails, String>,
		JpaSpecificationExecutor<TripDetails> {

	//public Integer 
	@Query("UPDATE TripDetails trip set trip.status=:status WHERE trip.tripId IN (:tripIdList)")
	@Modifying
	public Integer updateTripStatus(@Param("tripId") List<String> tripIdList, @Param("status") Integer status);
}
