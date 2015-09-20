<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>  
<jsp:include page="scripts.jsp"></jsp:include>
<jsp:include page="model.jsp"></jsp:include>
<!doctype html>
<html lang="en"  manifest="hytt.appcache">
<head>
	<meta charset="UTF-8">
	<title>The Hytt</title>
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
		    /*
			document.getElementById('hopLocation').onblur = function () {
		        google.maps.event.trigger( input, 'keydown', {
		        	keyCode:13
		        });
		        google.maps.event.trigger(input, 'focus')
		        google.maps.event.trigger(input, 'keydown', {
		            keyCode: 13
		        });
		        google.maps.event.trigger(input, 'places_changed');
		    };
		    */
		    // Hop Location ends here
		    // Post Location Starts here
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
		    //post location ends here
		}
		google.maps.event.addDomListener(window, 'load', initialize);
		</script>
		<link rel="icon" href="images/favicon.ico" type="images/ico" sizes="16x16">
</head>
<body>
	
	<!-- hdr starts here -->
	
	<header class="home-hdr">

		<div class="width-container">
			
			<div class="logo fleft">
				<a href="./home"></a>
			</div>

                <div id="dd" class="wrapper-dropdown-3 fleft" tabindex="1">
                    <span id="BLR-KA-IN">Bangalore</span>
                    <ul class="dropdown">
                        <li id="BLR-KA-IN"><a href="#">Bangalore</a></li>
                        <!-- <li id="HYD-TL-IN"><a href="#">Hydrabad</a></li>
                        <li id="MUM-MH-IN"><a href="#">Mumbai</a></li> -->
                    </ul>
				</div>

			<!-- navigation starts here -->

			<div class="fright">
				
				<button class="instbook" onclick="window.location='./instantBook'">
					Instant Book
				</button>



				<nav class="home-navigation fleft">
					

					<ul>
						<li><a href="#home" id="homeLink" class="active">Home</a></li>
						<li><a href="#about"><span></span>About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>



				</nav>

				<nav class="home-navigation registration-nav fright">

					<ul class="singInUpLink" id="anonymous">
						
						<li><a href="#" class="login">Sign In</a></li>
						
					</ul>

					<!-- after login here -->

					<div class="fright uname-log dis-none" id="authenticated">
						
						<div class="fleft uimg">
							<img src="images/grl.jpg" height="35" width="35" id="userImage">
						</div>
						<a href="#" class="uname-link" >Welcome,<br/><span id="userName"></span></a>
						
						<!-- options starts  here -->
						
						<div class="options dis-none" id="userNav">
							<ul>
								<li><a href="./profile">My Profile</a></li>
								<li><a href="./logout" id="logout">Log out</a></li>
							</ul>
						</div>

						<!-- options ends here -->

					</div>

					<!-- after login ends here -->
					
				</nav>


			</div>


			<!-- navigation ends here -->

		</div>

	</header>
	
	<!-- hdr ends here -->

	<main>
		
        <!-- content starts here -->
        
        <div class="content-hme" id="home">
           
           <div class="searchboxContainer">
            
            <div class="alert-home">
            	
            	Please provide City, Location, Date, Time and Vehicle Type

            </div>


            <h2><span>SHARE</span> YOUR JOURNEY AND <span>FARE</span></h2>
                
               <a href="https://www.youtube.com/watch?v=9B_-epnbj-8" class="videolink html5lightbox">Check out how it works?<span></span></a>
            
            
            <div class="contentsrch-box">
                
                <div class="tab-cab">
                    
                    <ul>
                        <li href="#hop"><a >find my ride</a></li>
                        <li  href="#post"><a>share my cab</a></li>
                    </ul>
                    
                </div>       
                    
                <!-- Hop a Cab starts here --> 
                    
              <div class="tab-1" id="hop">
					<form:form action="getCabsForHop.htm" method="post" id="hopForm" modelAttribute="hopSearchRequestDto">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<form:input id="hopLocation" class="search-box" type="text" path="hopLocation" placeholder="Start Typing your location"/>
						<form:input id="hopFormattedAddress" path="hopFormattedAddress" type="hidden"/>
						<form:input id="hopLatitude" path="hopLatitude" type="hidden"/>
						<form:input id="hopLongitude" path="hopLongitude" type="hidden"/>
						<form:input id="cscId" path="cscId" type="hidden"/>
						<form:input id="hopDate" class="search-box date fleft" path="hopDate" placeholder="dd/mm/yyyy"/>
						<form:input id="hopTime" path="hopTime" type="hidden"/>
						<form:input id="vehicleType" path="vehicleType" type="hidden"/>
						<div id="dd2"
							class="wrapper-dropdown-3 wrapper-dropdown-time fleft dd2"
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
						<div id="dd3"
							class="wrapper-dropdown-3 wrapper-dropdown-time vehicletype fleft dd6"
							tabindex="1">
							<span>Vehicle Type</span>
							<ul class="dropdown dropdown-time">
								<li id="0"><a href="#"><i class="icon-envelope icon-large"></i>Any</a></li>
								<li id="1"><a href="#"><i class="icon-envelope icon-large"></i>AC Sedan</a></li>
								<li id="2"><a href="#"><i class="icon-truck icon-large"></i>Non AC Sedan</a></li>
								<li id="3"><a href="#"><i class="icon-plane icon-large"></i>AC Mini</a></li>
								<li id="4"><a href="#"><i class="icon-plane icon-large"></i>Non AC Mini</a></li>

							</ul>
						</div>

						<button class="tab-btn" id="hopCab" type="submit">Get
							Cabs</button>
					</form:form>

				</div>
                <!-- Hop a Cab ends here --> 
                
                <!-- Post a cab starts here -->
                
                 <div id="post" class="tab-1">
                     <form:form action="postCab.htm" method="post" id="postForm" modelAttribute="postRequestDto">
                   		    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <form:input id="postLocation" class="search-box" type="text" path="postLocation" placeholder="Start Typing your location"/>
                            <form:input id="postFormattedAddress" type="hidden" path="postFormattedAddress" />
                            <form:input id="postLatitude" type="hidden" path="postLatitude" />
                            <form:input id="postLongitude" type="hidden" path="postLongitude" />
                            <form:input id="transactionId" path="transactionId" type="hidden"/>
                            <form:input id="postDate" class="search-box date fleft" path="postDate" placeholder="dd/mm/yyyy"/>
                            <form:input id="postCscId" path="cscId" type="hidden" />
                            <form:input id="postTime" path="postTime" type="hidden" />
							<form:input id="postVehicleType" path="postVehicleType" type="hidden" />
							<form:input id="genderPref" path="genderPref" type="hidden" />
                            <div id="dd4" class="wrapper-dropdown-3 wrapper-dropdown-time fleft dd2" tabindex="1">
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
                            <div class="clearall"></div>
                            <div id="dd5" class="wrapper-dropdown-3 wrapper-dropdown-time dd-gendersmall dd-small fleft" tabindex="1">
                                <span>Gender Preference</span>
                                    <ul class="dropdown dropdown-time">
                                    	<li id="0"><a href="#">Any</a></li>
                                        <li id="1"><a href="#">Male</a></li>
                                        <li id="2"><a href="#">Female</a></li>
                                    </ul>
                            </div>
                            
                            <div id="dd6" class="wrapper-dropdown-3 wrapper-dropdown-time  fleft" tabindex="1">
                                <span>Vehicle Type</span>
                                    <ul class="dropdown dropdown-time">
                                    	<li id="0"><a href="#">Any</a></li>
										<li id="1"><a href="#">AC Sedan</a></li>
										<li id="2"><a href="#">Non AC Sedan</a></li>
										<li id="3"><a href="#">AC Mini</a></li>
										<li id="4"><a href="#">Non AC Mini</a></li>
                                    </ul>
                            </div>
                            
                            <button class="tab-btn" id="postCab" type="submit">Post Cab</button>
                </form:form>
                </div>
                
                <!-- Post a cab ends here -->
                
                  <div class="clearall"></div>
                
            </div>
            
            <div class="clearall"></div>
            
            </div>
        </div>
        
        <!-- content ends here -->

        <div class="clearall"></div>

		
		<!-- Who we are starts here -->
		
		<section class="whowe" id="about">
		    
		    <h2>About Us</h2>
		    
		   <div class="width-container width-containerpx">
       <h3>Who we are?</h3>
       <p>
           
           5 of us make theHytt. Each one best in the class with what they do. 
We brougt the best of diverse expertise to bring theHytt where it is now.
       </p>
       
       
       <div class="whowecontainer">
           
           <div class="fleft whoimg-1">
               
           </div>
           
           <div class="fleft whowetxt">
               
               The Techies. Night Crawlers. Lives on Coffee.
               
           </div>
           
           <div class="clearall"></div>
           
       </div>
       
        <div class="whowecontainer">
           
           <div class="fleft whoimg-1 who-img2">
               
           </div>
           
           <div class="fleft whowetxt">
               
               UI Guys. The Dreamers. The Creatives. Night Crawlers.
               
           </div>
           
           <div class="clearall"></div>
           
       </div>
       
       <div class="whowecontainer">
           
           <div class="fleft whoimg-1 who-img3">
               
           </div>
           
           <div class="fleft whowetxt">
               
               The Management Guys. The requirement givers. Techies hate them.
               
           </div>
           
           
           <div class="clearall"></div>
           
       </div>
       
       
       <div class="whowecontainer">
           
           <div class="fleft whoimg-1 who-img3">
               
               
               
           </div>
           
           <div class="fleft whowetxt">
               
               The Operations Guys. The backbone. We all love them.
               
           </div>
           
           
           <div class="clearall"></div>
           
           
           
       </div>
       
       <div class="clearall"></div>
       <p>We are starting with one and will be adding more features as you give us more confidence :)</p>
       
       
       <h3>What we do?</h3>
       <br>
<p>	           
We Share..!! 

Well, thats all we do. We help you share Your journey and fare..!!
</p>

       <div class="whattxtcontainer">

           <h2>How you share?</h2>
           <p>

The flow is simple. You visit our website, search your location and pin your cab at the specified date and time. Someone else visit theHytt.com, search for location with date and time. He finds your cab a perfect match and click "join". You get a notification on your registered mobile for confirmation. Once you confirm, We trigger messages on both registered mobile numbers for each other details. That's all, you guys can now talk to each other for more details.
</p>
            <h2>Book a Cab?</h2>
           <p>

If you do not find any relevent post, you can book a cab through us and your cab will be automatically posted for share on theHytt.com. Anyone else williing to join can share a ride with you.
</p>
           
            <h2>What about security?</h2>
           <p>

Women can choose to hop with only Women. We have an option of mapping hop requests by women with cabs posted by women only. Also, we verify the mobile numbers while registration before they can post a cab or request for a hop. Thus we give you a share with whom you can discuss each details before hopping.
</p>
       
       </div>
       
       <p>	           
That's it.  Remember, The more you share, the more you have..!!
<br>
Happy Hopping..!! :)
</p>  
       
   </div>
		    
		    <div class="clearall"></div>
		    
		    
		</section>
		
		
		
		<!-- who we are ends here ->
		
		
		<!-- contact us starts here -->
		
		<section class="whowe contact" id="contact">
		    
		    <h2>Contact Us</h2>
		    
		    <div class="width-container width-containerpx">
		        
		        <!-- Maps starts here -->
		        
		       <!-- Maps starts here -->

       

        <div class="fleft maps-container" id="contactMap">

				<script>
					var markerLatLng = new google.maps.LatLng(12.9236557,77.6366322);
					var mapProp = {
						center : markerLatLng,
						draggable: false,
						zoom : 14
					};

					var map = new google.maps.Map(document.getElementById("contactMap"), mapProp);
					var marker;
					marker = new google.maps.Marker({
						position : markerLatLng,
						map : map,
						title : "The Hytt"
					});
				</script>

			</div>
		        
		        <!-- Maps ends here -->
		        <div class="fleft contactusform">
		            
		            <h3>Address:-</h3>
                    <p>#295, 1st floor,1st main<br/>1st block, <br/> Kormangla,<br/>Bangalore 560034</p>
		             <div class="phone-num">9880330038</div>
		             <a href="mailto:info@thehytt.com" class="mail">info@thehytt.com</a>
		        </div>
		        
		        <div class="clearall"></div>
		    </div>
		    
		    
		</section>
		
		
		
		<!-- contact us ends here -->
		
		

		<!-- Testimonial starts here -->

		<section class="testimonials">
			<h2>Client's Testimonials</h2>
			<div class="width-container width-containerpx">
				
				
				<div class="nav">
					<a id="prev2" class="prev" href="#"></a>
					 <a id="next2" class="prev next" href="#"></a>
				</div>
				<div id="tst-container">
				<!-- Testimonial one starts here -->
				<c:forEach var="testimonial" items="${testimonialList}">
				<div class="tst-one">
					
					<div class="fleft test-img-1">
						
						<img src="${testimonial.imageUrl}" width="100px" height="100px" alt="">
						<div class="testimonialName"><span>${testimonial.firstName} ${testimonial.lastName}</span></div>
					</div>

					<div class="fleft test-txt">
						
						<div class="fleft ex-ico"></div>

						<p class="fleft">
							${testimonial.message}
						</p>

					</div>


				</div>
				</c:forEach>
				<!-- Testimonial one ends here -->
				
				</div>

			</div>
			
			<div class="clearall"></div>
		
		</section>

		<!-- Testimonial ends here -->

		<div class="clearall"></div>

	    <!-- advertisment starts here -->

		<section class="advertisment-container">
			
			<div class="width-container width-containerpx">
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
	</main>
	
	<footer>
		<div class="width-container">
			<div class="fleft copy-rgt">
				&copy; The Hytt india pvt.ltd
			</div>
			
			<div class="fright">
			    <div class="social-media">
		
		<ul>
			<li><a href="#" target="_blank" class="facebook-ico"></a></li>
			<li><a href="#" target="_blank" class="facebook-ico twitter-ico"></a></li>
			<li><a href="#" target="_blank" class="facebook-ico linkedin-ico"></a></li>
		</ul>


	</div>
			</div>
		</div>
	</footer>
</body>
</html>