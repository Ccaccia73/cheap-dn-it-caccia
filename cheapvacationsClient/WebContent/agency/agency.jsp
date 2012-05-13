<%@ page import="com.cheapvacations.logic.*, com.cheapvacations.utility.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Travel Agency</title>

<link rel="stylesheet" type="text/css" href="../css/theme1.css"
	title="default" />

</head>

<body>
	<%
		TravelAgencyManagerRemote ta = (TravelAgencyManagerRemote)request.getSession().getAttribute("agencyREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Welcome to the Travel Agency Section</h3>
		
		
		<div id="update-usub"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right; border-left:1px dotted orange; padding-left:10px;">

			<form action="../update.jsp" method="get">
				<button type="submit" style="width:89px">Update</button><br/>
			</form>
			<br/>
			<form action="../unsubscribe.jsp" method="get">
				<button type="submit">Unsubscribe</button>
			</form>
		</div>
		
		
		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 20px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline">
			<% out.print(user.name); %>
			</div>
			. <br />
			<form action="../LogoutServlet" method="get">
				<button type="submit">Logout</button>
			</form>
		</div>

		<br />
		<br />
		<br />
		<br />
		<br />
		<br />




		<div id="entity">
			<object data="../images/hotel.svg" type="image/svg+xml"></object>
			<br /> <label>HOTEL</label>
			<ul>
				<li>
					<a href="hotel/hotel_search.jsp">Search Existing</a>
				</li>
			</ul>
			<br />
			<hr />
			<label>Hotel Offer</label>
			<ul>
				<li>
					<a href="hotel/hotel_search.jsp">Search Existing</a>
				</li>
			</ul>
			<ul>
				<li>
				<%
					String hoi = request.getParameter("hotelofferinsert");
					if(hoi != null){
						if(hoi.equals("ok")){
							out.print("<strong>Hotel Offer successfully Added</strong>");
						}else if(hoi.equals("fail")){
							out.print("<strong>Failed to Add Hotel Offer</strong>");
						}
					}
				%>
				</li>
			</ul>
			<br />
			<br />
		</div>

		<div id="entity">
			<object style="margin: 34px;" data="../images/flight.svg"
				type="image/svg+xml"></object>
			<br/> <label>FLIGHT</label>
			<ul>
				<li>
					<a href="flight/flight_search.jsp">Search Existing</a>
				</li>
			</ul>
			<br />
			<hr />
			<label>Flight Offer</label>
			<ul>
				<li>
					<a href="flight/flight_search.jsp">Search Existing</a>
				</li>
			</ul>
			<ul>
				<li>
				<%
					String foi = request.getParameter("flightofferinsert");
					if(foi != null){
						if(foi.equals("Dok")){
							out.print("<strong>Departure Flight Offer added OK</strong>");
						}else if(foi.equals("Rok")){
							out.print("<strong>Return Flight Offer added OK</strong>");
						}else if(foi.equals("fail")){
							out.print("<strong>Failed to add Flight Offer</strong>");
						}
					}
				%>
				</li>
			</ul>
			<br />
			<br />
		</div>

		<div id="entity">
			<object data="../images/package.svg" type="image/svg+xml"></object>
			<br /> <label>PACKAGE</label> <br />
			<br />
			<ul>
				<li><a href="package/package_new.jsp">Insert New</a></li>
				<li><a href="package/package_search.jsp">Manage Packages</a></li>
			</ul>
			<ul>
				<li>
				<%
					String poi = request.getParameter("compcancel");
					if(poi != null){
						if(poi.equals("ok")){
							out.print("<strong>Components canceled OK</strong>");
						}else if(poi.equals("fail")){
							out.print("<strong>Failed cancel Components</strong>");
						}
					}
					
					String pkc = request.getParameter("packcancel");
					if(pkc != null){
						if(pkc.equals("ok")){
							out.print("<strong>Package canceled OK</strong>");
						}else if(pkc.equals("fail")){
							out.print("<strong>Failed cancel Package</strong>");
						}
					}

					String pki = request.getParameter("packinsert");
					if(pki != null){
						if(pki.equals("ok")){
							out.print("<strong>PACKAGE inserted OK</strong>");
						}
					}
					
				%>
				</li>
			</ul>
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />

		</div>



		<br />

		<div class="push"></div>

	</div>

	<div id="footer_ag">
		<jsp:include page="footer.jsp" />
	</div>


</body>

</html>