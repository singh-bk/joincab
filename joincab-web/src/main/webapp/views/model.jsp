<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html  manifest="hytt.appcache">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="overlay dis-none">
		
	


		<!-- signin content starts here -->
<sec:authorize access="isAnonymous()">
			<div class="overlay-container signup-container sign-in-cont dis-none" id="signInPopup">

				<a href="#" class="cross-mark"></a>

				<!-- sign in starts here -->
				
				<div class="fleft signin">
					
					<h2 class="overlay-txt">Sign In</h2>
					<!-- error block -->
					<div class="display-error dis-none" id="error-container"></div>

					<!-- fb login start here -->
					<div class="socialLink">
					 <a href="#" id="facebookLogin">
					<button class="facebook-login">
						
						<span class="fleft fb-ico"></span>
						<span class="social-log-txt">Sign in Using Facebook </span>
					</button>
					</a>
					</div>
					<!--center border starts here-->

					<div class="border-container">
						<span class="border fleft"></span>
						<span class="fleft txt-border">OR</span>
						<span class="border fleft"></span>
					</div>
					<!-- google plus login starts here -->
					<div class="socialLink">
						 <a href="#" id="googlePlusLogin">
							<button class="facebook-login gplus">
								<span class="fleft gplus-ico"></span>
								<span class="social-log-txt">Sign in Using Google plus</span>
							</button>
						</a>
					</div>
					<!--google plus login ends here-->
						
					<div class="border-container">
						<span class="border fleft"></span>
						<span class="fleft txt-border">OR</span>
						<span class="border fleft"></span>
					</div>

					
					<!-- center border ends here -->


    <form action="" method="POST" role="form" id="loginForm"> 
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		
		  <input id="user-email" name="username" type="email" class="overlay-input margin-top form-control inField" placeholder="Email Address" data-validation="email"/>
		  <input id="user-password" name="password" type="password" class="overlay-input margin-top form-control inField" placeholder="Password" data-validation="required"/>
				
					<div class="rem-pasword">
						<div class="fleft">
							<input type="checkbox" id="chk-1"> <label for="chk-1">Remember me next time</label>
						</div>
						<a href="#" class="blue-link for-pass fright forgotPassword">Forgot Password?</a>

					</div>
						
						<div class="clearall"></div>

					
 	 </form>	
 	 <button class="tab-btn overlay-btn" type="submit" id="restLogin">Let me in</button>			
					<div class="clearall"></div>

					<!-- signup link starts here -->
		
					<div class="signup-link-cont">
						
						<span>Don't have an account?</span><a href="#" class="blue-link register" id="register1"> Join here</a>

					</div>

					<!-- signup link ends here -->



				</div>


				<!-- sign in end here -->

			</div>
</sec:authorize>
			<!-- sign in content ends here -->

			<!-- signup content starts here -->
	
			<div class="overlay-container signup-container sign-in-cont dis-none" id="signUpPopup">
<sec:authorize access="isAnonymous()">
				<a href="#" class="cross-mark"></a>

				<!-- sign in starts here -->
				
				<div class="fleft signin">
					
					<h2 class="overlay-txt">Sign Up</h2>
					<div class="display-error dis-none"></div>
      <form:form action="${pageContext.request.contextPath}/auth/register" commandName="registerationForm" method="POST" enctype="utf8" role="form" id="registerationForm"> 
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="name-input">
						
						<form:input class="fleft small-input inField" id="user-firstName" path="firstName" type="text" placeholder="First Name" data-validation="required"/>
						<form:input class="fleft small-input lst-name inField" id="user-lastName" path="lastName" type="text" placeholder="Last Name" data-validation="required"/>

					</div>	
					<form:input class="overlay-input margin-top inField" id="user-email-reg" type="email" path="email" placeholder="Email Address" data-validation="email"/>
					
					<%-- <form:input class="overlay-input margin-top" id="user-phonenumber" path="phoneNumber" placeholder="Phone Number" data-validation="number" length="10"/> --%>
					<div class="tab-data-container margin-top">
							
							<form:select id="user-gender" path="gender" placeholder="Gender">
								<option value='MALE'>Male</option>
								<option value='FEMALE'>Female</option>
							</form:select>

					</div>

					<form:input class="overlay-input margin-top inField" id="user-password-reg" path="password" placeholder="Enter Password" type="password" data-validation="required"/>
					<form:input class="overlay-input margin-top inField" id="user-passwordVerification" path="passwordVerification" placeholder="Confirm Password" type="password" data-validation="required"/>
					
			

					
	 </form:form> 	

				<button class="tab-btn overlay-btn" type="submit" id="register">Join In</button>
					<div class="clearall"></div>

					<!-- signup link starts here -->

					
					<div class="signup-link-cont">
						
						<span>Already have an account?</span><a href="#"  class="blue-link login" id="login1"> Sign in here</a>
						
					</div>


					<!-- signup link ends here -->



				</div>


				<!-- sign up end here -->


</sec:authorize>

			</div>
 
			<!-- sign up content ends here -->

			<!-- Forgot password starts here -->
			
			<div class="overlay-container signup-container sign-in-cont dis-none" id="forgot-password">

				<a href="#" class="cross-mark"></a>

				<!-- Forgot password starts here -->
				
				<div class="fleft signin">
					
					<h2 class="overlay-txt">Forgot Password</h2>

					<p>Please enter your email address and we will remind you of your password.</p>
						
					<input class="overlay-input margin-top inField" placeholder="Email Address" id="mail"/>
					
						<div class="border-container">
						<span class="border fleft"></span>
						<span class="fleft txt-border">OR</span>
						<span class="border fleft"></span>
						</div>


					<input class="overlay-input margin-top inField" placeholder="Mobile Number" id="mobile"/>

					<button class="tab-btn overlay-btn" id="forgotPwd">Submit</button>

					<div class="clearall"></div>

				</div>
			</div>

				<!-- forgot password ends here -->


				<!-- Reset password starts here -->

				<div class="overlay-container signup-container sign-in-cont dis-none" id="reset-password">

					<a href="#" class="cross-mark"></a>

					<div class="fleft signin">
					
					<h2 class="overlay-txt">Reset Password</h2>
				
					<p>Please enter your Password and submit</p>
						
					<input class="overlay-input margin-top inField" placeholder="Enter your password">
					<input class="overlay-input margin-top inField" placeholder="Re-Enter your password">
					<button class="tab-btn overlay-btn">Submit</button> 
	
					<div class="clearall"></div>

				</div>
			</div>
				<!-- Reset password ends here -->


   <!-- Error starts here -->
        
        <div class="center-container error alert dis-none" id="error">
				
				<div class="fleft commonpopupcont">
					
					<h2 class="common-popheading red-txt">Error</h2>

				
                        <p class="commonpoptxt"></p>
    
						
						<div class="clearall"></div>
                    <div class="fright commonbtn-container">
					    <button class="common-btn alert-btn fleft">Ok</button>
	                </div>

					<div class="clearall"></div>

				</div>

			</div>
            
            <!-- Error end here -->
            
            
            
            <!-- success starts here -->
              <div class="center-container success alert dis-none" id="success">
				<div class="fleft commonpopupcont">
					
					<h2 class="common-popheading green-txt">Success</h2>
				
                        <p class="commonpoptxt"></p>
						
						<div class="clearall"></div>
                    <div class="fright commonbtn-container">
					    <button class="common-btn alert-btn fleft">Cool</button>
	                </div>
					

					<div class="clearall"></div>

				</div>

			</div>
            
            <!-- success end here -->
         
         
            
            <!-- warning starts here -->
            
               <div class="center-container warning alert dis-none" id="warning">
				<div class="fleft commonpopupcont">
					
					<h2 class="common-popheading warning-txt">Warning</h2>

				
                        <p class="commonpoptxt">You are almost there ! we strongly recommend the following.</p>
    
						
						<div class="clearall"></div>
                    <div class="fright commonbtn-container">
					    <button class="common-btn fleft alert-btn">Ok</button>
	                </div>

					<div class="clearall"></div>

				</div>

			</div>
            
            <!-- warning end here -->
            
            
            
            
            <!-- Phone number and password starts here -->
            
            <div class="center-container phonepass dis-none model-win" id='phone-verification'>
				<div class="fleft commonpopupcont">
					
					<h2 class="common-popheading">Phone Details</h2>

				
                        <p class="commonpoptxt ver-pop-txt">Please enter your phone number here!</p>
    
						<div class="txtboxcommoncont">
						    <div id="verificationDiv">
						    <input class="commonpoptxtbox fleft inField" placeholder="Phone number" id="mobileNumber"/>
						    <div class="fleft">
						        
						        <button class="commonlink" id="ver-code">Apply</button>
						        
						    </div>
						    </div>
						    <div class="clearall"></div>
						    
						     <input class="commonpoptxtbox fleft inField" placeholder="Verification code" id="verCode"/>
						    <div class="fleft">
						        
						        
						    </div>
						    
						</div>
						
						
						<div class="clearall"></div>
                    <div class="fright commonbtn-container">
					    <button class="common-btn fleft" id="mobile-ver">Submit</button>
                        <button class="common-btn margin-left fleft cancel">Cancel</button>
	                </div>

					<div class="clearall"></div>

				</div>

			</div>
            
            <!-- Phone number and password ends here -->
		</div>
	<!-- overlay ends here -->
</body>
</html>