package com.bits.canvas.persistence.service;

import org.springframework.stereotype.Component;

import com.bits.canvas.persistence.entity.User;
import com.bits.canvas.request.FacebookDto;
import com.bits.canvas.request.GooglePlusDto;
import com.bits.canvas.request.RegistrationForm;


@Component
public interface UserService {

	/**
     * Creates a new user account to the service.
     * @param userAccountData   The information of the created user account.
     * @return  The information of the created user account.
     * @throws DuplicateEmailException Thrown when the email address is found from the database.
     */
    public User registerNewUserAccount(RegistrationForm userAccountData) throws Exception;
    public User registerFacebookUser(FacebookDto facebookDto);
    public User registerGoogleUser(GooglePlusDto googlePlusDto);
    public User getUserAccountsByEmail(String userId);
    public User updateUserData(User user);
    public User getUserAccountsByMobile(String mobileNumber);
    public User resetPassword(User user);
}