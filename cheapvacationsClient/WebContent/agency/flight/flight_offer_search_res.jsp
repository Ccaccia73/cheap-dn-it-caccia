<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Flight Offers Search Results</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		UserData user = (UserData)request.getSession().getAttribute("user");
		List<FlightOffer>fol = (List<FlightOffer>)request.getSession().getAttribute("flightoffer");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="">View Flight OFFERS</h3>
		<br />[or go back to <a href="../agency.jsp">Travel Agency home</a>]


		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline"><% out.print(user.name); %></div>
			. <br />
			
		</div>

		<br />
		<br />
		<object data="../../../images/flight.svg" type="image/svg+xml"></object>
		<br /> <br />
		<br /> The following results were found: <br />
		<br />
		<blockquote>
		<%
			if(fol != null && fol.size() > 0){
				Iterator<FlightOffer> i = fol.iterator();
				int k = 1;
				DateUtility du = new DateUtility();
					Flight f1 = fol.get(0).getReferenceFlight();
					out.println("<div id=\"flight-name\" style=\"clear: both; color: orange; font-weight: bold; border-top: dotted orange 1px; margin-top: 50px;\">");
					out.println("Flight Code:"+f1.getCode());
					out.println("</div>");
					out.println("<div id=\"flight-details\" style=\"float: left\">");
					out.println("<p>Line: "+f1.getAirline()+"</p>");
					out.println("<p>From: "+f1.getDeparture()+"</p>");
					out.println("<p>To: "+f1.getArrival()+"</p>");
					out.println("</div>");
					while(i.hasNext()){
						FlightOffer fo1 = i.next();
						out.println("<div id=\"flight-offer\" style=\"float: left; width: 150px; border-left: dotted orange 1px; margin-left: 70px; padding-left: 20px;\">");
						out.println("<strong>Offer #"+k+"</strong> <br />");
						out.println("<br />");
						out.println("<p>Operator: "+fo1.getReferenceFlightOperator().getUsername()+"</p>");
						out.println("<br />");
						out.println("<p>From: "+du.getStringFromDate(fo1.getStartDate())+"</p>");
						out.println("<p>To: "+du.getStringFromDate(fo1.getEndDate())+"</p>");
						out.println("<p>Price: "+fo1.getPrice()+"</p>");
						out.println("<br />");
						out.println("<form action=\"/cheapvacationsClient/FlightOfferServlet\" method=\"get\">");
						out.println("<button name=\"fo_id\" style=\"width: 160px;\" type=\"submit\" value=\""+fo1.getId()+"\">Add as Departure Flight</button>");
						out.println("<input type=\"hidden\" name=\"op\" value=\"7\"/>");
						out.println("</form>");
						out.println("<form action=\"/cheapvacationsClient/FlightOfferServlet\" method=\"get\">");
						out.println("<button name=\"fo_id\" style=\"width: 160px;\" type=\"submit\" value=\""+fo1.getId()+"\">Add as Return Flight</button>");
						out.println("<input type=\"hidden\" name=\"op\" value=\"8\"/>");
						out.println("</form>");
						out.println("<br /> <br />");
						out.println("</div>");
						k++;
					}
				}else{
					out.println("<strong>No Offers FOUND</strong>");
				}
		%>
		</blockquote>

		<br />

		<div class="push"></div>

	</div>

	<div id="footer_ag">
		<jsp:include page="../footer.jsp" />
	</div>


</body>

</html>