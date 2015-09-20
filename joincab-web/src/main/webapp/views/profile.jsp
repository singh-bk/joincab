<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="model.jsp"></jsp:include>
<jsp:include page="scripts.jsp"></jsp:include>
<!DOCTYPE html>
<html lang="en" manifest="hytt.appcache">
<head>
<meta charset="UTF-8">
<title>Profile</title>
</head>
<body>

	<!-- Content starts here -->

	<section class="content">

		<div class="uprofilecontainer">

			<div class="ulft-container fleft">

				<ul>
					<li><a href="#" class="userProfile active">User Profile</a></li>
					<li><a href="#" class="hopHistory">Hop History</a></li>
					<li><a href="#" class="postHistory">Post History</a></li>
					<li><a href="#" class="testimonial">Testimonial</a></li>
				</ul>


			</div>

			<!-- user profile starts here -->

			<div class="fleft urgt-container" id="userContainer">

				<div class="clearall"></div>
				<div class="ucontainer">

					<div id="uprofile" class="userpro">
						<div class="fleft upic-container">
							<img
								src="http://graph.facebook.com/${userProfile.providerUid}/picture?type=large"
								alt="mypic" width="100" height="100" />
						</div>
						<a href="#" class="edit-upro save-upro fright"></a>
						<div class="uprodet">

							<table width="82%" border="none">
								<tr>
									<td width="15%">Name:-</td>
									<td width="85%">${userProfile.firstName}</td>
								</tr>
								<tr>
									<td width="15%">Email:-</td>
									<td width="85%">${userProfile.email}</td>
								</tr>
								<tr>
									<td width="15%">Phone Num:-</td>
									<td width="85%">${userProfile.phoneNumber}</td>
								</tr>
							</table>


						</div>

						<div class="clearall"></div>
					</div>


				</div>

			</div>

			<!-- user profile ends here -->

			<!-- Hop details -->

			<div class="fleft urgt-container dis-none" id="journeyContainer">

				<div class="clearall"></div>
				<div class="utable">

					<!-- content table starts here -->

					<div class="tablecontainer">

						<div class="table-head dis-none" id="dataTableHeader">

							<ul>
								<li class="dateth">Date / Time</li>
								<li class="locationth">Location</li>
								<li class="hopuserth">Name</li>
								<li class="stautsth">Status</li>
								<li class="actionsth">Actions</li>
							</ul>

						</div>

						<div class="tabledet-container" id="dataContainer"></div>

					</div>

					<!-- content table ends here -->


				</div>

			</div>

			<!-- Hop history ends here -->

			<!-- Testimonials starts here -->

			<div class="fleft urgt-container dis-none" id="testimonialContainer">


				<div class="clearall"></div>

				<textarea name="" id="subText"
					placeholder="Please write something for us.." maxlength="500"
					class="tstmonial-textarea"></textarea>
				<div class="clearall"></div>
				<div class="tst-btncont fleft">

					<button class="black-btn fleft testimonailSubmit" id="feedbackSb">Submit</button>
					<button class="black-btn fleft clearSubmit " id="clr">Clear</button>

				</div>


			</div>

		</div>

	</section>

	<!-- Content ends here -->

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
</body>
</html>