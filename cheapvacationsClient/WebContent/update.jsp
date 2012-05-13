<%@ page import="com.cheapvacations.logic.*, com.cheapvacations.utility.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Update your data</title>

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
		
		<h3>Update your Personal Data</h3>
		<br /> <br />you can change the fields below: <br /> <br /> <br />

		<fieldset style="width: 400px">
			<b>Your username </b>:
			<strong><% out.print(user.name); %></strong>
			<br />
			<br />
			<form action="UpdateServlet" method="get">
				<label>First Name*: <input type="text" name="firstname" value="<%
				if(user.type == 1){
					out.print(op.getOperator().getFirstName());
				}
				if(user.type == 2){
					out.print(ta.getTravelAgency().getFirstName());
				}
				if(user.type == 3){
					out.print(cust.getCustomer().getFirstName());
				}
				%>" />
				</label><br />
				<label>Last Name*: <input type="text" name="lastname" value="<%
				if(user.type == 1){
					out.print(op.getOperator().getLastName());
				}
				if(user.type == 2){
					out.print(ta.getTravelAgency().getLastName());
				}
				if(user.type == 3){
					out.print(cust.getCustomer().getLastName());
				}
				%>" />
				</label><br />
				<label>Email: <input type="text" name="email" value="<%
				if(user.type == 1){
					out.print(op.getOperator().getEmail());
				}
				if(user.type == 2){
					out.print(ta.getTravelAgency().getEmail());
				}
				if(user.type == 3){
					out.print(cust.getCustomer().getEmail());
				}
				%>" /></label><br />
				<label>Address: <input type="text" name="address" value="<%
				if(user.type == 1){
					out.print(op.getOperator().getAddress());
				}
				if(user.type == 2){
					out.print(ta.getTravelAgency().getAddress());
				}
				if(user.type == 3){
					out.print(cust.getCustomer().getAddress());
				}
				
				%>" />
				</label><br /> <label>Your category*: <br />
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
				<input type="submit" value="Update" />
			</form>
		</fieldset>
		<% }else{ %>
		<h3>This page is used to update User personal data</h3>
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