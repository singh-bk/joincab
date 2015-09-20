package com.bits.canvas.persistence.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bits.canvas.common.dto.AuthUserDetails;
import com.bits.canvas.persistence.entity.User;
import com.bits.canvas.persistence.repo.UserRepository;



@Component
public class RepositoryUserDetailService implements UserDetailsService {

	@Autowired	
	UserRepository repository;


	private static final Logger LOG = Logger.getLogger(RepositoryUserDetailService.class);
	

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = repository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("No user found with username: " + username);
		}
		AuthUserDetails principal = AuthUserDetails.getBuilder()

				.firstName(user.getFirstName())
				.id(user.getId())
				.lastName(user.getLastName())
				.password(user.getPassword())
				.role(user.getRole())
				.socialSignInProvider(user.getSignInProvider())
				.username(user.getEmail())
				.build();

		return principal;
	}

	public User getUserAccountsByEmail(String userId){
		User userAccounts = null;

		try{
			userAccounts = repository.findByEmail(userId);
		}
		catch(Exception e){
			LOG.error("Could not fetch details from User Accounts table using Mobile Number " + e.getMessage());
		}
		return userAccounts;


	}

	public User getUserAccount(String postId){

		User userAccounts = null;

		try{
			userAccounts = repository.findUserDetailsByPostId(postId);
		}
		catch(Exception e){
			LOG.error("Could not fetch details from User Accounts table " + e.getMessage());
		}
		return userAccounts;
	}

	public User getUserAccountByMobileNUmber(String mobileNumber){
		User userAccounts = null;

		try{
			userAccounts = repository.findByPhoneNumber(mobileNumber);
		}
		catch(Exception e){
			LOG.error("Could not fetch details from User Accounts table using Mobile Number " + e.getMessage());
		}
		return userAccounts;
	}
	
	public UserDetails logInUser(User user) {

	        AuthUserDetails userDetails = AuthUserDetails.getBuilder()
	                .firstName(user.getFirstName())
	                .id(user.getId())
	                .lastName(user.getLastName())
	                .password(user.getPassword())
	                .role(user.getRole())
	                .socialSignInProvider(user.getSignInProvider())
	                .username(user.getEmail())
	                .build();

	        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        return userDetails;
	}
}
