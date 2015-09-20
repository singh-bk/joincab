package com.bits.canvas.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bits.canvas.persistence.entity.HopDetails;

public interface HopDetailsRepo extends JpaRepository<HopDetails, String>,
		JpaSpecificationExecutor<HopDetails> {

	@Query("SELECT hop From HopDetails hop where hop.cscId=:cscId And hop.status=:status")
	List<HopDetails> findByCscIdAndStatus(@Param("cscId") String cscId, @Param("status") Integer status);
	
}
