<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Your Cart</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		CustomerManagerRemote cust = (CustomerManagerRemote)request.getSession().getAttribute("customerREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="">Your Cart</h3>
		<br />[or go back to <a href="../customer.jsp">Customer home</a>]


		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline"><% out.print(user.name); %></div>
			. <br />
			
		</div>

		<br />
		<br />
		<object data="../../images/package.svg" type="image/svg+xml"></object>
		<br />


		<blockquote>
			<%
				DateUtility du = new DateUtility();
				if(cust.getBookedPackages().size() == 0){
					out.print("<p><strong>NO Packages in your Cart</strong></p>");
				}else{
					Iterator<VacationPackage> i = cust.getBookedPackages().iterator();
					int k = 1;
					while(i.hasNext()){
						VacationPackage tmpvp = i.next();
						out.print("<div id=\"hotel-name\" style=\"clear: both; color: orange; font-weight: bold; border-bottom: dotted orange 1px; margin-top: 50px; padding-bottom: 20px;\">Your Selection</div>");
						out.print("<div id=\"hotel-offer\" style=\"float: left; border-left: dotted orange 1px; margin-left: 120px; padding: 20px;\">");
						out.print("<strong>Package #"+k+"</strong> <br /><br />");
						out.print("<p><label>From: </label>"+du.getStringFromDate(tmpvp.getStartDate())+"</p>");
						out.print("<p><label>To: </label>"+du.getStringFromDate(tmpvp.getEndDate())+"</p>");
						out.print("<p><label>Price: </label>"+tmpvp.getPrice()+"</p><br />");
						out.print("<p><label>Hotel: </label>"+tmpvp.getStayHotel().getReferenceHotel().getName()+"</p>");
						out.print("<p><label>Stars: </label>"+tmpvp.getStayHotel().getReferenceHotel().getStars()+"</p>");
						// out.print("<p><label>Customer Rating: </label>[cus rating]</p><br />");
						out.print("<p><label>Airline: </label>"+tmpvp.getDepartFlight().getReferenceFlight().getAirline()+"</p><br />");
						out.print("<p><label>Travel Agency: </label>"+tmpvp.getReferenceAgency().getUsername()+"</p><br /><br />");
						out.print("<form action=\"/cheapvacationsClient/PackageServlet\" method=\"get\">");
						out.print("<input type=\"hidden\" name=\"op\" value=\"2\"/>");
						out.print("<button type=\"submit\">Place Order</button></form>");
						out.print("<form action=\"/cheapvacationsClient/PackageServlet\" method=\"get\">");
						out.print("<input type=\"hidden\" name=\"op\" value=\"3\"/>");
						out.print("<button type=\"submit\">Remove</button>");
						out.print("</form>");
						out.print("</div>");
					}
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