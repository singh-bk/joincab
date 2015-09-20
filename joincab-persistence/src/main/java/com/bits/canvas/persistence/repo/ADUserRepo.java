package com.bits.canvas.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bits.canvas.persistence.entity.ADUser;

public interface ADUserRepo extends JpaRepository<ADUser, String>,
		JpaSpecificationExecutor<ADUser> {

	ADUser findOne(String userId);
}
