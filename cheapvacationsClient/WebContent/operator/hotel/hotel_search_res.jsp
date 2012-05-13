<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hotel Search Results</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		// OperatorManagerRemote op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
		
		List<Hotel> hl = (List<Hotel>)request.getSession().getAttribute("hotel");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Hotel Results</h3>
		<br />[or go back to <a href="../operator.jsp">Operator home</a>]


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
		<br /> The following results were found: <br />
		<br />
		<blockquote>
			<% Iterator<Hotel> i = hl.iterator();
				while(i.hasNext()){
					Hotel h1 = i.next();
					out.print("<div id=\"hotel-name\" style=\"clear: both; color: orange; font-weight: bold; border-top: dotted orange 1px; margin-top: 50px;\">");
					out.print("Hotel "+h1.getName()+"</div>");
					out.print("<div id=\"hotel-details\" style=\"float: left\"><p>"+h1.getAddress()+"</p>");
					out.print("<p>"+h1.getCity()+"</p>");
					out.print("<a href=\"hotel_offer_new.jsp?hid="+h1.getId()+"\">Create new offer?</a>");
					out.print("<br/><br/>");
					out.print("<form action=\"/cheapvacationsClient/HotelOfferServlet\" method=\"get\">");
					out.print("<button name=\"hid\" type=\"submit\" value=\""+h1.getId()+"\">See related Offers</button>");
					out.print("<input type=\"hidden\" name=\"op\" value=\"6\"/>");
					out.print("<br /> </form>");					
					out.print("</div>");
					out.print("<div id=\"manage-hotel\" style=\"padding-right: 50px; border-left: dotted orange 1px; margin-left: 180px; padding-left: 20px; width: 400px;\">");
					out.print("<form action=\"/cheapvacationsClient/HotelServlet\" method=\"get\">");
					out.print("<button name=\"hid\" type=\"submit\" value=\""+h1.getId()+"\">Remove from DB</button>");
					out.print("<input type=\"hidden\" name=\"op\" value=\"4\"/>");
					out.print("<br /> <br /> </form>");
					out.print("<form action=\"hotel_details.jsp\" method=\"get\">");
					out.print("<button name=\"hid\" type=\"submit\" value=\""+h1.getId()+"\">Update details</button>");
					out.print("</form><br /><br />");
					out.print("</div>");					
				}
			%>
		</blockquote>

		<br />

		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>