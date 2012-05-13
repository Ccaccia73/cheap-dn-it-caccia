<%@ page import="com.cheapvacations.logic.*, com.cheapvacations.utility.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Unsubscription Page</title>

<link rel="stylesheet" type="text/css" href="css/theme1.css"
	title="default" />
<link rel="alternate stylesheet" type="text/css" href="theme0.css"
	title="alt" />



</head>

<body>
	<%
		UserData user = (UserData)request.getSession().getAttribute("user");

		OperatorManagerRemote op = null;
		TravelAgencyManagerRemote ta = null;
		CustomerManagerRemote cust = null;
		
		if(user.type == 1){
			op = (OperatorManagerRemote)request.getSession().getAttribute("operatorREF");
		}else if(user.type == 2){
			ta = (TravelAgencyManagerRemote)request.getSession().getAttribute("agencyREF");
		}else if(user.type == 3){
			cust = (CustomerManagerRemote)request.getSession().getAttribute("customerREF");
		}
	%>


	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		
		<% if(user.type >= 1 && user.type <= 3){ %>
		
		<h3>Do you really want to unsubscribe?</h3>
		<br /> <br />you will no more able to access your data: <br /> <br /> <br />

		<fieldset style="width: 400px">
			<b>Your username </b>:
			<strong><% out.print(user.name); %></strong>
			<br />
			<br />
			<form action="LogoutServlet" method="get">
				<br /> <label>Your category*: <br />
				<%
				if(user.type == 1){
					out.print("<strong>Operator</strong>");
				}
				if(user.type == 2){
					out.print("<strong>Travel Agency</strong>");
				}
				if(user.type == 3){
					out.print("<strong>Customer</strong>");
				}
				%>
				</label><br /> <br />
				<input type="hidden"  name="unsubscribe" value="true"/>
				<button type="submit">Unsubscribe</button>
			</form>
		</fieldset>
		<% }else{ %>
		<h3>This page is used to Unsubscribe</h3>
		<br /> <br />please return to <a href="home.jsp">home</a> <br /> <br /> <br />
		
		<%} %>
		<br /> <br />  <br />

		<div class="push"></div>

	</div>

	<div id="footer" style="width: 100%">
		<p>
			<%
			if(user.type == 1){
				out.print("<a href=\"operator/operator.jsp\">Operator Home</a>");
			}
			if(user.type == 2){
				out.print("<a href=\"agency/agency.jsp\">Travel Agency Home</a>");
			}
			if(user.type == 3){
				out.print("<a href=\"customer/customer.jsp\">Customer Home</a>");
			}
			%>
		</p>
	</div>


</body>

</html>