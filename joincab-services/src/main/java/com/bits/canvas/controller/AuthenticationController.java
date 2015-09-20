package com.bits.canvas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bits.canvas.common.constant.CommonConstants;
import com.bits.canvas.common.constant.SocialMediaService;
import com.bits.canvas.common.enums.EmailType;
import com.bits.canvas.common.enums.SMSType;
import com.bits.canvas.common.utils.CommonUtils;
import com.bits.canvas.communication.dto.MessageDto;
import com.bits.canvas.communication.send.SendEmail;
import com.bits.canvas.communication.send.SendSMS;
import com.bits.canvas.executor.task.Mail;
import com.bits.canvas.persistence.entity.User;
import com.bits.canvas.persistence.service.RepositoryUserDetailService;
import com.bits.canvas.persistence.service.UserService;
import com.bits.canvas.request.FacebookDto;
import com.bits.canvas.request.GooglePlusDto;
import com.bits.canvas.request.RegistrationForm;
import com.bits.canvas.response.CommonResponse;
import com.bits.canvas.response.LoginResult;
import com.bits.canvas.response.UserProfile;
import com.bits.canvas.transformer.EmailTransformer;
import com.bits.canvas.transformer.MessageTransformer;
import com.bits.canvas.transformer.ModelTransformer;

@Controller
public class AuthenticationController {

	@Autowired
	@Qualifier("myAuthenticationManager")
	AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("myRepository")
	SecurityContextRepository repository;

	@Autowired
	ModelTransformer modelTransformer;

	@Autowired
	MessageTransformer messageTransformer;

	@Autowired
	UserService userService;

	@Autowired
	SendSMS sendSMS;

	@Autowired
	SendEmail sendEmail;

	@Autowired
	RepositoryUserDetailService repositoryUserDetailService;

	@Autowired
	EmailTransformer emailTransformer;
	
	@Autowired
	Mail mail;
	
	@Autowired
	TaskExecutor taskExecutor;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@RequestMapping(value="/auth/login",method=RequestMethod.POST)
	@ResponseBody
	public LoginResult performLogin(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response)
	{
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(username, password);
		LoginResult loginResult = new LoginResult();
		try {
			HttpSession session = request.getSession();
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			repository.saveContext(SecurityContextHolder.getContext(), request, response);
			//rememberMeServices.loginSuccess(request, response, auth);
			User user = userService.getUserAccountsByEmail(username);
			UserProfile userProfile = modelTransformer.getUserProfile(user);
			loginResult.setUserProfile(userProfile);
			session.setAttribute("userProfile", userProfile);
			loginResult.setLoggedIn(true);
		} catch (BadCredentialsException ex) {
			loginResult.setLoggedIn(false);
			loginResult.setMessage("Username or password is incorrect.");
		}
		return loginResult;
	}


	@RequestMapping(value="/auth/facebook",method=RequestMethod.POST)
	@ResponseBody
	public LoginResult facebookLogIn(@ModelAttribute("facebookDto")FacebookDto facebookDto,HttpServletRequest request, HttpServletResponse response) {
		LoginResult loginResult = new LoginResult();
		User user=userService.registerFacebookUser(facebookDto);
		if(user.getSignInProvider().equals(SocialMediaService.CUSTOM)){
			loginResult.setLoggedIn(false);
			loginResult.setMessage("Email is already registered. Recover your password using forgot password link.");
			return loginResult;
		}
		else if(user.getSignInProvider().equals(SocialMediaService.GOOGLE)){
			loginResult.setLoggedIn(false);
			loginResult.setMessage("Email is already registered for google plus login. Please use Login Using Google Plus");
			return loginResult;
		}
		HttpSession session = request.getSession();
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(facebookDto.getEmail(), "Ocwen@123");
		Authentication auth = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(auth);
		repository.saveContext(SecurityContextHolder.getContext(), request, response);
		UserProfile userProfile = modelTransformer.getFacebookUserProfile(user,facebookDto);
		loginResult.setUserProfile(userProfile);
		loginResult.setLoggedIn(true);
		session.setAttribute("userProfile", userProfile);
		return loginResult;
	}

	@RequestMapping(value="/auth/google",method=RequestMethod.POST)
	@ResponseBody
	public LoginResult facebookLogIn(@ModelAttribute("googlePlusDto")GooglePlusDto googlePlusDto,HttpServletRequest request, HttpServletResponse response) {
		LoginResult loginResult = new LoginResult();
		User user=userService.registerGoogleUser(googlePlusDto);
		if(user.getSignInProvider().equals(SocialMediaService.CUSTOM)){
			loginResult.setLoggedIn(false);
			loginResult.setMessage("Email is already registered. Recover your password using forgot password link.");
			return loginResult;
		}
		else if(user.getSignInProvider().equals(SocialMediaService.FACEBOOK)){
			loginResult.setLoggedIn(false);
			loginResult.setMessage("Email is already registered for facebook login. Please use Login Using Facebook");
			return loginResult;
		}
		HttpSession session = request.getSession();
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(googlePlusDto.getPrimaryEmail(), "Ocwen@123");
		Authentication auth = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(auth);
		repository.saveContext(SecurityContextHolder.getContext(), request, response);
		UserProfile userProfile = modelTransformer.getGooglePlusUserProfile(user, googlePlusDto);
		loginResult.setUserProfile(userProfile);
		loginResult.setLoggedIn(true);
		session.setAttribute("userProfile", userProfile);
		return loginResult;
	}

	/**
	 * Processes the form submissions of the registration form with rest.
	 * @throws Exception 
	 */
	@RequestMapping(value ="auth/register", method = RequestMethod.POST)
	@ResponseBody
	public LoginResult registerUserAccount(@ModelAttribute("registerationForm") RegistrationForm registrationForm,HttpServletRequest request, HttpServletResponse response) throws Exception{
		LoginResult registrationResult = new LoginResult();
		LOGGER.debug("Registering user account with information: {}", registrationForm);
		HttpSession session = request.getSession();
		User registered = userService.registerNewUserAccount(registrationForm);

		//If email address was already found from the database, render the form view.
		if (registered == null) {
			registrationResult.setLoggedIn(false);
			registrationResult.setMessage("Email already exists in database.");
			return registrationResult;
		}else{
			MessageDto messageDto = emailTransformer.prepareWelcomeEmailDto(registered);
			messageDto.setEmailType(EmailType.WELCOME_EMAIL_TEMPLATE);
			messageDto.setEmailSubject(CommonConstants.WELCOME_EMAIL_TEMPLATE);
			
			mail.setMessageDto(messageDto);
			//send Email
			taskExecutor.execute(mail);
		}

		//Logs the user in.
		repositoryUserDetailService.logInUser(registered);
		LOGGER.debug("User {} has been signed in", registered);
		registrationResult.setLoggedIn(true);
		UserProfile userProfile = modelTransformer.getUserProfile(registered);
		registrationResult.setUserProfile(userProfile);
		registrationResult.setMessage("Registered Succesfully.");
		session.setAttribute("userProfile", userProfile);
		return registrationResult;
	}

	@RequestMapping(value="/auth/verCode",method=RequestMethod.POST)
	@ResponseBody
	public CommonResponse getMobileVerificationCode(@RequestParam("mobileNumber") String mobileNumber,HttpServletRequest request){
		CommonResponse commonResponse = new CommonResponse();
		UserProfile userProfile = CommonUtils.getProfileFromSession(request);
		User user =userService.getUserAccountsByMobile("91"+mobileNumber);
		if(user != null){
			commonResponse.setSuccess(false);
			commonResponse.setMessage("Mobile Number already registered. Please use another mobile number.");
		}else{
			MessageDto messageDto = new MessageDto();
			messageDto.setUser(userProfile.getFirstName());
			messageDto.setVerificationCode(CommonUtils.getVerificationCode());
			messageDto.setRecipientNumber(mobileNumber.trim());
			messageDto.setSmsType(SMSType.VERIFY_TEMPLATE);
			HttpSession session = request.getSession();
			session.setAttribute("verCode", messageDto.getVerificationCode());
			boolean isSent = sendSMS.sendMessage(messageDto);
			if(isSent){
				commonResponse.setSuccess(isSent);
				commonResponse.setMessage("Verification code has been sent to your mobile.");
			}else{
				commonResponse.setSuccess(isSent);
				commonResponse.setMessage("Unable to send message to your mobile.");
			}
		}
		return commonResponse;
	}

	@RequestMapping(value="/auth/verMobile",method=RequestMethod.POST)
	@ResponseBody
	public CommonResponse getMobileVerificationCode(@RequestParam("mobileNumber") String mobileNumber,@RequestParam("verificationCode") String verificationCode,HttpServletRequest request){
		CommonResponse commonResponse = new CommonResponse();
		UserProfile userProfile = CommonUtils.getProfileFromSession(request);
		HttpSession session = request.getSession();
		String verCode = (String)session.getAttribute("verCode");
		if(verCode.trim().equals(verificationCode)){
			User user =userService.getUserAccountsByEmail(userProfile.getEmail());
			user.setPhoneNumber("91"+mobileNumber);
			user = userService.updateUserData(user);
			if(user != null){
				commonResponse.setSuccess(true);
				commonResponse.setMessage("Your mobile has been verified. Enjoy our services.");
			}else{
				commonResponse.setSuccess(false);
				commonResponse.setMessage("Error occured. Please try again.");
			}
		}else{
			commonResponse.setSuccess(false);
			commonResponse.setMessage("Verification code is not correct. Please try again.");
		}

		return commonResponse;
	}


	@RequestMapping(value="/auth/forgotPassword",method=RequestMethod.POST)
	@ResponseBody
	public CommonResponse getPassword(@RequestParam("mobileNumber") String mobileNumber,@RequestParam("email") String email,HttpServletRequest request){
		CommonResponse commonResponse = new CommonResponse();
		User user = null;
		boolean isSent = false;

		if(StringUtils.hasText(mobileNumber)){
			mobileNumber = "91"+mobileNumber;
			user = userService.getUserAccountsByMobile(mobileNumber);

			if(user != null){
				if(user.getSignInProvider().equals(SocialMediaService.FACEBOOK)){
					commonResponse.setSuccess(true);
					commonResponse.setMessage("You are registered with facebook. Please login with facebook.");
				}else if(user.getSignInProvider().equals(SocialMediaService.CUSTOM)){
					isSent = recoverPasswordWithMobile(mobileNumber,user);
					if(isSent){
						commonResponse.setSuccess(true);
						commonResponse.setMessage("Password has been sent to you mobile..!!");
					}else{
						commonResponse.setSuccess(false);
						commonResponse.setMessage("Unable to send password to your mobile..!!");
					}
				}
			}else if(StringUtils.hasText(email)){
				commonResponse = recoverPasswordWithEmail(email);
			}else{
				commonResponse.setSuccess(false);
				commonResponse.setMessage("We do not have details for this number. Please check with your email id. <br/><br/> OR <br/><br/> "
						+ "Please login with Facebook/G+ or create an account");
			}
		}else if(StringUtils.hasText(email)){
			commonResponse = recoverPasswordWithEmail(email);
		}
		return commonResponse;
	}

	public CommonResponse recoverPasswordWithEmail(String email){

		CommonResponse commonResponse = new CommonResponse();
		User user = null;
		boolean isSent = false;

		user = userService.getUserAccountsByEmail(email);
		if(user !=null){
			if(user.getSignInProvider().equals(SocialMediaService.FACEBOOK)){
				commonResponse.setSuccess(true);
				commonResponse.setMessage("You are registered with facebook. Please login with facebook,");
			}else if(user.getSignInProvider().equals(SocialMediaService.CUSTOM)){
				String password = RandomStringUtils.randomAlphanumeric(8);
				user.setPassword(password);
				user=userService.resetPassword(user);
				if(user !=null){
					MessageDto messageDto = emailTransformer.prepareForgotEmailDto(user,password);
					isSent = sendEmail.sendEmailNotification(messageDto);
					if(isSent){
						commonResponse.setSuccess(true);
						commonResponse.setMessage("Password has been sent to you Email Id");
					}else{
						commonResponse.setSuccess(false);
						commonResponse.setMessage("Unable to send password to your Email Id..!!");
					}
				}
			}
		}else{
			commonResponse.setSuccess(false);
			commonResponse.setMessage("You are not registered with us yet. Please login with Facebook/G+ or create an account");
		}

		return commonResponse;
	}

	public boolean recoverPasswordWithMobile(String mobileNumber,User user){

		boolean isSent = false;

		if(user != null){
			String password = RandomStringUtils.randomAlphanumeric(8);
			user.setPassword(password);
			user=userService.resetPassword(user);
			MessageDto messageDto = messageTransformer.prepareForotPwdMessageDto(user,password);
			isSent=sendSMS.sendMessage(messageDto);
		}

		return isSent;
	}
}