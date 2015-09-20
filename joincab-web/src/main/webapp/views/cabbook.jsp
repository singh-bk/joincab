<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="model.jsp"></jsp:include>
<jsp:include page="scripts.jsp"></jsp:include>
<!doctype html>
<html lang="en" manifest="hytt.appcache">
<head>
	<meta charset="UTF-8">
	<title>The Hytt-cab booking</title>
	<script type="text/javascript">
	 	var searchBox,geocoder;
	    var cacheHopLocation, cachePostLocation;
	    var input;
		function initialize() {
			var defaultBounds = new google.maps.LatLngBounds(//bounds for Bengaluru
				    new google.maps.LatLng(12.7342888,77.3791981),
				    new google.maps.LatLng(13.173706,77.8826809)
				);
			//Hop Location starts here
			input = (document.getElementById('hopLocation'));
			searchBox = new google.maps.places.SearchBox((input));
			searchBox.setBounds(defaultBounds);
			cacheHopLocation = document.getElementById('hopLocation').value;
			google.maps.event.addListener(searchBox, 'places_changed', function() {
				document.getElementById('hopFormattedAddress').value = "";
				cacheHopLocation = document.getElementById('hopLocation').value;
				var places = searchBox.getPlaces();
				if (places.length == 0) {
					alert("PLEASE PROVIDE CORRECT LOCATION.");
					document.getElementById('hopFormattedAddress').value = "";
				} else {
					var place = places[0];
					document.getElementById('hopFormattedAddress').value = place.formatted_address;
					var hopLat = place.geometry.location.lat();
					var hopLng = place.geometry.location.lng();
					document.getElementById('hopLatitude').value = hopLat;
					document.getElementById('hopLongitude').value = hopLng;
					var hopLoc = new google.maps.LatLng(hopLat, hopLng);
					var locWithinRange = defaultBounds.contains(hopLoc);
					if(!locWithinRange){
						alert("Location not within Range");
						document.getElementById('hopLocation').value = '';
						return false;
					}
				}
			});
		    // Hop Location ends here
		    var postInput = (document.getElementById('postLocation'));
			var postSearchBox = new google.maps.places.SearchBox((postInput));
			postSearchBox.setBounds(defaultBounds);
			google.maps.event.addListener(postSearchBox, 'places_changed', function() {
				document.getElementById('postFormattedAddress').value = "";
				cachePostLocation = document.getElementById('postLocation').value;
				var places = postSearchBox.getPlaces();
				if (places.length == 0) {
					alert("PLEASE PROVIDE CORRECT LOCATION.");
					document.getElementById('postFormattedAddress').value = "";
				} else {
					var place = places[0];
					document.getElementById('postFormattedAddress').value = place.formatted_address;
					var hopLat = place.geometry.location.lat();
					var hopLng = place.geometry.location.lng();
					document.getElementById('postLatitude').value = hopLat;
					document.getElementById('postLongitude').value = hopLng;
					var hopLoc = new google.maps.LatLng(hopLat, hopLng);
					var locWithinRange = defaultBounds.contains(hopLoc);
					if(!locWithinRange){
						alert("Location not within Range");
						document.getElementById('postLocation').value = '';
						return false;
					}
				}
			});
		}
		google.maps.event.addDomListener(window, 'load', initialize);
		</script>



</head>
<body>
	<!-- subheader starts here -->
	
	
			
	<section class="subheader">

		<div class="ref-search-tab">
			
			<div class="ref-warning-txt"><span>No posts found. You can refine your search or you can book a cab and post for sharing.</span></div>
			<a href="#" class="refSearch tab-btn fright">Refine search</a>

		</div>




	    <div class="ref-search-box dis-none">

	    <div class="width-container">
	        <div class="fleft">
	            <form:form action="getCabsForHop.htm" method="post" id="hopForm" modelAttribute="hopSearchRequestDto">
	            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	            <form:input id="hopLocation" path="hopLocation" class="fleft subhdr-inputloc" type="text" placeholder="Start typing your location"/>
	            <form:input type="hidden" path="cscId" id="cscId" />
				<form:input type="hidden" path="hopFormattedAddress" id="hopFormattedAddress" />
				<form:input type="hidden" path="hopLatitude" id="hopLatitude" />
				<form:input type="hidden" path="hopLongitude" id="hopLongitude" />
	             <form:input id="hopDate" path="hopDate" class="fleft subhdr-inputloc subhdr-inputdate date" type="text" placeholder="dd/mm/yyyy"/>
	            <form:input id="hopTime" path="hopTime" type="hidden"/>
				<form:input id="vehicleType" path="vehicleType" type="hidden"/>
	           	<div id="dd2"
							class="wrapper-dropdown-3 select-veh-trans fleft dd2"
							tabindex="1">
							<span>Select Time</span>
							<ul class="dropdown dropdown-time">
									<li id="00:00"><a href="#">00:00am</a></li>
									<li id="00:30"><a href="#">00:30am</a></li>
									<li id="01:00"><a href="#">01:00am</a></li>
									<li id="01:30"><a href="#">01:30am</a></li>
									<li id="02:00"><a href="#">02:00am</a></li>
									<li id="02:30"><a href="#">02:30am</a></li>
									<li id="03:00"><a href="#">03:00am</a></li>
									<li id="03:30"><a href="#">03:30am</a></li>
									<li id="04:00"><a href="#">04:00am</a></li>
									<li id="04:30"><a href="#">04:30am</a></li>
									<li id="05:00"><a href="#">05:00am</a></li>
									<li id="05:30"><a href="#">05:30am</a></li>
									<li id="06:00"><a href="#">06:00am</a></li>
									<li id="06:30"><a href="#">06:30am</a></li>
									<li id="07:00"><a href="#">07:00am</a></li>
									<li id="07:30"><a href="#">07:30am</a></li>
									<li id="08:00"><a href="#">08:00am</a></li>
									<li id="08:30"><a href="#">08:30am</a></li>
									<li id="09:00"><a href="#">09:00am</a></li>
									<li id="09:30"><a href="#">09:30am</a></li>
									<li id="10:00"><a href="#">10:00am</a></li>
									<li id="10:30"><a href="#">10:30am</a></li>
									<li id="11:00"><a href="#">11:00am</a></li>
									<li id="11:30"><a href="#">11:30am</a></li>
									<li id="12:00"><a href="#">12:00am</a></li>
									<li id="12:30"><a href="#">12:30pm</a></li>
									<li id="13:00"><a href="#">01:00pm</a></li>
									<li id="13:30"><a href="#">01:30pm</a></li>
									<li id="14:00"><a href="#">02:00pm</a></li>
									<li id="14:30"><a href="#">02:30pm</a></li>
									<li id="15:00"><a href="#">03:00pm</a></li>
									<li id="15:30"><a href="#">03:30pm</a></li>
									<li id="16:00"><a href="#">04:00pm</a></li>
									<li id="16:30"><a href="#">04:30pm</a></li>
									<li id="17:00"><a href="#">05:00pm</a></li>
									<li id="17:30"><a href="#">05:30pm</a></li>
									<li id="18:00"><a href="#">06:00pm</a></li>
									<li id="18:30"><a href="#">06:30pm</a></li>
									<li id="19:00"><a href="#">07:00pm</a></li>
									<li id="19:30"><a href="#">07:30pm</a></li>
									<li id="20:00"><a href="#">08:00pm</a></li>
									<li id="20:30"><a href="#">08:30pm</a></li>
									<li id="21:00"><a href="#">09:00pm</a></li>
									<li id="21:30"><a href="#">09:30pm</a></li>
									<li id="22:00"><a href="#">10:00pm</a></li>
									<li id="22:30"><a href="#">10:30pm</a></li>
									<li id="23:00"><a href="#">11:00pm</a></li>
									<li id="23:30"><a href="#">11:30pm</a></li>
							</ul>
						</div>
                 <div id="dd3" class="wrapper-dropdown-3 dd7 select-veh-trans fleft" tabindex="1">
                    <span>Select Vehicle</span>
                        <ul class="dropdown dropdown-time">
								<li id="0"><a href="#">Any</a></li>
								<li id="1"><a href="#">AC Sedan</a></li>
								<li id="2"><a href="#">Non AC Sedan</a></li>
								<li id="3"><a href="#">AC Mini</a></li>
								<li id="4"><a href="#">Non AC Mini</a></li>

							</ul>
				</div>
             
             
	             <button class="golink" id="hopCab" type="submit">Go</button>
	            <!-- <a href="#" class="golink" id="hopCab">Go</a> -->
	          	<a href="#" class="subhdr-close-btn"></a>
	            </form:form>
	        </div>
	    </div>
	    
		</div>


	</section>

	<main>
	<!-- content starts here -->

		
			
			<div class="video-tab-container">

			<div class="width-container width-containerpx">
				<div class="clearall"></div>
               
               <!-- Booking form starts here -->
                
                <div class="fleft bookingform">
                    
                    <div class="bookcabheading">
                        
                        <h2>Book Your Cab</h2>
                        
                    </div>
                <form:form action="bookCab.htm" method="post" id="bookForm" modelAttribute="postRequestDto">
                   	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<form:input id="postFormattedAddress" path="postFormattedAddress" type="hidden"/>
					<form:input id="postCscId" path="cscId" type="hidden"/>
					<form:input id="postTime" path="postTime" type="hidden"/>
					<form:input id="postVehicleType" path="postVehicleType" type="hidden"/>
					<form:input id="vendorId" path="vendorId" type="hidden"/>
					<form:input id="postLatitude" path="postLatitude" type="hidden"/>
					<form:input id="postLongitude" path="postLongitude" type="hidden"/>
					<form:input id="genderPref" path="genderPref" type="hidden"/>
					<form:input id="transactionId" path="transactionId" type="hidden"/>
					<form:input id="share" path="share" type="hidden"/>

                    <div class="bookcab-content">
                        
                        <div class="rows">
                            <form:input id="postLocation" class="booklocation" type="text" path="postLocation" placeholder="Start Typing your location"/>
                        </div>
                        
                        <div class="rows rows-2">
                            
                            <form:input id="postDate" class="booklocation date bookdate cabbookdate" path="postDate" placeholder="dd/mm/yyyy"/>
                            
                             <div id="dd4" class="wrapper-dropdown-3 select-timedd-cab fleft" tabindex="1">
                                <span>Select Time</span>
                                <ul class="dropdown">
												<li id="00:00"><a href="#">00:00am</a></li>
												<li id="00:30"><a href="#">00:30am</a></li>
												<li id="01:00"><a href="#">01:00am</a></li>
												<li id="01:30"><a href="#">01:30am</a></li>
												<li id="02:00"><a href="#">02:00am</a></li>
												<li id="02:30"><a href="#">02:30am</a></li>
												<li id="03:00"><a href="#">03:00am</a></li>
												<li id="03:30"><a href="#">03:30am</a></li>
												<li id="04:00"><a href="#">04:00am</a></li>
												<li id="04:30"><a href="#">04:30am</a></li>
												<li id="05:00"><a href="#">05:00am</a></li>
												<li id="05:30"><a href="#">05:30am</a></li>
												<li id="06:00"><a href="#">06:00am</a></li>
												<li id="06:30"><a href="#">06:30am</a></li>
												<li id="07:00"><a href="#">07:00am</a></li>
												<li id="07:30"><a href="#">07:30am</a></li>
												<li id="08:00"><a href="#">08:00am</a></li>
												<li id="08:30"><a href="#">08:30am</a></li>
												<li id="09:00"><a href="#">09:00am</a></li>
												<li id="09:30"><a href="#">09:30am</a></li>
												<li id="10:00"><a href="#">10:00am</a></li>
												<li id="10:30"><a href="#">10:30am</a></li>
												<li id="11:00"><a href="#">11:00am</a></li>
												<li id="11:30"><a href="#">11:30am</a></li>
												<li id="12:00"><a href="#">12:00am</a></li>
												<li id="12:30"><a href="#">12:30pm</a></li>
												<li id="13:00"><a href="#">01:00pm</a></li>
												<li id="13:30"><a href="#">01:30pm</a></li>
												<li id="14:00"><a href="#">02:00pm</a></li>
												<li id="14:30"><a href="#">02:30pm</a></li>
												<li id="15:00"><a href="#">03:00pm</a></li>
												<li id="15:30"><a href="#">03:30pm</a></li>
												<li id="16:00"><a href="#">04:00pm</a></li>
												<li id="16:30"><a href="#">04:30pm</a></li>
												<li id="17:00"><a href="#">05:00pm</a></li>
												<li id="17:30"><a href="#">05:30pm</a></li>
												<li id="18:00"><a href="#">06:00pm</a></li>
												<li id="18:30"><a href="#">06:30pm</a></li>
												<li id="19:00"><a href="#">07:00pm</a></li>
												<li id="19:30"><a href="#">07:30pm</a></li>
												<li id="20:00"><a href="#">08:00pm</a></li>
												<li id="20:30"><a href="#">08:30pm</a></li>
												<li id="21:00"><a href="#">09:00pm</a></li>
												<li id="21:30"><a href="#">09:30pm</a></li>
												<li id="22:00"><a href="#">10:00pm</a></li>
												<li id="22:30"><a href="#">10:30pm</a></li>
												<li id="23:00"><a href="#">11:00pm</a></li>
												<li id="23:30"><a href="#">11:30pm</a></li>
                                            </ul>
				            </div>
                      </div>
                        <div class="clearall"></div>
                        
                        <div class="rows rows-2">
                            
                             <div id="dd5" class="wrapper-dropdown-3 dd12 select-timedd-cab selectgender-cab fleft" tabindex="1">
                                <span>Select Gender</span>
                                <ul class="dropdown dropdown-time">
                                    	<li id="0"><a href="#">Any</a></li>
                                        <li id="1"><a href="#">Male</a></li>
                                        <li id="2"><a href="#">Female</a></li>
                                </ul>
				            </div>
                            
                            <div class="clearall"></div>
                            
                            
                        </div>
                        
                        <div class="rows rows-2">
                            
                             <div id="dd13" class="wrapper-dropdown-3 dd13 select-timedd-cab selectgender-cab fleft" tabindex="1">
                                <span>Select Vendor</span>
                                <ul class="dropdown">
                                    <li id="1"><a href="#">Utaxi</a></li>
                                 </ul>
				            </div>
                            
                            <div class="clearall"></div>
                            
                            
                        </div>
                        
                          <div class="rows rows-2">
                            
                             <div id="dd6" class="wrapper-dropdown-3 select-timedd-cab selectgender-cab fleft" tabindex="1">
                                <span>Select Vehicle Type</span>
                                <ul class="dropdown dropdown-time">
                                		<li id="0"><a href="#">Any</a></li>
										<li id="1"><a href="#">AC Sedan</a></li>
										<li id="2"><a href="#">Non AC Sedan</a></li>
										<li id="3"><a href="#">AC Mini</a></li>
										<li id="4"><a href="#">Non AC Mini</a></li>
                                </ul>
				            </div>
                            
                            <div class="clearall"></div>
                            
                           <div class="shareChk">
							<input type="checkbox" checked="checked" id="isShared"> <label for="isShared">Post my cab for share</label>
							</div>
						 <div class="clearall"></div>
                        </div>
                         
                        <div class="btm-bottombook">
                            
                            <button id="bookCab" class="black-btn" type="submit">Book a Cab</button>
                            
                            
                        </div>
                        
                    </div>
                    </form:form>
                    
                </div>
				
                 <!-- Booking form ends here -->
                 
                 <!-- Content display starts here -->
                 
                 <div class="fright cabcontent">
                     
                     <div class="cabcontheading">
                         
                         <div class="logo fleft">
                             <img src="images/logo.png" "width:148px"/>
                         </div>
                         
                         <div class="company-name fleft">
                             <h3>The Hytt, Bangalore</h3>
                         </div>
                         
                         <div class="clearall"></div>
                         
                         <div class="cabcontrate">
                             
                             <label>Approximate Fare (in Rs.): </label><span class="rate"></span>
                             
                             <div class="clearall"></div>
                             
                             <div class="raterow">
                             
                             <label>Approximate Distance (in KM): </label><span class="distance time"></span>
                             </div>
                             
                             <script type="text/javascript">
								showFare(${postRequestDto.distanceFromDest},${postRequestDto.postVehicleType});
							</script>
                            <div class="raterow">
                                 <br/><br/><b>Terms & Conditions</b><br/><br/>
                                 <p>
                                 	 
                                     **Fares are approximate and may vary on the route taken<br>
                                     **Any parking charges as applicable<br>
                                     **Any toll charges as applicable<br>
                                     **No waiting charges for first 10 mins<br>
                                     **After 10 mins waiting charges will be charged at Rs.2/min<br>
                                 </p>
                                 
                                 
                             </div>
                             
                         </div>
                         
                         
                         
                         
                     </div>
                     
                     
                     
                 </div>
                 
                 <!-- Content display ends here -->
				
			</div>
			<div class="clearall"></div>
		</div>
			
			
        <!-- advertisment starts here -->

		<section class="advertisment-container">
			
			<div class="width-container  width-containerpx">
				<div class="ads">

				<ul>
					<li>
						<a href="#">
						<img src="images/ad-small-1.jpg">
						</a>
					</li>
					<li>
						<a href="#">
						<img src="images/ad-small-2.jpg">
						</a>
					</li>
					<li>
						<a href="#">
						<img src="images/ad-small-3.jpg">
						</a>
					</li>
					<li>
						<a href="#">
						<img src="images/ad-small-4.jpg">
						</a>
					</li>
					<li>
						<a href="#">
						<img src="images/ad-small-5.jpg">
						</a>
					</li>


					
				</ul>

				</div>


			</div>



		</section>

		<!-- advertisment ends here -->

		<!-- content ends here -->
		
		<div class="clearall"></div>
	
	</main>
	
	<footer class="abs-footer">
		<div class="width-container">
			<div class="fleft copy-rgt">&copy; The Hytt india pvt.ltd</div>
			<div class="fright">
				<div class="social-media">

					<ul>
						<li><a href="#" target="_blank" class="facebook-ico"></a></li>
						<li><a href="#" target="_blank"
							class="facebook-ico twitter-ico"></a></li>
						<li><a href="#" target="_blank"
							class="facebook-ico linkedin-ico"></a></li>
					</ul>


				</div>
			</div>
		</div>

	</footer>


	<!-- social media icons -->


	<div class="social-media">

		<ul>

			<li><a href="#" target="_blank" class="facebook-ico"></a></li>

			<li><a href="#" target="_blank" class="facebook-ico twitter-ico"></a></li>

			<li><a href="#" target="_blank"
				class="facebook-ico linkedin-ico"></a></li>

		</ul>



	</div>



	<!-- social media icons ends here -->

    
</body>
</html>