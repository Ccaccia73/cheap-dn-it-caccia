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

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3 style="">Package Search Results</h3>
		<br />[or go back to <a href="../agency.jsp">Agency home</a>]


		<div id="loggedin"
			style="float: right; font-size: 80%; color: grey; margin-right: 50px; text-align: right;">

			You are logged in as:
			<div style="font-weight: bold; color: orange; display: inline">username</div>
			. <br />
			
		</div>

		<br />
		<br />
		<object data="../../images/package.svg" type="image/svg+xml"></object>
		<br /> <br />
		<br /> The following results were found: <br />
		<br />
		<blockquote>

			<div id="hotel-name"
				style="clear: both; color: orange; font-weight: bold; border-top: dotted orange 1px; margin-top: 50px;">Your
				Package Criteria</div>
			<div id="hotel-details" style="float: left">
				<p>Città di part</p>
				<p>Città di arrivo</p>
				<p>Periodo</p>
				<p>Prezzo</p>



			</div>
			<div id="hotel-offer"
				style="float: left; width: 150px; border-left: dotted orange 1px; margin-left: 70px; padding-left: 20px;">
				<form action="" method="get">
					<strong>Package #1</strong> <br />
					<br />
					<p>From: [start-date]</p>
					<p>To: [end-date]</p>
					<p>Price: [price]</p>
					<br /> <br />
					<br />
					<button name="detail" type="submit" value="insert">Insert
						Package</button>
				</form>

			</div>


			<!-- generati automaticam-->
			<div id="hotel-offer"
				style="float: left; width: 150px; border-left: dotted orange 1px; margin-left: 70px; padding-left: 20px;">
				<form action="" method="get">
					<strong>Offer #2</strong> <br />
					<br />
					<p>From: [start-date]</p>
					<p>To: [end-date]</p>
					<p>Price: [price]</p>
					<br /> <br />
					<br />
					<button name="detail" type="submit" value="insert">Insert
						Package</button>
				</form>

			</div>


			<div id="hotel-offer"
				style="float: left; width: 150px; border-left: dotted orange 1px; margin-left: 70px; padding-left: 20px;">
				<form action="" method="get">
					<strong>Offer #3</strong> <br />
					<br />
					<p>From: [start-date]</p>
					<p>To: [end-date]</p>
					<p>Price: [price]</p>
					<br /> <br />
					<br />
					<button name="detail" type="submit" value="insert">Insert
						Package</button>
				</form>

			</div>


		</blockquote>

		<br />

		<div class="push"></div>

	</div>

	<div id="footer_ag">
		<jsp:include page="../footer.jsp" />
	</div>


</body>

</html>