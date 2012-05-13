<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hotel Feedbacks</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		// CustomerManagerRemote op = (CustomerManagerRemote)request.getSession().getAttribute("customerREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
		List<Hotel> hl = (List<Hotel>)request.getSession().getAttribute("hotel");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Hotel Feedbacks</h3>
		<br />[or go back to <a href="../customer.jsp">Customer home</a>]
		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline"><% out.print(user.name); %></div>
			. <br />
			
		</div>

		<br />
		<br />
		<object data="../../images/hotel.svg" type="image/svg+xml"></object>
		<br />




		<div id="results"
			style="clear: both; color: orange; font-weight: bold; border-top: dotted orange 1px; margin-top: 50px;"></div>


		<div id="hotel-details"
			style="float: left; margin-left: 30px; border-left: dotted orange 1px; padding: 30px;">
			<div id="hotel-name"
				style="color: orange; font-weight: bold; margin-top: 20px;">Nome
				Hotel #1</div>
			<p>Indirizzo dell'hotel</p>
			<p>Città</p>
			<br />
			<br />


		</div>

		<!--AVE rating-->

		<div id="hotel-details"
			style="float: left; margin-left: 30px; border-left: dotted orange 1px; padding: 30px;">

			<h1>Average Hotel Rating</h1>
			<h2>8,5</h2>
			<br />
			<br />
			<br />
			<br />

		</div>

		<div id="hotel-feedback"
			style="clear: both; margin-left: 30px; border-left: dotted orange 1px; border-top: dotted orange 1px; padding: 30px;">
			<div id="hotel-name"
				style="color: orange; font-weight: bold; margin-top: 20px;">User1</div>
			<br /> <label>Rating: [rating]</label> <br />
			<br /> <label>Comment:</label>
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
				do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
				enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi
				ut aliquip ex ea commodo consequat. Duis aute irure dolor in
				reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
				pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
				culpa qui officia deserunt mollit anim id est laborum</p>
			<br />
			<br />
		</div>

		<div id="hotel-feedback"
			style="clear: both; margin-left: 30px; border-left: dotted orange 1px; border-top: dotted orange 1px; padding: 30px;">
			<div id="hotel-name"
				style="color: orange; font-weight: bold; margin-top: 20px;">User2</div>
			<br /> <label>Rating: [rating]</label> <br />
			<br /> <label>Comment:</label>
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
				do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
				enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi
				ut aliquip ex ea commodo consequat. Duis aute irure dolor in
				reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
				pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
				culpa qui officia deserunt mollit anim id est laborum</p>
			<br />
			<br />
		</div>



		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>