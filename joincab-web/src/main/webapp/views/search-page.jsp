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

<title>Sharegini search page</title>

<script>

var directionsDisplay;
var cacheHopLocation;
var directionsService = new google.maps.DirectionsService();
var neighbors_latitude = [];
var neighbors_longitude = [];
var neighbors_address = [];
var map;
var markers = [];
var final_src_latLng;
var final_src_lat;
var final_src_lng;
var autocomplete;
var currentMarkerIndex;

function loadNeighbors(lat, lng, address){
	neighbors_latitude.push(lat);
	neighbors_longitude.push(lng);
	neighbors_address.push(address);
}

function initialize() {
		var defaultBounds = new google.maps.LatLngBounds(//bounds for Bengaluru
			    new google.maps.LatLng(12.7342888,77.3791981),
			    new google.maps.LatLng(13.173706,77.8826809)
			);
		//Hop Location starts here
		var input = (document.getElementById('hopLocation'));
		var searchBox = new google.maps.places.SearchBox((input));
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
	  	directionsDisplay = new google.maps.DirectionsRenderer();
  		final_src_lat = ${srcLocation.latitude};
		final_src_lng = ${srcLocation.longitude};
  		final_src_latLng = new google.maps.LatLng(final_src_lat,final_src_lng);
		var mapProp = {
		    center:final_src_latLng,
	    	zoom:14
		};
  		map=new google.maps.Map(document.getElementById("googleMap"), mapProp);
  	  	// Create the DIV to hold the control and
  	 	// call the HomeControl() constructor passing
  	 	// in this DIV.
  	 	var homeControlDiv = document.createElement('div');
  	  	var homeControl = new HomeControl(homeControlDiv, map);

  	  	homeControlDiv.index = 1;
  	  	map.controls[google.maps.ControlPosition.TOP_RIGHT].push(homeControlDiv);
  		//Control - END 
  	  	loadSrcMarker(final_src_latLng);
	  	directionsDisplay.setMap(map);
	  	//Supress default A and B markes provided by google
	  	directionsDisplay.setOptions( { suppressMarkers: true } );
	  	var circle = new google.maps.Circle({
			  center:final_src_latLng,
			  radius:2500,
			  strokeColor:"#0000FF",
			  strokeOpacity:0.1,
			  strokeWeight:1,
			  fillColor:"05FF00FF",
			  fillOpacity:0.4
  	    });
  		circle.setMap(map);
}

function loadSrcMarker(markerLatLng){
	  var marker;
	  marker = new google.maps.Marker({
		    position: markerLatLng,
		    map: map,
		    title:"This is your location"
		});
      	for(var i=0;i<neighbors_latitude.length;i++){
      		loadDestMarker(neighbors_latitude[i], neighbors_longitude[i], neighbors_address[i]);
  		}
}//END loadSrcMarker

function loadDestMarker(lat, lng, address){
  	var myLatLng = new google.maps.LatLng(lat, lng);
  	var image = "images/taxi.png";
  	var marker;
  	marker = new google.maps.Marker({
	    position: myLatLng,
	    map: map,
	    title:address
	});
  	marker.setIcon(image);
	markers.push(marker);
  	google.maps.event.addListener(marker, 'click', function(){
	  	var request = {
		    origin:final_src_latLng,
		    destination: myLatLng,
		    travelMode: google.maps.TravelMode.DRIVING,
		    durationInTraffic: true
	  	};
	  	directionsService.route(request, function(result, status) {
	    	if (status == google.maps.DirectionsStatus.OK) {
	      		directionsDisplay.setDirections(result);
	    	}
	  	});
  	});
}//End loadDestMarker


function animateMarker(index){
	markers[index].setAnimation(google.maps.Animation.BOUNCE);
}

function removeAnimationMarker(index){
	markers[index].setAnimation(null);
}

function displayDirection(index){
	var myLatLng = new google.maps.LatLng(neighbors_latitude[index], neighbors_longitude[index]);
  	var request = {
	    origin:final_src_latLng,
	    destination: myLatLng,
	    travelMode: google.maps.TravelMode.DRIVING,
	    durationInTraffic: true
  	};
  	directionsService.route(request, function(result, status) {
	    if (status == google.maps.DirectionsStatus.OK) {
	      directionsDisplay.setDirections(result);
	    }
  	});

}//END displayDirection

function removeDisplayDirections(){
	directionsDisplay.setDirections({routes: []});
}

function removeMarker(index){
	markers[index].setMap(null);
}
/**
 * The HomeControl adds a control to the map that simply
 * returns the user to Chicago. This constructor takes
 * the control DIV as an argument.
 * @constructor
 */
function HomeControl(controlDiv, map) {

  // Set CSS styles for the DIV containing the control
  // Setting padding to 5 px will offset the control
  // from the edge of the map
  controlDiv.style.padding = '5px';

  // Set CSS for the control border
  var controlUI = document.createElement('div');
  controlUI.style.backgroundColor = 'white';
  controlUI.style.borderStyle = 'solid';
  controlUI.style.borderWidth = '2px';
  controlUI.style.cursor = 'pointer';
  controlUI.style.textAlign = 'center';
  controlUI.title = 'Click to set the map to Home';
  controlDiv.appendChild(controlUI);

  // Set CSS for the control interior
  var controlText = document.createElement('div');
  controlText.style.fontFamily = 'Arial,sans-serif';
  controlText.style.fontSize = '12px';
  controlText.style.paddingLeft = '4px';
  controlText.style.paddingRight = '4px';
  controlText.innerHTML = '<b>Home</b>';
  controlUI.appendChild(controlText);

  // Setup the click event listeners: 
  // Set the center to src location, zoom to 14 and remove the displayed direction
  google.maps.event.addDomListener(controlUI, 'click', function() {
    map.setCenter(final_src_latLng);
    map.setZoom(14);
    removeDisplayDirections();
  });

}//End HomeControl


google.maps.event.addDomListener(window, 'load', initialize);

</script>


</head>

<body>

	<!-- subheader starts here -->
	
	
			
	<section class="subheader">


		<div class="ref-search-tab">
			
			<div class="ref-warning-txt"><span></span></div>
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
	
	<!-- subeader ends here -->

	<main> <!-- content starts here -->


	<section class="content-srch">

		<div class="width-container">

			<!-- Maps starts here -->

			<div id="googleMap" class="lft-content fleft"></div>

			<!-- Maps ends here -->

			<div class="rgt-content fright">

				<div class="heading-fixed">

					<h2 class="heading">Cabmates with Cab</h2>

				</div>

				<div class="details-container">
					<c:forEach var="hopResults" items="${hopResultList}"
						varStatus="count">
						<script>
							loadNeighbors(${hopResults.latitude},${hopResults.longitude},'${hopResults.formattedAddress}');
						</script>
						<div class="triggerMap" id="${count.index}">
						<div class="details details-first ${hopResults.postId}">

							


								<div class="fleft journey-details">

									<div class="name">${hopResults.name }</div>

									<div class="time">${hopResults.travelTime }</div>

								</div>

							

							<div class="fright rating-container">

								<div class="star-rating-four"></div>

									<button class="join" id="${hopResults.postId}">Join</button>	
									

							</div>

							<div class="clearall"></div>

							</div>

						</div>

					</c:forEach>

				</div>

			</div>

		</div>

	</section>


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