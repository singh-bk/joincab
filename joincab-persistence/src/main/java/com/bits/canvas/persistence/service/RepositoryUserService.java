package com.bits.canvas.persistence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bits.canvas.common.constant.SocialMediaService;
import com.bits.canvas.persistence.entity.User;
import com.bits.canvas.persistence.repo.UserRepository;
import com.bits.canvas.request.FacebookDto;
import com.bits.canvas.request.GooglePlusDto;
import com.bits.canvas.request.RegistrationForm;


/**
 * 5-JAN-2014
 * @author vatsritu
 */
@Service
public class RepositoryUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    public RepositoryUserService(PasswordEncoder passwordEncoder, UserRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Transactional
    public User registerNewUserAccount(RegistrationForm userAccountData) throws Exception {
        LOGGER.debug("Registering new user account with information: {}", userAccountData);

        if (emailExist(userAccountData.getEmail())) {
            LOGGER.debug("Email: {} exists. Throwing exception.", userAccountData.getEmail());
            throw new Exception("The email address: " + userAccountData.getEmail() + " is already in use.");
        }

        LOGGER.debug("Email: {} does not exist. Continuing registration.", userAccountData.getEmail());

        String encodedPassword = encodePassword(userAccountData.getPassword());

        User.Builder user = User.getBuilder()
                .email(userAccountData.getEmail())
                .firstName(userAccountData.getFirstName())
                .lastName(userAccountData.getLastName())
                .password(encodedPassword)
                //.phoneNumber("91"+userAccountData.getPhoneNumber())
                .gender(userAccountData.getGender());

            user.signInProvider(SocialMediaService.CUSTOM);
            user.imageUrl("images/uimg.jpg");
        User registered = user.build();

        LOGGER.debug("Persisting new user with information: {}", registered);

        return repository.save(registered);
    }

    private boolean emailExist(String email) {
        LOGGER.debug("Checking if email {} is already found from the database.", email);

        User user = repository.findByEmail(email);

        if (user != null) {
            LOGGER.debug("User account: {} found with email: {}. Returning true.", user, email);
            return true;
        }

        LOGGER.debug("No user account found with email: {}. Returning false.", email);

        return false;
    }

    private String encodePassword(String  password) {
        String encodedPassword = null;

            encodedPassword = passwordEncoder.encode(password);

        return encodedPassword;
    }
    
    @Transactional
    public User registerFacebookUser(FacebookDto facebookDto){

          if (emailExist(facebookDto.getEmail())) {
        	  User user = null;
        	   user =repository.findByEmail(facebookDto.getEmail());
        	  if(user != null ){
        		  return user;
        	  }
          }

          LOGGER.debug("Email: {} does not exist. Continuing registration.", facebookDto.getEmail());

          String encodedPassword = encodePassword("Ocwen@123");
          User.Builder user = User.getBuilder()
                  .email(facebookDto.getEmail())
                  .firstName(facebookDto.getFirst_name())
                  .lastName(facebookDto.getLast_name())
                  .password(encodedPassword)
                  .gender(facebookDto.getGender());

              user.signInProvider(SocialMediaService.FACEBOOK);
              user.imageUrl("http://graph.facebook.com/"+facebookDto.getId()+"/picture?type=large");

          User registered = user.build();

          LOGGER.debug("Persisting new user with information: {}", registered);

          return repository.save(registered);
    }
    
    public User getUserAccountsByEmail(String userId){
		User userAccounts = null;

		try{
			userAccounts = repository.findByEmail(userId);
		}
		catch(Exception e){
			LOGGER.error("Could not fetch details from User Accounts table using Email " + e.getMessage());
		}
		return userAccounts;
		
		
	}
    
    public User updateUserData(User user){
    	User user2 = null;
    	try {
			user2 = repository.save(user);
		} catch (Exception e) {
			 LOGGER.debug("Unable to update user information: {}", user);
		}
    	return user2;
    }

	public User getUserAccountsByMobile(String mobileNumber) {
		User userAccounts = null;

		try{
			userAccounts = repository.findByPhoneNumber(mobileNumber);
		}
		catch(Exception e){
			LOGGER.error("Could not fetch details from User Accounts table using Mobile Number " + e.getMessage());
		}
		return userAccounts;
		
	}

	public User resetPassword(User user) {
		try {
			 String encodedPassword = encodePassword(user.getPassword());
			 user.setPassword(encodedPassword);
			 user =repository.save(user);
		} catch (Exception e) {
			LOGGER.error("Error occured while reseting password " + e.getMessage());
		}
		
		return user;
	}

    @Transactional
    public User registerGoogleUser(GooglePlusDto googlePlusDto){

          if (emailExist(googlePlusDto.getPrimaryEmail())) {
        	  User user = null;
        	   user =repository.findByEmail(googlePlusDto.getPrimaryEmail());
        	  if(user != null ){
        		  return user;
        	  }
          }

          LOGGER.debug("Email: {} does not exist. Continuing registration.", googlePlusDto.getPrimaryEmail());

          String encodedPassword = encodePassword("Ocwen@123");
          User.Builder user = User.getBuilder()
                  .email(googlePlusDto.getPrimaryEmail())
                  .firstName(googlePlusDto.getGivenName())
                  .lastName(googlePlusDto.getFamilyName())
                  .password(encodedPassword)
                  .gender(googlePlusDto.getGender());

              user.signInProvider(SocialMediaService.GOOGLE);
              user.imageUrl(googlePlusDto.getImageUrl());

          User registered = user.build();

          LOGGER.debug("Persisting new user with information: {}", registered);

          return repository.save(registered);
    }

}
