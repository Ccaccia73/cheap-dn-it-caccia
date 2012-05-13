<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Update Flight Details</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
		long Id = Long.parseLong(request.getParameter("fid"));
		
		List<Flight> fl = op.manageFlight(null, null, null, null, -1, Id, 5);
		
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Update Flight Details</h3>
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
		<br />



		<form action="/cheapvacationsClient/FlightServlet" method="get">
			<label>Code: <input type="text" name="fcode" value="<%out.print(fl.get(0).getCode()); %>" /></label><br />
			<label>Airline: <% out.print(fl.get(0).getAirline()); %></label><br />
			<input type="hidden" name="fairline" value="<%out.print(fl.get(0).getAirline()); %>"/>
			<label>Departure: <% out.print(fl.get(0).getDeparture()); %> </label><br />
			<input type="hidden" name="fdeparture" value="<%out.print(fl.get(0).getDeparture()); %>"/>
			<label>Arrival: <% out.print(fl.get(0).getArrival()); %></label><br />
			<input type="hidden" name="farrival" value="<%out.print(fl.get(0).getArrival()); %>"/>
			<!-- <label>Time: <input type="text" name="ftime" value="[pescato da DB]" /></label><br />  -->
			<!-- <label>Duration: <input type="text" name="fduration" value="[pescato da DB]" /> </label><br /> -->
			<br /> <br />
			<input type="hidden" name="op" value="3"/>
			<input type="submit" value="Update Flight Details" />
			<input type="reset" value="Clear" />
		</form>
		<br />

		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>