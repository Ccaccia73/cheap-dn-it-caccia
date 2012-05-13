<%@ page import="com.cheapvacations.logic.*, com.cheapvacations.utility.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Customer</title>

<link rel="stylesheet" type="text/css" href="../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		CustomerManagerRemote cust = (CustomerManagerRemote)request.getSession().getAttribute("customerREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Welcome to the Customer Section</h3>
		
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
			<label>Hotel Feedbacks</label>
			<ul>
				<li><a href="hotel/hotel_feedback_new.jsp">Insert New</a></li>
				<li><a href="hotel/hotel_feedback_search.jsp">View
						Feedbacks</a></li>
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
			<label>Flight Feedbacks</label>
			<ul>
				<li><a href="flight/flight_feedback_new.jsp">Insert New</a></li>
				<li><a href="flight/flight_feedback_search.jsp">View
						Feedbacks</a></li>
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
			<br />
			<br />
		</div>

		<div id="entity">
			<object data="../images/package.svg" type="image/svg+xml"></object>
			<br /> <label>PACKAGE</label> <br />
			<br /> <a href="package/package_search.jsp">Search Existing</a> <br />
			<br />
			<hr />
			<object data="../images/cart.svg" type="image/svg+xml"></object>
			<br /> <label>Cart</label> <br />
			<ul>
				<li><a href="package/cart.jsp">View Cart</a></li>
				<li><a href="package/orders.jsp">View Orders</a></li>
			</ul>
		</div>



		<br />

		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>