<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ColumbusList Login</title>
</head>
	<body>
	<div class="center">
		<h1>ColumbusList Login</h1>
		<h2>Please Login Below</h2>
		<div class="forms">
		<form id="login" method="post" action="<%=request.getContextPath()%>/LoginServlet">
			<label>Email: <input name="email" type="email" required></label><br><br>
			<label>Password: <input name="password" type="password" required></label><br><br>
			<button type="submit">Login</button>
		</form>
		</div>
		<p>Need to create an account? <a href="AccountServlet">Click here</a></p><br><br><br><br>
		<p class="message">${message}</p>
	</div>
	</body>
	<style>
		.center {
  			width: 400px;
        	height: 400px;
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