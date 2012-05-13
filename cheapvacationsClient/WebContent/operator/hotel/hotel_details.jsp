<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Update Hotel Details</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
		long Id = Long.parseLong(request.getParameter("hid"));
		
		List<Hotel> hl = op.manageHotel(null, null, null, 0, -1, Id, 5);
		
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Update Hotel Details</h3>
		<br />[or go back to <a href="../operator.jsp">Operator home</a>]


		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline"><% out.print(user.name); %></div>
			. <br />
			
		</div>

		<br />
		<br />
		<object data="../../images/Hotel.svg" type="image/svg+xml"></object>
		<br /> <br />
		<br />



		<form action="/cheapvacationsClient/HotelServlet" method="get">

			<label>Hotel Name: <%out.print(hl.get(0).getName()); %></label><br />
			<input type="hidden" name="hname" value="<%out.print(hl.get(0).getName()); %>"/>
			<label>City: <%out.print(hl.get(0).getCity()); %> </label><br />
			<input type="hidden" name="hcity" value="<%out.print(hl.get(0).getCity()); %>"/>
			<label>Address: <input type="text" name="haddress" value="<% out.print(hl.get(0).getAddress()); %>" /></label><br /> 
			<b>Stars</b>: <input type="radio" name="hstars" value="1" <% if(hl.get(0).getStars() == 1){out.print("checked=\"y\"");} %> /> *
							<input type="radio" name="hstars" value="2" <% if(hl.get(0).getStars() == 2){out.print("checked=\"y\"");} %> /> **
							<input type="radio" name="hstars" value="3" <% if(hl.get(0).getStars() == 3){out.print("checked=\"y\"");} %>/> ***
							<input type="radio" name="hstars" value="4" <% if(hl.get(0).getStars() == 4){out.print("checked=\"y\"");} %>/> ****
			<br /> <br /> <br />
			<input type="submit" value="Update Hotel Details" />
			<input type="reset" value="Clear" />
			<input type="hidden" name="op" value="3"/>
		</form>
		<br />

		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>