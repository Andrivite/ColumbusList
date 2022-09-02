<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Account</title>
</head>
	<body>
	<div class="center">
		<h1>ColumbusList Account Creation</h1>
		<h2>Please Create an Account Below</h2>
		<form id="createAccount" method="post" action="<%=request.getContextPath()%>/AccountServlet">
			<label>Email: <input name="email" type="email" required></label><br><br>
			<label>Password: <input name="password" type="password" required></label><br><br>
			<label>Repeat Password: <input name="password2" type="password" required></label><br><br>
			<label>First Name: <input name="firstName" type="text"></label><br><br>
			<label>Last Name: <input name="lastName" type="text"></label><br><br>
			<button id="acButton" type="submit">Create Account</button>
		</form>
		<p>Already have an account? <a href="LoginServlet">Click here</a></p><br>
		<p class="message">${message}</p>
	</div>
	</body>
	<style>
		.center {
  			width: 450px;
        	height: 500px;
        	position: absolute;
        	background-color: CornflowerBlue;
        	left: 0;
        	right: 0;
        	top: 0;
        	bottom: 0;
        	margin: auto;
        	max-width: 100%;
        	max-height: 100%;
        	overflow: auto;
        	border-radius: 25px;
			border: 2px solid black;
			padding: 20px;
		}
		.message{
			font-weight: bold;
			color: Darkred;
		}
		label{
			margin-right: 100px;
			float: right;
		}
		body{
			background-color: SteelBlue;
			text-align: center;
		}
		h1{
			color: Black;
			font-family: helvetica;
		}
		h2{
			color: Ivory;
			font-family: helvetica;
		}
		p{
			color: Ivory;
			font-family: helvetica;
		}
		label{
			color: Ivory;
			font-family: helvetica;
		}
	</style>
</html>