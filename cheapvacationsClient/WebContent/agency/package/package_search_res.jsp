<%@ page import="com.cheapvacations.logic.*, com.cheapvacations.entities.*, com.cheapvacations.utility.*, java.util.*" %>
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
		TravelAgencyManagerRemote ta = (TravelAgencyManagerRemote)request.getSession().getAttribute("agencyREF");
		UserData user = (UserData)request.getSession().getAttribute("user");
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="">Package Search Results</h3>
		<br />[or go back to <a href="../agency.jsp">Agency home</a>]


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
		<% if(ta.getPackages() == null ){ %>
			<br /><strong>NO Results Found</strong>		
		<% } else if(ta.getPackages().isEmpty()) { %>
			<br /><strong>NO Results Found</strong>
		<% } else { %>		
		<br /> The following results were found: <br />
		<br />
		<blockquote>

			<div id="hotel-name"
				style="clear: both; color: orange; font-weight: bold; border-top: dotted orange 1px; margin-top: 50px;">Your Serch Criteria</div>
			<div id="hotel-details" style="float: left">
				<%  String ho_id = request.getParameter("ho_id");
					
					String fo_id = request.getParameter("fo_id");
					
					String departure = request.getParameter("pdepart");
					String arrival = request.getParameter("parrive");
					String hotelname = request.getParameter("photel");
					String str_price = request.getParameter("pmaxprice");
					String pstartdate = request.getParameter("pstartdate");
					String penddate = request.getParameter("penddate");				
					
					DateUtility du = new DateUtility();
				
					
					if( ho_id != null ) {
						if( ho_id.length() > 0 ) {
							HotelOffer ho = ta.getHotelOffer();
							
							out.print("<p><strong>Hotel Offer</strong></p>");
							out.print("<p>Name: "+ho.getReferenceHotel().getName()+"</p>");
							out.print("<p>City: "+ho.getReferenceHotel().getCity()+"</p>");
							out.print("<p>Period: "+du.getStringFromDate(ho.getStartDate())+" - "+du.getStringFromDate(ho.getEndDate())+"</p>");
							out.print("<p>Price: "+ho.getPrice()+"</p>");

						}
					}
					else if(fo_id != null) {
						if(fo_id.length() > 0) {
							FlightOffer fo = ta.getFlightOffer();

							out.print("<p><strong>Flight Offer</strong></p>");
							out.print("<p>Departure: "+fo.getReferenceFlight().getDeparture()+"</p>");
							out.print("<p>Arrival: "+fo.getReferenceFlight().getArrival()+"</p>");
							out.print("<p>Period: "+du.getStringFromDate(fo.getStartDate())+" - "+du.getStringFromDate(fo.getEndDate())+"</p>");
							out.print("<p>Price: "+fo.getPrice()+"</p>");
						}
					}
					else {
						if(departure.length()>0){ out.print("<p>Departure: "+departure+"</p>"); }
						if(arrival.length()>0){ out.print("<p>Arrival: "+arrival+"</p>"); }
						if(hotelname.length()>0){ out.print("<p>Hotel Name: "+hotelname+"</p>"); }
						if(str_price.length()>0){ out.print("<p>Max Price: "+str_price+"</p>"); }
						if(pstartdate.length()>0){ out.print("<p>Departure: "+pstartdate+"</p>"); }
						if(penddate.length()>0){ out.print("<p>Departure: "+penddate+"</p>"); }
					}
				%>
			</div>
			<%
				int k = 1;
				Iterator<VacationPackage> i = ta.getPackages().iterator();
				while(i.hasNext()){
					VacationPackage tempp = i.next();
					out.print("<div id=\"hotel-offer\" style=\"float: left; width: 150px; border-left: dotted orange 1px; margin-left: 70px; padding-left: 20px;\">");
					out.print("<strong>Package #"+k+"</strong> <br />");
					out.print("<br />");
					out.print("<p>Departure: "+tempp.getDepartFlight().getReferenceFlight().getDeparture()+"</p>");
					out.print("<p>Arrival: "+tempp.getDepartFlight().getReferenceFlight().getArrival()+"</p>");
					out.print("<p>Hotel: "+tempp.getStayHotel().getReferenceHotel().getName()+"</p>");
					out.print("<p>From: "+du.getStringFromDate(tempp.getStartDate())+"</p>");
					out.print("<p>To: "+du.getStringFromDate(tempp.getEndDate())+"</p>");
					out.print("<p>Price: "+tempp.getPrice()+"</p>");
					out.print("<br />");
					int pst = tempp.getStatus();
					String packStatus = null;
					switch (pst){
					case 2:
						packStatus = "Available";
						break;
					case 1:
						packStatus = "Booked";
						break;
					case 0:
						packStatus = "Acquired";
						break;
					case -1:
						packStatus = "Expired";
						break;
					default:
						break;
					}					
					out.print("<p>Status: "+packStatus+"</p>");
					out.print("<br />");
					out.println("<form action=\"/cheapvacationsClient/PackageServlet\" method=\"get\">");
					out.println("<button name=\"p_id\" type=\"submit\" value=\""+tempp.getId()+"\">Remove Package</button>");
					out.println("<input type=\"hidden\" name=\"op\" value=\"5\"/>");
					out.println("</form>");
					out.print("<br />");
					out.print("<br />");
					out.print("</div>");
	
	
					k++;
				} %>

				<!--  
						<form action="package_details.jsp" method="get">
							<button name="detail" type="submit" value="">See Details</button>
						</form>
					-->
		</blockquote>
		<% } %>
		<br />

		<div class="push"></div>

	</div>

	<div id="footer_ag">
		<jsp:include page="../footer.jsp" />
	</div>

</body>

</html>