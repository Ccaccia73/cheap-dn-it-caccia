<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Package Details</title>

<link rel="stylesheet" type="text/css" href="../../css/theme1.css"
	title="default" />




</head>

<body>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="">Package Details</h3>
		<br />[or go back to <a href="../customer.jsp">Customer home</a>]


		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline">username</div>
			. <br />
			
		</div>

		<br />
		<br />
		<object data="../../images/package.svg" type="image/svg+xml"></object>
		<br />


		<blockquote>

			<div id="hotel-name"
				style="clear: both; color: orange; font-weight: bold; border-top: dotted orange 1px; margin-top: 50px;">Your
				Serch Criteria</div>
			<div id="hotel-details" style="float: left">
				<p>Città di part</p>
				<p>Città di arrivo</p>
				<p>Periodo</p>
				<p>Prezzo</p>



			</div>
			<div id="hotel-offer"
				style="float: left; border-left: dotted orange 1px; margin-left: 170px; padding-left: 20px;">

				<strong>Package #1</strong> <br />
				<br />
				<p>
					<label>From:</label> [start-date]
				</p>
				<p>
					<label>To:</label> [end-date]
				</p>
				<p>
					<label>Price: </label>[price]
				</p>
				<br />
				<p>
					<label>Hotel: </label>[nome hotel]
				</p>
				<p>
					<label>Stars: </label>[stelle]
				</p>
				<p>
					<label>Customer Rating: </label>[cus rating]
				</p>
				<br />
				<p>
					<label>Airline: </label>[compagnia aerea]
				</p>
				<br />
				<p>
					<label>Travel Agency: </label>[agenzia]
				</p>

				<br />
				<br />
				<form action="cart.jsp" method="get">
					<button name="detail" type="submit" value="add">Add to
						Cart</button>
				</form>

			</div>


		</blockquote>

		<br />

		<div class="push"></div>

	</div>

	<div id="footer">
		<p></p>
	</div>


</body>

</html>