<%@ page import="com.cheapvacations.logic.*, com.cheapvacations.utility.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Operator</title>

<link rel="stylesheet" type="text/css" href="../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Welcome to the Operator Section</h3>
		
		
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
				<li><a href="hotel/hotel_new.jsp">Insert New</a></li>
				<li><a href="hotel/hotel_search.jsp">Manage Hotels</a></li>
			</ul>
			<ul>
				<li>
				<%
					String hup = request.getParameter("hotelupdate");
					if(hup != null){
						if(hup.equals("ok")){
							out.print("<strong>Hotel successfully updated</strong>");
						}else if(hup.equals("fail")){
							out.print("<strong>Failed to update Hotel</strong>");
						}
					}
					String hrem = request.getParameter("hotelremove");
					if(hrem != null){
						if(hrem.equals("ok")){
							out.print("<strong>Hotel successfully removed</strong>");
						}else if(hrem.equals("fail")){
							out.print("<strong>Failed to remove Hotel</strong>");
						}
					}		
				%>
				</li>
			</ul>

			<hr />
			<label>Hotel Offer</label>
			<ul>
				<li><a href="hotel/hotel_search.jsp">Insert New or Manage</a></li>
				<!-- <li><a href="hotel/hotel_offer_search.jsp">Manage Offers</a></li> -->
			</ul>
			<ul>
				<li>
				<%
					String hoi = request.getParameter("hotelofferinsert");
					if(hoi != null){
						if(hoi.equals("ok")){
							out.print("<strong>Hotel Offer successfully inserted</strong>");
						}else if(hoi.equals("fail")){
							out.print("<strong>Failed to insert Hotel Offer</strong>");
						}
					}
					String hor = request.getParameter("hotelofferremove");
					if(hor != null){
						if(hor.equals("ok")){
							out.print("<strong>Hotel Offer successfully removed</strong>");
						}else if(hor.equals("fail")){
							out.print("<strong>Failed to remove Hotel Offer</strong>");
						}
					}
				%>
				</li>
			</ul>

			<br />
		</div>

		<div id="entity">
			<object style="margin: 34px;" data="../images/flight.svg"
				type="image/svg+xml"></object>
			<br/ > <label>FLIGHT</label>
			<ul>
				<li><a href="flight/flight_new.jsp">Insert New</a></li>
				<li><a href="flight/flight_search.jsp">Manage Flights</a></li>
			</ul>
			<ul>
				<li>
				<%
					String fup = request.getParameter("flightupdate");
					if(fup != null){
						if(fup.equals("ok")){
							out.print("<strong>Flight successfully updated</strong>");
						}else if(fup.equals("fail")){
							out.print("<strong>Failed to update Flight</strong>");
						}
					}
					String frem = request.getParameter("flightremove");
					if(frem != null){
						if(frem.equals("ok")){
							out.print("<strong>Flight successfully removed</strong>");
						}else if(frem.equals("fail")){
							out.print("<strong>Failed to remove Flight</strong>");
						}
					}		
				%>
				</li>
			</ul>
			<hr />
			<label>Flight Offer</label>
			<ul>
				<li><a href="flight/flight_search.jsp">Insert New or Manage</a></li>
				<!-- <li><a href="flight/flight_offer_search.jsp">Manage Offers</a></li> -->
			</ul>
			<ul>
				<li>
				<%
					String foi = request.getParameter("flightofferinsert");
					if(foi != null){
						if(foi.equals("ok")){
							out.print("<strong>Flight Offer successfully inserted</strong>");
						}else if(foi.equals("fail")){
							out.print("<strong>Failed to insert Flight Offer</strong>");
						}
					}
					String for1 = request.getParameter("flightofferremove");
					if(for1 != null){
						if(for1.equals("ok")){
							out.print("<strong>Flight Offer successfully removed</strong>");
						}else if(for1.equals("fail")){
							out.print("<strong>Failed to remove Flight Offer</strong>");
						}
					}
				%>
				</li>
			</ul>
			<br />
		</div>

		<div id="entity">
			<br />
			<object data="../images/package.svg" type="image/svg+xml"></object>
			<br /> <label>PACKAGE</label> <br />
			<br />
			<br /> <a href="package/package_search.jsp">Search Existing</a>
			<!--<ul><li ><a href="">Insert New</a></li><li ><a href="">Search Existing</a></li></ul>-->
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			
			<!--<hr>
<label>Package Offer</label>
<ul><li ><a href="">Insert New</a></li><li ><a href="">Search Existing</a></li></ul>
-->
			<br />
		</div>



		<br />

		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>