<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Register</title>

<link rel="stylesheet" type="text/css" href="css/theme1.css"
	title="default" />
<link rel="alternate stylesheet" type="text/css" href="theme0.css"
	title="alt" />



</head>

<body>

	<div class="wrapper">

		<h2>Cheap Vacations</h2>
		<hr />
		<br />
		<h3>Welcome to Cheap Vacations website!</h3>
		<br />
		<br /> To become a member of our website, you need to fill in the
		following form: <br />
		<br /> <small><i> (fields marked with * are required)<br />
			<br />
		</i></small> <br />
		<%
			String p2 = (String)request.getParameter("register");
			if (p2 != null){
				if(p2.equals("fail")){
					out.println("<p ><strong>Registration Error</strong></p>");
				}
			}else{
				// out.println("<p>NULL</p>");
			}
		%>

		<form action="RegisterServlet" method="get">
			<label>First Name*: <input type="text" name="firstname" /></label><br />
			<label>Last Name*: <input type="text" name="lastname" /></label><br />
			<label>Email: <input type="text" name="email" /></label><br />
			<label>Address: <input type="text" name="address" /> </label><br />
			<label>Your category*: <br /> <select
				name="category">
					<option>- please select -</option>
					<option value="1">Operator</option>
					<option value="2">Travel Agency</option>
					<option value="3">Customers</option>

			</select>

			</label><br /> <br />
			<fieldset style="width: 400px">
				<b>Create your username and password</b>: <br />
				<br /> <label>Username*: <input type="text" name="username" /></label><br />
				<label>Password*: <input type="password" name="password" /></label><br />
				<input type="submit" value="Register" />
			</fieldset>
			<br /> <br />
		</form>
		<br />

		<div class="push"></div>

	</div>

	<div id="footer" style="width: 100%">
		<p>
			<a href="home.jsp">Home</a> | <a href="about.html">About</a>
			<!--  | <a href="contact.html">Contact Us</a> -->
		</p>
	</div>


</body>

</html>