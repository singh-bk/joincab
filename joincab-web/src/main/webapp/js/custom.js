$(document).ready(function(){

	$( "#accordion" ).accordion({
		collapsible: true
	});

	/* google - maps */

	$('.triggerMap').click(function(){
		var id = $(this).attr('id');
		displayDirection(id);
	});

	$('.triggerMap').mouseover(function(){
		var id = $(this).attr('id');
		animateMarker(id);
	});

	$('.triggerMap').mouseout(function(){
		var id = $(this).attr('id');
		removeAnimationMarker(id);
	});	

	/* Date and timepicker code */

	$(".date").datepicker({ minDate: 0,maxDate :7}).attr('readonly','readonly');

	$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
		jqXHR.setRequestHeader('X-CSRF-Token', csrf_token);
		jqXHR.setRequestHeader('Content-Type', "application/json;charset=UTF-8");
	});




	/* hop and post tab code */

	$('.tab-1').hide();
	$('.tab-heading ul li a:first').addClass("active").show();
	$('.tab-1:first').show();

	/*search tab navigation function*/







	$('.tab-heading ul li a').click(function(){
		$('.tab-heading ul li a').removeClass("active");
		$(this).addClass("active");
		$('.tab-1').hide();
		var activeTab = $(this).attr("href");
		$(activeTab).show();
		$('html, body').stop();
		return false;
	});

	$(".time").on("focus",function(e){
		$(".time").timepicker({
			step:30,
			showSecond: false,
			dateFormat: "dd-mm-yy", 
			timeFormat: "H:i"
		});
	});


	/* testimonial slider code */
	$('.join').click(function(){
		$.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' }); 
		var userDetails = $.jStorage.get('userDetails');
		if(userDetails == null || userDetails.userProfile.accessToken == "" || userDetails.userProfile.accessToken == undefined){
			showPopUp('signIn');
			return false;
		}else if(!userDetails.userProfile.verified){
			showPopUp('phone-ver');
			return false;
		}
		var address = $('#hopFormattedAddress').val();
		var postId = $(this).attr('id');
		var data = $.jStorage.get('userDetails');
		var userId = data.userProfile.email;
		var cscId = $.jStorage.get('cscId');
		$.ajax({
				 	url: './joinCab',
				 	type: 'POST',
				 	dataType: 'json',
				 	data: {hopFormattedAddress:address,
					postId:postId,
					userId:userId,
					cscId:cscId},
				 })
				.done(function(response){
				 	if(response.success){
						$('.'+postId).addClass('dis-none');
						removeMarker(currentMarkerIndex);
						showPopUp('success');
						popUpMessage(response.message);
					}
				 	
				 })
				 .fail(function(response) {
				 	closePopUp('phone-ver');
				 	showPopUp('error');
				 	popUpMessage(response.message);
				 });
	});	

	$("#hopCab").on("click",function(e){
		var cscId = $('#dd span').attr('id');
		var hopLocation = $("#hopLocation").val();
	    if(hopLocation != cacheHopLocation){
			$('#hopFormattedAddress').val('');	
		}
		var date =  $("#hopDate").val();
		var time = $('#dd2 span').attr('id');
		var vehicleType= $('#dd3 span').attr('id');
		if(cscId == undefined || time == undefined || vehicleType == undefined || hopLocation == '' || date == ''){
			alert("Please Provide City, Location, Date, Time and Vehicle Type");
			return false;
		}
		$("#cscId").val(cscId);
		$("#hopTime").val(time);
		$("#vehicleType").val(vehicleType);
		/*for populating dropdown on other pages*/
		$.jStorage.set('vehicleType', $('#dd3 span').text());
		$.jStorage.set('time', $('#dd2 span').text());
		$.jStorage.set('vehicleTypeId', vehicleType);
		$.jStorage.set('timeId', time);
		/*for populating dropdown end*/

		$.jStorage.set('cscId', cscId);
	});

	$("#postCab").on("click",function(e){
		
		var userDetails = $.jStorage.get('userDetails');
		if(userDetails == null || userDetails.userProfile.accessToken == "" || userDetails.userProfile.accessToken == undefined){
			showPopUp('signIn');
			return false;
		}else if(!userDetails.userProfile.verified){
			showPopUp('phone-ver');
			return false;
		}
		var cscId = $('#dd span').attr('id');
		var postLocation = $("#postLocation").val();
		if(postLocation != cachePostLocation){
			$('#postFormattedAddress').val('');	
		}
		var date =  $("#postDate").val();
		var time = $('#dd4 span').attr('id');
		var genderPref= $('#dd5 span').attr('id');
		var vehicleType= $('#dd6 span').attr('id');
		var isShared = $('#isShared').val();
		if(cscId == undefined || time == undefined || vehicleType == undefined || genderPref == undefined || postLocation == '' || date == ''){
			alert("Please Provide City, Location, Date, Time, Gender Preference and Vehicle Type");
			return false;
		}
		$("#postCscId").val(cscId);
		$("#postTime").val(time);
		$("#postVehicleType").val(vehicleType);
		$('#genderPref').val(genderPref);
		$('#transactionId').val(date+time);

		$.jStorage.set('cscId', cscId);
	});

	$("#bookCab").on("click",function(e){
		var userDetails = $.jStorage.get('userDetails');
		if(userDetails == null || userDetails.userProfile.accessToken == "" || userDetails.userProfile.accessToken == undefined){
			showPopUp('signIn');
			return false;
		}else if(!userDetails.userProfile.verified){
			showPopUp('phone-ver');
			return false;
		}
		var cscId = $('#dd span').attr('id');
		var postLocation = $("#postLocation").val();
		/*
			if(postLocation != cachePostLocation){
				$('#postFormattedAddress').val('');	
			}
		*/
		var date =  $("#postDate").val();
		var time = $('#dd4 span').attr('id');
		var genderPref= $('#dd5 span').attr('id');
		var vehicleType= $('#dd6 span').attr('id');
		var vendorId= $('#dd13 span').attr('id');
		if(cscId == undefined || time == undefined || vehicleType == undefined || genderPref == undefined  ||vendorId == undefined || postLocation == '' || date == ''){
			alert("Please Provide City, Location, Date, Time, Gender Preference and Vehicle Type");
			return false;
		}
		$("#postCscId").val(cscId);
		$("#postTime").val(time);
		$("#vendorId").val(vendorId);
		$("#postVehicleType").val(vehicleType);
		$('#genderPref').val(genderPref);
		$('#transactionId').val(date+time);

		$.jStorage.set('cscId', cscId);
	});
	
	
	

	$('.location-icon').click(function(){
		var mapUrl = $(this).attr('id');
		var win = window.open(mapUrl, "pop", "resizable,width=400,height=200,")
	});

	$('#tst-container').cycle({ 
		fx:     'slideY', 
		speed:  'slow', 
		timeout: '3000', 
		next:   '#next2', 
		prev:   '#prev2' 
	});


});