<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>New Flight OFFER</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />
<link href="../../css/CalendarControl.css" rel="stylesheet"
	type="text/css" />
<script src="../../js/CalendarControl.js" language="javascript" type=""></script>


</head>

<body>
	<%
		OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
		
		long Id = Long.parseLong(request.getParameter("fid"));
		
		Flight f = op.manageFlight(null, null, null, null, -1, Id, 5).get(0);
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Insert A New Flight OFFER</h3>
		<br />[or go back to <a href="../operator.jsp">Operator home</a>]
		
		<%
			String p2 = (String)request.getParameter("insert");
			if (p2 != null){
				if(p2.equals("fail")){
					out.println("<p ><strong>Data Input Error</strong></p>");
				}
			}else{
				// out.println("<p>NULL</p>");
			}
		%>

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


		<p>Selected Flight:</p><br />
		<div>
			Airline: <% out.println(f.getAirline()); %>
			Departure: <% out.println(f.getDeparture()); %>
			Arrival: <%out.println(f.getArrival()); %>		
		</div>
		<form action="/cheapvacationsClient/FlightOfferServlet" method="get">
			<br /> <br />
				<label>Start Date: <input type="text" name="fo_start" onfocus="showCalendarControl(this);" /></label><br />
				<label>End Date: <input type="text" name="fo_end" onfocus="showCalendarControl(this);" /> </label><br />
				<br />
				<label>Price: <input type="text" name="fo_price" /> <small>(all prices are in EUROS)</small>
				</label><br /> <br /> <br />
				<input type="hidden" name="fid" value="<%=Id%>"/>
				<input type="hidden" name="op" value="1"/>
				<input type="submit" value="Insert New Flight OFFER" /> <br /> <input type="reset" value="Clear" />
		</form>
		<div id="cal1Container"></div>
		<br />

		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>