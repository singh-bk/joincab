package com.bits.canvas.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bits.canvas.persistence.entity.UserTestimonail;
import com.bits.canvas.response.TestimonialDto;

public interface UserTestimonialRepo  extends JpaRepository<UserTestimonail, Integer>,
JpaSpecificationExecutor<UserTestimonail>{

	@Query("select new com.bits.canvas.response.TestimonialDto(user.firstName,user.lastName,user.imageUrl,ut.testimonial) from User user,UserTestimonail ut where ut.email=user.email and ut.isViewable=1")
	public List<TestimonialDto> getTestimonial();
}
