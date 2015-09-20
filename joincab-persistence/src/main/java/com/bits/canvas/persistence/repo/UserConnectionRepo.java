package com.bits.canvas.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bits.canvas.persistence.entity.UserConnectionDtls;

public interface UserConnectionRepo extends JpaRepository<UserConnectionDtls, String>,JpaSpecificationExecutor<UserConnectionDtls>{

}
