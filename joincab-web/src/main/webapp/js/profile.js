$(document).ready(function(){


	$('.userProfile').click(function(event) {
		$('#userContainer').removeClass('dis-none');
		$('#dataTableHeader').addClass('dis-none');
		$('#journeyContainer').addClass('dis-none');
		$('#testimonialContainer').addClass('dis-none');
	});

	$('.hopHistory').click(function(event) {
		$.get('./getHopHistory', function(response) {
		$('#dataContainer').html("");
		$('#userContainer').addClass('dis-none');
		$('#testimonialContainer').addClass('dis-none');
		$('#dataTableHeader').removeClass('dis-none');
		$('#journeyContainer').removeClass('dis-none');

		$.each(response, function(index, val) {
			var statusNum = val.hopStatus;
			var status;
			var actions;
			if(statusNum == 1){
				status = 'REQUESTED';
				actions = '<li class="actionstd"><a href="#" class="delete hopCancel" id='+val.postId+' title="Delete"></a></li></ul></div>'
			}else if(statusNum == 2){
				status = 'JOINED';
				actions = '<li class="actionstd"><a href="#" class="delete  hopCancel" id='+val.postId+' title="Delete"></a></li></ul></div>'
			}else if(statusNum == 4){
				status = 'CANCELLED';
				actions = '</ul></div>'
			}else {
				status = 'EXPIRED';
				actions = '</ul></div>';
			}
				$('#dataContainer').append('<div class="udetails-table"> <ul><li class="datetd">'+val.date+'</li> <li class="locationtd">'+val.hopLocation+'</li><li class="hopusertd"><span class='+val.postId+'>'+val.posterName+'</span></li> <li class="stautstd">'+status+'</li>'+actions);
			});
		
	});
});


	$('.postHistory').click(function(event) {
		$.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' }); 
		$.get('./getPostHistory', function(response) {
		$('#dataContainer').html("");
		$('#userContainer').addClass('dis-none');
		$('#testimonialContainer').addClass('dis-none');
		$('#dataTableHeader').removeClass('dis-none');
		$('#journeyContainer').removeClass('dis-none');
		$.each(response, function(index, val) {
			var statusNum = val.postStatus;
			var status;
			var actions;
			var hopperName;
			
			if(statusNum == 1){
				status = 'POSTED';
				hopperName = '--';
				actions = '<li class="actionstd"><a href="#" class="delete postCancel" id='+val.postId+' title ="Delete"></a></li></ul></div>'
			}else if(statusNum == 2){
				status = 'REQUEST RECEIVED';
				hopperName = val.hopperName;
				actions = '<li class="actionstd"><a href="#" class="deny hopDeny" id='+val.postId+' title="Deny"></a><a href="#" class="accept hopConfirm" id='+val.postId+' title="Accept"></a><a href="#" class="delete postCancel" id='+val.postId+' title="Delete"></a></li></ul></div>';
			}else if(statusNum == 3){
				status = 'CONFIRMED';
				hopperName = val.hopperName;
				actions = '<li class="actionstd"><a href="#" class="delete postCancel" id='+val.postId+'></a></li></ul></div>';
			}else{
				status = 'CANCELLED';
				actions = '</ul></div>';
			}
			
				$('#dataContainer').append('<div class="udetails-table"> <ul><li class="datetd">'+val.date+'</li> <li class="locationtd">'+val.postLocation+'</li><li class="hopusertd"><span class='+val.postId+'>'+hopperName+'</span></li> <li class="stautstd">'+status+'</li>'+actions);
		});
		
	});
});


	$('.testimonial').click(function(event) {
		$('#userContainer').addClass('dis-none');
		$('#dataTableHeader').addClass('dis-none');
		$('#journeyContainer').addClass('dis-none');
		$('#testimonialContainer').removeClass('dis-none');

	});

	$(document).on("click", ".hopCancel", function(e) {
		$.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' }); 
		var selectedId = $(this).attr('id');
		$.ajax({
			url: './cancelHop',
			type: 'POST',
			data: {postId: selectedId},
		})
		.done(function(response) {
			if(response.success){
				 showPopUp('success');
				location.reload();
				 popUpMessage(response.message);
			}else{
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
		
	});


	$(document).on("click", ".postCancel", function(e) {
		$.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' }); 
		var selectedId = $(this).attr('id');
		$.ajax({
			url: './cancelPost',
			type: 'POST',
			data: {postId: selectedId},
		})
		.done(function(response) {
			if(response.success){
				 showPopUp('success');
				 location.reload();
				 popUpMessage(response.message);
			}else{
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
		
	});

	$(document).on("click", ".hopConfirm", function(e) {
		$.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' }); 
		var selectedId = $(this).attr('id');
		$.ajax({
			url: './confirmHop',
			type: 'POST',
			data: {postId: selectedId},
		})
		.done(function(response) {
			if(response.success){
				 showPopUp('success');
				 location.reload();
				 popUpMessage(response.message);
			}else{
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
		
	});
	
	$(document).on("click", ".hopDeny", function(e) {
		$.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' }); 
        var selectedId = $(this).attr('id');
        $.ajax({
            url: './hopDeny',
            type: 'POST',
            data: {postId: selectedId},
        })
        .done(function() {
            console.log("success");if(response.success){
				 showPopUp('success');
				 location.reload();
				 popUpMessage(response.message);
			}else{
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
        
    });

	$('.testimonailSubmit').click(function(event) {
		var testimonial = $("#subText").val();
		$.ajax({
			url: './submitTestimonial',
			type: 'POST',
			data: {text: testimonial},
		})
		.done(function() {
			$('#subText').val('');
            console.log("success");if(response.success){
				 showPopUp('success');
				 location.reload();
				 popUpMessage(response.message);
			}else{
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
	});

	$('#clr').click(function(event) {
		$('#subText').val('');
	});

});
