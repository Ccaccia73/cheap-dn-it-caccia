<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Manage Flight OFFERS</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="display: inline;">Find Flight And See OFFERS</h3>
		<br />[or go back to <a href="../agency.jsp">Travel Agency home</a>]
		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline">username</div>
			. <br />
			
		</div>

		<br />
		<br />
		<object data="../../images/flight.svg" type="image/svg+xml"></object>
		<br /> <br />
		<br />



		<form action="flight_offer_search_res.jsp" method="get">
			<label>Flight Code: <input type="text" name="hname" /></label><br />
			<!-- <label>Address: <input type="text" name="haddress" /></label><br />-->
			<label>Airline: <input type="text" name="hcity" /></label><br /> <label>Departure:
				<input type="text" name="hcity" />
			</label><br /> <label>Arrival: <input type="text" name="hcity" /></label><br />
			<label>Time: <input type="text" name="hcity" /></label><br /><br /> <br />


			<br /> <br /> <input type="submit" value="Search Flight Offer" /> <input
				type="reset" value="Clear" />

		</form>
		<br />

		<div class="push"></div>

	</div>

	<div id="footer_ag">
		<jsp:include page="../footer.jsp" />
	</div>


</body>

</html>