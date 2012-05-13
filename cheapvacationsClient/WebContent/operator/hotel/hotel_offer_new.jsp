<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>New Hotel OFFER</title>

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
		
		long Id = Long.parseLong(request.getParameter("hid"));
		
		Hotel h = ((List<Hotel>)op.manageHotel(null, null, null, 0, -1, Id, 5)).get(0);
	%>


	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Insert A New Hotel OFFER</h3>
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
		<object data="../../images/hotel.svg" type="image/svg+xml"></object>
		<br /> <br />
		<br />


		<p>Selected hotel:</p><br />
		<div>
			Name: <% out.println(h.getName()); %>
			City: <% out.println(h.getCity()); %>
			Stars: <%out.println(h.getStars()); %>		
		</div>
		<form action="/cheapvacationsClient/HotelOfferServlet" method="get">
			<br /> <br />
				<label>Start Date: <input type="text" name="ho_start" onfocus="showCalendarControl(this);" /></label><br />
				<label>End Date: <input type="text" name="ho_end" onfocus="showCalendarControl(this);" /> </label><br />
				<br />
				<label>Price: <input type="text" name="ho_price" /> <small>(all prices are in EUROS)</small>
				</label><br /> <br /> <br />
				<input type="hidden" name="hid" value="<%=Id%>"/>
				<input type="hidden" name="op" value="1"/>
				<input type="submit" value="Insert New Hotel OFFER" /> <br /> <input type="reset" value="Clear" />
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