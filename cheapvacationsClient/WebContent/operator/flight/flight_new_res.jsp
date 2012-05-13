<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>New Flight</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		// OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
		
		Flight f = ((List<Flight>)request.getSession().getAttribute("flight")).get(0);
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">New Flight Inserted</h3>
		<br />[or go back to <a href="../operator.jsp">Operator home</a>]


		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline"><% out.print(user.name); %></div>
			. <br />
			
		</div>

		<br />
		<br />
		<object data="../../images/flight.svg" type="image/svg+xml"></object>
		<br /> <br />
		<br /> You have added the following flight reference: <br />
		<br />
		<blockquote>

			<div style="color: orange; font-weight: bold;">Code: <%out.print(f.getCode()); %></div>
			<p>Airline: <%out.print(f.getAirline()); %></p>
			<p>Departure: <%out.print(f.getDeparture()); %></p>
			<p>Arrival: <%out.print(f.getArrival()); %></p>
			<!-- <p>Time</p> -->
			<!-- <p>Duration</p> -->
			<a href="flight_offer_new.jsp?fid=<%out.print(f.getId()); %>"><small>Create new offer?</small></a>

		</blockquote>

		<br />

		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>