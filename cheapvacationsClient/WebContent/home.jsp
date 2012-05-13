<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login Page</title>

<link rel="stylesheet" type="text/css" href="css/theme1.css"
	title="default" />
<link rel="alternate stylesheet" type="text/css" href="theme0.css"
	title="alt" />

</head>

<body>
	<div class="wrapper">
		<div class="header">
			<h2>Cheap Vacations</h2>
			<hr>
			<br />
			<h3>Welcome to Cheap Vacations website!</h3>
			<br />
			<br />
		</div>
		<%
			String p1 = request.getParameter("logout");
			if (p1 != null){
				out.println("<p><strong>"+p1+": you logged out</strong></p>");
			}
		%>
		<br />
		<br />
		<br />
		<div id="login">
			<strong>If you are a returning user, please login in:</strong> <br />
			<br />
			<br />
			<fieldset>
				<form action="LoginServlet" method="get">
					<label>Username: <input type="text" name="username" value="" /></label><br />
					<label>Password: <input type="password" name="password"
						value="" /></label><br /> <br /> <input type="submit" value="OK" />
				</form>
				<br />
				<small> <a href="registration.jsp">Not registered
						yet?</a> <br /> <a href="password.jsp">Forgot your password?</a>
				</small>
				<%
					String p2 = (String)request.getParameter("login");
					if (p2 != null){
						if(p2.equals("fail")){
							out.println("<p ><strong>Login Error</strong></p>");
						}
					}else{
						// out.println("<p>NULL</p>");
					}
				%>
			</fieldset>
		</div>
		<div id="text" style="width: 1200px;">
			<p>CheapVacations is the place where Tour Operators, Agencies and
				Cusotmers meet to build and find wonderful and cheap Vacation
				Packages</p>
			<p>
				<a href="guest.jsp">Take a Tour</a>
			</p>
		</div>
	</div>
	<div class="push"></div>
	<div id="footer" style="width: 100%">
		<p>
			<a href="home.jsp">Home</a> | <a href="about.html">About</a>
			<!--  | <a href="contact.html">Contact Us</a> | -->
		</p>
	</div>
</body>
</html>