<%@ page import="com.cheapvacations.utility.* ,com.cheapvacations.entities.* ,com.cheapvacations.logic.* ,java.util.*, java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Make new Package OFFERS</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />

<link href="../../css/CalendarControl.css" rel="stylesheet"
	type="text/css" />
<script src="../../js/CalendarControl.js" language="javascript" type=""></script>



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
		<h3 style="display: inline;">Make A New Package</h3>
		<br />[or go back to <a href="../agency.jsp">Agency home</a>]
		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline"><% out.print(user.name); %></div>. <br />
			
		</div>

		<br />
		<br />
		<object data="../../images/package.svg" type="image/svg+xml"></object>
		<br /> <br />
		<br />
		<%	
			boolean newp = true;
			String insert_str = request.getParameter("insert");
			if( insert_str != null){
				if(insert_str.equals("fail")){
					out.print("<p><strong>Insert Failed</strong></p>");
					newp = false;
				}
			}
		%>
		
		<% if(ta.getPackageDepart() == null || ta.getPackageReturn() == null || ta.getPackageHotel() == null) {
			out.print("<p><strong>Function not yet implemented</strong></p>");
			out.print("<p>You must select ALL the package components first</p>");		
		} else {
				DateUtility du = new DateUtility();
				if( !ta.getPackageDepart().getReferenceFlight().getDeparture().equals(ta.getPackageReturn().getReferenceFlight().getArrival()) ){
					// test on departure
					out.print("<p><strong>Your flight selections don't match</strong></p>");
					out.print("<p>Different departure.</p>");
					out.print("<p>Please select again.</p>");
				}else if(! ta.getPackageDepart().getReferenceFlight().getArrival().equals(ta.getPackageReturn().getReferenceFlight().getDeparture()) ){
					// test on arrival
					out.print("<p><strong>Your flight selections don't match</strong></p>");
					out.print("<p>Different arrival.</p>");
					out.print("<p>Please select again.</p>");
				}else if(!ta.getPackageDepart().getReferenceFlight().getArrival().equals(ta.getPackageHotel().getReferenceHotel().getCity()) ){
					// test on Stay
					out.print("<p><strong>Your flight and hotel selections don't match</strong></p>");
					out.print("<p>Different Hotel location.</p>");
					out.print("<p>Please select again.</p>");
				}else if(du.isNotIntersecting(ta.getPackageDepart().getStartDate(), ta.getPackageDepart().getEndDate(), ta.getPackageHotel().getStartDate(), ta.getPackageHotel().getEndDate())){
					// test on Period start and hotel
					out.print("<p><strong>Your flight and hotel selections don't match</strong></p>");
					out.print("<p>Departure and Stay periods NOT compatible.</p>");
					out.print("<p>Please select again.</p>");
				}else if((du.isNotIntersecting(ta.getPackageReturn().getStartDate(), ta.getPackageReturn().getEndDate(), ta.getPackageHotel().getStartDate(), ta.getPackageHotel().getEndDate()))){
					// test on Period return and hotel
					out.print("<p><strong>Your flight and hotel selections don't match</strong></p>");
					out.print("<p>Departure and Stay periods NOT compatible.</p>");
					out.print("<p>Please select again.</p>");
				}else{
					if(newp){
						out.print("<p><strong>Your selections match!</strong></p>");
					}
					out.print("<p>Please fill the following form to complete the PACKAGE</p>");
					out.print("<form action=\"/cheapvacationsClient/PackageServlet\" method=\"get\">");
					out.print("<p>Departure City: "+ta.getPackageDepart().getReferenceFlight().getDeparture()+"</p>");
					out.print("<p>Arrival City: "+ta.getPackageDepart().getReferenceFlight().getArrival()+"</p>");
					out.print("<p>Hotel Name: "+ta.getPackageHotel().getReferenceHotel().getName()+"</p>");
					out.print("<label>Markup [%]: <input type=\"text\" name=\"pmarkup\"/></label><br/><br/>");
					out.print("<label>Start date: <input type=\"text\" name=\"pstartd\" onfocus=\"showCalendarControl(this);\" /></label><br/>");
					Date d1 = du.getIntersectingPeriodStart(ta.getPackageDepart().getStartDate(), ta.getPackageHotel().getStartDate());
					Date d2 = du.getIntersectingPeriodEnd(ta.getPackageDepart().getEndDate(), ta.getPackageHotel().getEndDate());
					out.print("<p>min: "+du.getStringFromDate(d1)+" max: "+du.getStringFromDate(d2)+"</p><br/>");
					out.print("<label>End date: <input type=\"text\" name=\"pendd\" onfocus=\"showCalendarControl(this);\" /></label><br/>");
					d1 = du.getIntersectingPeriodStart(ta.getPackageReturn().getStartDate(), ta.getPackageHotel().getStartDate());
					d2 = du.getIntersectingPeriodEnd(ta.getPackageReturn().getEndDate(), ta.getPackageHotel().getEndDate());
					out.print("<p>min: "+du.getStringFromDate(d1)+" max: "+du.getStringFromDate(d2)+"</p>");
					out.print("<input type=\"hidden\" name=\"op\" value=\"1\">");
					out.print("<br /> <br />");
					out.print("<input type=\"submit\" value=\"Build Package\" />");
					out.print("<input type=\"reset\" value=\"Clear\" />");
					out.print("</form>");
				}
			}
		%>
		<br />

		<div class="push"></div>

	</div>

	<div id="footer_ag">
		<jsp:include page="../footer.jsp" />
	</div>


</body>

</html>