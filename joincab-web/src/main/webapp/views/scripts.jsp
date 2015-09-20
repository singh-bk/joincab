<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html  manifest="hytt.appcache">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The Hytt</title>
<%String contextPath= request.getContextPath();%>

	<link rel="stylesheet" type="text/css" href="./css/style.css">
	<link rel="stylesheet" type="text/css" href="./css/style-dd.css">
	<link rel="stylesheet" type="text/css" href="./css/reset.css">
	<link rel="stylesheet" type="text/css" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css"/>
	<link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">

	<script type="text/javascript" src="./js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="./js/scrollheader.js"></script>
	<script type="text/javascript" src="./js/html5lightbox.js"></script>
	<script type="text/javascript" src="./js/custom.js"></script>
	<script type="text/javascript" src="./js/profile.js"></script>
	<script type="text/javascript" src="./js/custom-ui.js"></script>
	<script type="text/javascript" src="./js/auth.js"></script>
	<script type="text/javascript" src="./js/jquery.timepicker.js"></script>
	<script type="text/javascript" src="./js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="./js/jquery.geocomplete.js"></script>
	<script type="text/javascript" src="./js/jstorage.js"></script>
	<script type="text/javascript" src="./js/jquery.form-validator.min.js"></script>
	<script type="text/javascript" src="./js/jquery-cycle.js"></script>
	<script type="text/javascript" src="./js/jquery.blockUI.min.js"></script>
	<script type="text/javascript" src="./js/jquery.fblogin.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/json2/20110223/json2.js"></script>
	<script src="http://maps.googleapis.com/maps/api/js?libraries=places"></script>
	<script src="https://apis.google.com/js/client:platform.js" async defer></script>
	<script type="text/javascript">
		var csrf_token = '${_csrf.token}';
		
		function initialize() {
			  var mapProp = {
			    center:new google.maps.LatLng(51.508742,-0.120850),
			    zoom:5,
			    mapTypeId:google.maps.MapTypeId.ROADMAP
			  };
			  var map=new google.maps.Map(document.getElementById("googleMap"), mapProp);
			}
			google.maps.event.addDomListener(window, 'load', initialize);

	 
     
      (function(d, s, id){
         var js, fjs = d.getElementsByTagName(s)[0];
         if (d.getElementById(id)) {return;}
         js = d.createElement(s); js.id = id;
         js.src = "//connect.facebook.net/en_US/sdk.js";
         fjs.parentNode.insertBefore(js, fjs);
       }(document, 'script', 'facebook-jssdk'));
    
	</script>
</head>
<body>
</body>
</html>