package com.bits.canvas.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bits.canvas.persistence.entity.User;

public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
	 
    public User findByEmail(String email);
    
    
	@Query(value = "select u from CabPostDetails c,User u where c.userId=u.email and c.id = ?1")
	User findUserDetailsByPostId(String postId);
	
	User findByPhoneNumber(String phoneNumber);
}
