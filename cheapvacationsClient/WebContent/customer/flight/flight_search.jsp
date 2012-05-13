<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Search Flight</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		// CustomerManagerRemote op = (CustomerManagerRemote)request.getSession().getAttribute("customerREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Search Flight</h3>
		<br />[or go back to <a href="../customer.jsp">Customer home</a>]
		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline"><% out.print(user.name); %></div>
			. <br />
			
		</div>
		<%
			String p2 = (String)request.getParameter("query");
			if (p2 != null){
				if(p2.equals("fail")){
					out.println("<p ><strong>Flight Query Error</strong></p>");
				}
			}
		%>

		<br />
		<br />
		<object data="../../images/flight.svg" type="image/svg+xml"></object>
		<br /> <br />
		<br />



		<form action="/cheapvacationsClient/FlightServlet" method="get">
			<label>Airline: <input type="text" name="fairline" /></label><br />
			<label>Departure City: <input type="text" name="fdeparture" /></label><br />
			<label>Arrival City: <input type="text" name="farrival" /></label><br />
			<br /> <br />
			<input type="hidden" name="op" value="2" />
			<br />
			<input type="submit" value="Search Flight" />
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