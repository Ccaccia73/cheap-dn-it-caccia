<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hotel Search</title>

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
		<h3 style="display: inline;">Search Hotel</h3>
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
					out.println("<p ><strong>Hotel Query Error</strong></p>");
				}
			}
		%>

		<br />
		<br />
		<object data="../../images/hotel.svg" type="image/svg+xml"></object>
		<br /> <br />
		<br />



		<form action="/cheapvacationsClient/HotelServlet" method="get">
			<label>Hotel Name: <input type="text" name="hname" /></label><br />
			<!--<label>Address: <input type="text" name="haddress" /></label><br />-->
			<label>City: <input type="text" name="hcity" /></label><br /> <b>Minimum Stars:</b>
			<input type="radio" name="hstars" value="1" /> *
			<input type="radio" name="hstars" value="2" /> **
			<input type="radio" name="hstars" value="3" /> ***
			<input type="radio" name="hstars" value="4" /> ****
			<br /> 
			<input type="hidden" name="op" value="2" />
			<br /> <br />
			<input type="submit" value="Search Hotel" />
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