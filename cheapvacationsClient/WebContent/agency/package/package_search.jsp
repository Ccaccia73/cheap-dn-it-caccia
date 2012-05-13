<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Search Package OFFERS</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />

<link href="../../css/CalendarControl.css" rel="stylesheet"
	type="text/css" />
<script src="../../js/CalendarControl.js" language="javascript" type=""></script>



</head>

<body>
	<%
		// TravelAgencyManagerRemote ta = (TravelAgencyManagerRemote)request.getSession().getAttribute("agencyREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Find A Package</h3>
		<br />[or go back to <a href="../agency.jsp">Travel Agency home</a>]
		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline"><% out.print(user.name); %></div>
			. <br />
			
		</div>

		<br />
		<br />
		<object data="../../images/package.svg" type="image/svg+xml"></object>
		<br /> <br />
		<br />



		<form action="/cheapvacationsClient/PackageServlet" method="get">
			<label>Departure City : <input type="text" name="pdepart" /></label><br />
			<!-- <label>Address: <input type="text" name="haddress" /></label><br />-->
			<label>Arrival City: <input type="text" name="parrive" /></label><br />
			<label>Hotel Name: <input type="text" name="photel" /></label><br />
			<!-- <label>Min Price: <input type="text" name="minprice" /></label><br /> -->
			<label>Max Price: <input type="text" name="pmaxprice" /></label><br />
			<br />
			<label>Start Date: <input type="text" name="pstartdate" onfocus="showCalendarControl(this);" /></label>
			<br />
			<label>End Date: <input type="text" name="penddate" onfocus="showCalendarControl(this);" /></label>
			<br />
			<input type="hidden" name="op" value="4"/>
			<input type="submit" value="Submit" />
			<input type="reset" value="Clear" />
		</form>
		<br />

		<div class="push"></div>

	</div>

	<div id="footer_ag">
		<jsp:include page="../footer.jsp" />
	</div>


</body>

</html>