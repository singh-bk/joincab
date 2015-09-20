$(document).ready(function(){

/* check the user is logged in or not on refresh*/
	$(function() {
		checkLoginOnRefresh();
	});

	/*Google Login starts here*/
	$('#googlePlusLogin').click(function(){
		var myParams = {
		    'clientid' : '214405173114-skp1n196hhtknr0e7ovd1f3m36vm9mug.apps.googleusercontent.com', //You need to set client id
		    'cookiepolicy' : 'single_host_origin',
		    'callback' : function(authResult){gapi.client.load('plus', 'v1', apiClientLoaded);},
		    'scope' : 'email'
		};
		gapi.auth.signIn(myParams);
	});
	var apiClientLoaded = function() {
    	gapi.client.plus.people.get({userId: 'me'}).execute(handleEmailResponse);
  	};
  	var handleEmailResponse = function(resp) {
  		saveGoogleUserDetails(resp);
  	};

  	var saveGoogleUserDetails = function(resp){
  		    var primaryEmail;
    		for (var i=0; i < resp.emails.length; i++) {
      			if (resp.emails[i].type === 'account') primaryEmail = resp.emails[i].value;
    		}
    		var gender;
    		if(resp.gender != null){
    			gender = resp.gender;
    		}else{
    			gender = "OTHERS";
    		}
			$.ajax({
				url: './auth/google',
				type: 'POST',
				dataType: 'json',
				data: {
					id: resp.id,
					primaryEmail: primaryEmail,
					displayName: resp.displayName,
					givenName: resp.name.givenName,
					familyName: resp.name.familyName,
					gender: gender,
					imageUrl: resp.image.url
				}
			})
			.done(function(response) {
				if(response.loggedIn){
					 if(response.loggedIn){
						closePopUp('signIn');
						loggedUser(response);
						if(!response.userProfile.verified){
							showPopUp('phone-ver');
						}
					}else{
						showError(response.message);
					}
				}else{
					showError(response.message);
				}
			})
			.fail(function() {
				alert("Sorry !! Something went wrong..");
			})
			.always(function() {
				console.log("complete");
			});
	};
	/*Google login ends here*/

$('#facebookLogin').click(function(){
			$.fblogin({
					    fbId: '360376000776504',
					    permissions: 'email,user_birthday',
					    success: function (data) {
					        console.log('Basic public user data returned by Facebook', data);
					        saveUserDetails(data);
					    },
					    error: function (error) {
					        console.log('An error occurred.', error);
					    }
					});
		});

var saveUserDetails = function(data){

	$.ajax({
		url: './auth/facebook',
		type: 'POST',
		dataType: 'json',
		data: data,
	})
	.done(function(response) {
		if(response.loggedIn){
			closePopUp('signIn');
			loggedUser(response);
			if(!response.userProfile.verified){
				showPopUp('phone-ver');
			}
		}else{
			showError(response.message);
		}
	})
	.fail(function() {
		alert("Sorry !! Something went wrong..");
	})
	.always(function() {
		console.log("complete");
	});
	
};



var loggedUser =function(data){
		$('#anonymous').addClass('dis-none');
		$('#authenticated').removeClass('dis-none');
		if(data.userProfile.imageUrl != null){
			$('#userImage').attr("src",data.userProfile.imageUrl);
		}else{
			$('#userImage').attr("src","http://graph.facebook.com/"+data.userProfile.providerUid+"/picture?type=small");
		}
		$('#userName').text(data.userProfile.firstName);
		$.jStorage.set('userDetails', data);
		var accessToken = data.userProfile.accessToken;
		$.jStorage.set('accessToken', accessToken);
		
};



var checkLoginOnRefresh = function(){
	var data = $.jStorage.get('userDetails');
	if(data.loggedIn){
	if(data.userProfile.provider=='FACEBOOK'){
		$('#anonymous').addClass('dis-none');
		$('#authenticated').removeClass('dis-none');
		$('#userImage').attr("src","http://graph.facebook.com/"+data.userProfile.providerUid+"/picture?type=small");
		$('#userName').text(data.userProfile.firstName);
	}else if(data.userProfile.provider=='CUSTOM'){
		$('#anonymous').addClass('dis-none');
		$('#authenticated').removeClass('dis-none');
		$('#userImage').attr("src","http://graph.facebook.com/undefined/picture?type=small");
		$('#userName').text(data.userProfile.firstName);
	}else if(data.userProfile.provider=='GOOGLE'){
		$('#anonymous').addClass('dis-none');
		$('#authenticated').removeClass('dis-none');
		$('#userImage').attr("src",data.userProfile.imageUrl);
		$('#userName').text(data.userProfile.firstName);
	}
	}
};


var userRegistration =  function(){
		$.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' }); 
		 var firstName = $('#user-firstName').val();
			 var lastName = $('#user-lastName').val();
			 var email = $('#user-email-reg').val();
			/* var phoneNumber = $('#user-phonenumber').val();*/
			 var password = $('#user-password-reg').val();
			 var gender = $('#user-gender').val();
			 var passwordVerification = $('#user-passwordVerification').val();
			 $.ajax({
			 	url: './auth/register',
			 	type: 'POST',
			 	data: {firstName:firstName,
			 		lastName:lastName,
			 		email:email,
			 		/*phoneNumber:phoneNumber,*/
			 		password:password,
			 		gender:gender,
			 		passwordVerification:passwordVerification},
			 })
			 .success(function(response) {
			 	if(response.loggedIn){
					closePopUp('signUp');
					if(!response.userProfile.verified){
						showPopUp('phone-ver');
					}
					loggedUser(response);
				}else{
					showError(response.message);
				}
				 
			 })
			 .error(function() {
			 	console.log("error");
			 });
	};

	var loginProc = function(){
		$.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' }); 
		 var password = $('#user-password').val();
				 var userId = $('#user-email').val();
				 if(userId == ("") && password ==("")){
				 loginValidation();
				 }
				 $.ajax({
				 	url: './auth/login',
				 	type: 'POST',
				 	data: {username:userId,
				 		password:password},
				 })
				 .success(function(response) {
					 if(response.loggedIn){
						closePopUp('signIn');
						if(!response.userProfile.verified){
							showPopUp('phone-ver');
						}
						loggedUser(response);
					}else{
						showError(response.message);
					}
				 })
				 .error(function() {
				 	console.log("error");
				 });
		 
	};

	$('#register').click(function(){
			userRegistration();
		
	});
	$('#restLogin').click(function(){
		var isvalid = $('#loginForm').validateForm();
		if(isvalid){
			 loginProc();
		}else{
			showError("Please fill the highlighted field.");
		}
		
	});	
	
$('#userName').click(function(event) {
	$('#userNav').removeClass('dis-none');
});

$(function () {
    $('#userNav').on('blur', function (event) {
        $(this).addClass('dis-none');
    });
});

$('#logout').click(function(event) {
	var data = $.jStorage.get('userDetails');
	if(data.loggedIn){
		if(data.userProfile.provider=='GOOGLE'){
			gapi.auth.signOut();
			window.open('https://accounts.google.com/logout', '_blank'); 
		}
		if(data.userProfile.provider=='FACEBOOK'){
			$.fblogout();
		}
	}
	$.jStorage.flush();
});

var showError = function(data){
	$('.display-error').removeClass('dis-none');
	$('.display-error').html(data);
};



	var verifyMobile = function(){
		var mobileNumber = $('#mobileNumber').val();
		var verificationCode = $('#verCode').val();
		if(mobileNumber == "" && verificationCode == ""){
			popUpMessage('Please fill the below fields.');
			return false;
		}
		$.ajax({
				 	url: './auth/verMobile',
				 	type: 'POST',
				 	data: {mobileNumber:mobileNumber,
				 		verificationCode:verificationCode},
				 })
				 .success(function(response){
				 	if(response.success){
				 		var userDetails = $.jStorage.get('userDetails');
				 		userDetails.userProfile.verified = true;
				 		updateUserDetails(userDetails);
				 		closePopUp('phone-ver');
				 		showPopUp('success');
				 		popUpMessage(response.message);
				 	}else{
				 		 $('.ver-pop-txt').replaceWith(response.message);
				 	}
				 	
				 })
				 .error(function(response) {
				 	closePopUp('phone-ver');
				 	showPopUp('error');
				 	popUpMessage(response.message);	
				 });
	};

	var getVerificationCode = function(mobileNumber){
		if(mobileNumber == ""){
			popUpMessage('Please fill the below fields.');
			return false;
		}else{
			$('#verificationDiv').addClass('dis-none');
		}
		$.ajax({
				 	url: './auth/verCode',
				 	type: 'POST',
				 	data: {mobileNumber:mobileNumber},
				 })
				.success(function(response){
				 	if(!response.success){
				 		  $('.ver-pop-txt').replaceWith(response.message);
				 		  $('#verificationDiv').removeClass('dis-none');
				 	}
				 	
				 })
				 .error(function(response) {
				 	closePopUp('phone-ver');
				 	showPopUp('error');
				 	popUpMessage(response.message);
				 });
	};

var getPassword = function(mobileNumber,email){
	$.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' }); 
		$.ajax({
			url: './auth/forgotPassword',
			type: 'POST',
			data: {mobileNumber: mobileNumber,email:email},
		})
		.done(function(response) {
			if(response.success){
				closePopUp('forgot-password');
				showPopUp('success');
				popUpMessage(response.message);
			}else{
				closePopUp('forgot-password');
				showPopUp('error');
				popUpMessage(response.message);
			}
		})
		.fail(function() {
			console.log("error");
		})
		.always(function() {
			console.log("complete");
		});
		
	};

	$('#forgotPwd').click(function(event) {
		var mobileNumber = $('#mobile').val();
		var mail = $('#mail').val();
		getPassword(mobileNumber,mail);
	});

	$('.forgotPassword').click(function(event) {
		showPopUp('forgot-password');
	});
	$('#ver-code').click(function(event) {
		var mobileNumber = $('#mobileNumber').val();
		getVerificationCode(mobileNumber);
	});

	$('#mobile-ver').click(function(event) {
		verifyMobile();
	});

	$('.alert-btn').click(function(event) {
		$('.alert').addClass("dis-none");
		$('.overlay').addClass("dis-none");
	});

	$('.cancel').click(function(event) {
		$('.model-win').addClass("dis-none");
		$('.overlay').addClass("dis-none");
	});

	$('.login').click(function(event) {
		$('.overlay').removeClass("dis-none");
		$('#signUpPopup').addClass('dis-none');
		$('#signInPopup').removeClass('dis-none');
	});

	$('.register').click(function(event) {
		$('.overlay').removeClass("dis-none");
		$('#signInPopup').addClass('dis-none');
		$('#signUpPopup').removeClass('dis-none');
	});

	$('.cross-mark').click(function(event) {
		$('.overlay').removeClass("dis-none");
		closePopUp('signIn');
		closePopUp('signUp');
	});
});


/*global methods*/
var showPopUp = function(data){
	clearPopUpForm();
	if(data == 'phone-ver'){
		$('.overlay').removeClass('dis-none');
		$('#phone-verification').removeClass('dis-none');
	}else if(data == 'success'){
		$('.overlay').removeClass('dis-none');
		$('#success').removeClass('dis-none');
	}else if(data == 'error'){
		$('.overlay').removeClass('dis-none');
		$('#error').removeClass('dis-none');
	}else if(data == 'warning'){
		$('.overlay').removeClass('dis-none');
		$('#warning').removeClass('dis-none');
	}else if(data == 'signIn'){
		$('.overlay').removeClass('dis-none');
		$('#signInPopup').removeClass('dis-none');
		$('.display-error').text('');
	}else if(data == 'signUp'){
		$('.overlay').removeClass('dis-none');
		$('#signUpPopup').removeClass('dis-none');
	}else if(data == 'forgot-password'){
		$('.overlay').removeClass("dis-none");
		$('#signInPopup').addClass('dis-none');
		$('#forgot-password').removeClass('dis-none');
	}
};

var closePopUp = function(data){
		clearPopUpForm();
		$('.overlay').removeClass("dis-block");
		$('.overlay').addClass("dis-none");
		$('.commonpoptxt').append("");
		if(data == 'signIn'){
			$('.overlay').addClass('dis-none');
			$('#signInPopup').addClass('dis-none');
		}else if(data == 'signUp'){
			$('.overlay').addClass('dis-none');
			$('#signUpPopup').addClass('dis-none');
		}else if(data == 'phone-ver'){
			$('.overlay').addClass('dis-none');
			$('#phone-verification').addClass('dis-none');
		}else if(data == 'reset-password'){
			$('.overlay').addClass('dis-none');
			$('#reset-password').addClass('dis-none');
		}else if(data == 'forgot-password'){
			$('.overlay').addClass('dis-none');
			$('#forgot-password').addClass('dis-none');
		}
};

var popUpMessage = function(data){
	$('.commonpoptxt').text('');
	$('.commonpoptxt').append(data);
};

var updateUserDetails = function(data){
	$.jStorage.deleteKey('userDetails');
	$.jStorage.set('userDetails', data);
};

var clearPopUpForm = function(){
	$('.inField').val('');
};
