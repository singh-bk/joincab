package com.bits.canvas.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bits.canvas.persistence.entity.CityDetails;
/**
 * Date: 24/5/14
 * @author vatsritu
 *
 */

public interface CityDetailsRepo extends JpaRepository<CityDetails, String>,
		JpaSpecificationExecutor<CityDetails> {

}
