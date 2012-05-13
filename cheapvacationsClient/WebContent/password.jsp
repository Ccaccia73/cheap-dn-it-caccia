<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Lazy Password Recovery</title>

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
		<%
			String f = request.getParameter("recovery");
			if(f != null){
				if(f.equals("fail")){
					out.println("<p><strong>Failed to recover password</strong></p>");
				}
			}
		%>
		
		<br />
		<br /> To retrieve your password, please provide the following
		information: <br />
		<br /> <br />
		<fieldset style="width: 400px">
			<form action="PasswordServlet" method="get">

				<!-- <label>Email: <input type="text" name="email" /></label><br /> or<br /> -->
				<br /> <label>Username: <input type="text" name="username" /></label>
				<br />
				<input type="submit" value="Submit" />
				<input 	type="reset" value="Clear" />
				<br />
			</form>
			<%
				String pw = request.getParameter("password");
				
				if(pw != null){
					out.println("<p><strong>Your password: "+pw+"</strong></p>");
				}
			%>
		</fieldset>

		<br />
		<br />
		<br />

		<div class="push"></div>

	</div>

	<div id="footer" style="width: 100%">
		<p>
			<a href="home.jsp">Home</a> | <a href="about.html">About</a> 
			<!-- | <a href="contact.html">Contact Us</a> -->
		</p>
	</div>

</body>

</html>