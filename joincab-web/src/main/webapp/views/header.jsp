<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html  manifest="hytt.appcache">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The Hytt</title>

</head>
<body>
	<!-- hdr starts here -->
	
	<header>

		<div class="width-container">
			
			<div class="logo logo-small fleft">
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
			
				<nav class="home-navigation fleft">
					

					<ul>
						<li><a href="./home#home" id="homeLink">Home</a></li>
						<li><a href="./home#about">About</a></li>
						<li><a href="./home#contact">Contact</a></li>
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
		
</body>
</html>