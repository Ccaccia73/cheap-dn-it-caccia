<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hotel Offer Search Results</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>
	<%
		UserData user = (UserData)request.getSession().getAttribute("user");
		List<HotelOffer> hol = (List<HotelOffer>)request.getSession().getAttribute("hoteloffer");
		
	%>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="">Manage Hotel OFFERS</h3>
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
		<% 
			if(hol != null && hol.size() > 0){
				Iterator<HotelOffer> i = hol.iterator();
				int k = 1;
				DateUtility du = new DateUtility();
					Hotel h1 = hol.get(0).getReferenceHotel();
					out.println("<div id=\"hotel-name\" style=\"clear: both; color: orange; font-weight: bold; border-top: dotted orange 1px; margin-top: 50px;\">");
					out.println(h1.getName());
					out.println("</div>");
					out.println("<div id=\"hotel-details\" style=\"float: left\">");
					out.println("<p>"+h1.getAddress()+"</p>");
					out.println("<p>"+h1.getCity()+"</p>");
					out.println("</div>");
					
					while(i.hasNext()){
						HotelOffer ho1 = i.next();
						out.println("<div id=\"hotel-offer\" style=\"float: left; width: 150px; border-left: dotted orange 1px; margin-left: 70px; padding-left: 20px;\">");
						out.println("<strong>Offer #"+k+"</strong> <br />");
						out.println("<br />");
						out.println("<p>From: "+du.getStringFromDate(ho1.getStartDate())+"</p>");
						out.println("<p>To: "+du.getStringFromDate(ho1.getEndDate())+"</p>");
						out.println("<p>Price: "+ho1.getPrice()+"</p>");
						out.println("<br />");
						out.println("<form action=\"/cheapvacationsClient/HotelOfferServlet\" method=\"get\">");
						out.println("<button name=\"ho_id\" type=\"submit\" value=\""+ho1.getId()+"\">Remove Offer</button>");
						out.println("<input type=\"hidden\" name=\"op\" value=\"4\"/>");
						out.println("</form>");
						out.println("<br /> <br />");
						out.println("<form action=\"/cheapvacationsClient/PackageServlet\" method=\"get\">");
						out.println("<button name=\"ho_id\" type=\"submit\" value=\""+ho1.getId()+"\">See Related Packages</button>");
						out.println("<input type=\"hidden\" name=\"op\" value=\"2\"/>");
						out.println("</form>");
						out.println("</div>");
						k++;
					}
				}else{
					out.println("<strong>No Offers FOUND</strong>");
				}
			%>
		
				<!--	
				<form action="/cheapvacationsClient/HotelOfferServlet" method="get">
					<button name="ho_id" type="submit" value="">Edit Details</button>
				</form>
				  -->
		</blockquote>

		<br />

		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>