<%@ page import="com.cheapvacations.logic.*,com.cheapvacations.entities.*,com.cheapvacations.utility.*,java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Package Search Results</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		CustomerManagerRemote cust = (CustomerManagerRemote) request
				.getSession().getAttribute("customerREF");
		UserData user = (UserData) request.getSession()
				.getAttribute("user");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="">Package Search Results</h3>
		<br />[or go back to <a href="../customer.jsp">Customer home</a>]


		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline"><%
			out.print(user.name);
		%></div>
			. <br />
			
		</div>

		<br />
		<%
			String addpack = request.getParameter("addpack");
			if (addpack != null) {
				if (addpack.equals("fail")) {
					out.print("<strong>Sorry, PACKAGE has EXPIRED</strong>");
				}
			}
		%>

		<br />
		<object data="../../images/package.svg" type="image/svg+xml"></object>
		<br /> <br />
		<%
			if (cust.getPackages() == null) {
		%>
			<br /><strong>NO Results Found</strong>		
		<%
					} else if (cust.getPackages().isEmpty()) {
				%>
			<br /><strong>NO Results Found</strong>
		<%
			} else {
		%>		
		<br /> The following results were found: <br />
		<br />
		<blockquote>

			<div id="hotel-name"
				style="clear: both; color: orange; font-weight: bold; border-top: dotted orange 1px; margin-top: 50px;">Your Serch Criteria</div>
			<div id="hotel-details" style="float: left">
				<%
					String hid = request.getParameter("hid");

						String fid = request.getParameter("fid");

						String departure = request.getParameter("pdepart");
						String arrival = request.getParameter("parrive");
						String hotelname = request.getParameter("photel");
						String str_price = request.getParameter("pmaxprice");
						String pstartdate = request.getParameter("pstartdate");
						String penddate = request.getParameter("penddate");

						DateUtility du = new DateUtility();

						if (hid != null) {
							if (hid.length() > 0) {
								Hotel h1 = cust.getHotel();

								out.print("<p><strong>Hotel</strong></p>");
								out.print("<p>Name: " + h1.getName() + "</p>");
								out.print("<p>City: " + h1.getCity() + "</p>");
								out.print("<p>Stars: " + h1.getStars() + "</p>");
								// out.print("<p>Period: "+du.getStringFromDate(ho.getStartDate())+" - "+du.getStringFromDate(ho.getEndDate())+"</p>");
								// out.print("<p>Price: "+ho.getPrice()+"</p>");

							}
						} else if (fid != null) {
							if (fid.length() > 0) {
								Flight f1 = cust.getFlight();
								out.print("<p><strong>Flight</strong></p>");
								out.print("<p>Airline: " + f1.getAirline() + "</p>");
								out.print("<p>Code: " + f1.getCode() + "</p>");
								out.print("<p>Departure: " + f1.getDeparture() + "</p>");
								out.print("<p>Arrival: " + f1.getArrival() + "</p>");
								// out.print("<p>Period: "+du.getStringFromDate(fo.getStartDate())+" - "+du.getStringFromDate(fo.getEndDate())+"</p>");
								// out.print("<p>Price: "+fo.getPrice()+"</p>");
							}
						} else {
							if (departure.length() > 0) {
								out.print("<p>Departure: " + departure + "</p>");
							}
							if (arrival.length() > 0) {
								out.print("<p>Arrival: " + arrival + "</p>");
							}
							if (hotelname.length() > 0) {
								out.print("<p>Hotel Name: " + hotelname + "</p>");
							}
							if (str_price.length() > 0) {
								out.print("<p>Max Price: " + str_price + "</p>");
							}
							if (pstartdate.length() > 0) {
								out.print("<p>Departure: " + pstartdate + "</p>");
							}
							if (penddate.length() > 0) {
								out.print("<p>Departure: " + penddate + "</p>");
							}
						}
				%>
			</div>
			<%
				int k = 1;
					Iterator<VacationPackage> i = cust.getPackages().iterator();
					while (i.hasNext()) {
						VacationPackage tempp = i.next();
						if (tempp.getStatus() == 2) {
							out.print("<div id=\"hotel-offer\" style=\"float: left; width: 150px; border-left: dotted orange 1px; margin-left: 70px; padding-left: 20px;\">");
							out.print("<strong>Package #" + k + "</strong> <br />");
							out.print("<br />");
							out.print("<p>Departure: "
									+ tempp.getDepartFlight().getReferenceFlight()
											.getDeparture() + "</p>");
							out.print("<p>Arrival: "
									+ tempp.getDepartFlight().getReferenceFlight()
											.getArrival() + "</p>");
							out.print("<p>Hotel: "
									+ tempp.getStayHotel().getReferenceHotel()
											.getName() + "</p>");
							out.print("<p>From: "
									+ du.getStringFromDate(tempp.getStartDate())
									+ "</p>");
							out.print("<p>To: "
									+ du.getStringFromDate(tempp.getEndDate())
									+ "</p>");
							out.print("<p>Price: " + tempp.getPrice() + "</p>");
							out.print("<br />");
							out.println("<form action=\"/cheapvacationsClient/CartServlet\" method=\"get\">");
							out.println("<button name=\"p_id\" type=\"submit\" value=\""
									+ tempp.getId()
									+ "\">Add Package to Cart</button>");
							out.println("<input type=\"hidden\" name=\"op\" value=\"1\"/>");
							out.print("<br />");
							out.print("<br />");
							out.print("</div>");

							k++;
						}
					}
			%>

				<!--  
						<form action="package_details.jsp" method="get">
							<button name="detail" type="submit" value="">See Details</button>
						</form>
					-->
		</blockquote>
		<%
			}
		%>
		<br />

		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>