<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="columbusListLogin.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Checkout</title>
</head>
	<body>
	<h1>Checkout</h1>
		<div class="navigation">
			<a href="HomePageServlet">HomePage</a>
			<a href="BrowseItemsServlet">Browse Items</a>
			<a href="PostListingServlet">Post Listing</a>
			<a href="ProfilePageServlet">My Profile</a>
			<a href = "WishlistPageServlet">Wishlist</a>
			<a href="LogoutServlet">Logout</a>
		</div>
		<div>&nbsp;</div>
				<div class="center">
		<form id="checkoutPage" method="post" action="<%=request.getContextPath()%>/CheckoutPageServlet">
			<h1>Checkout: </h1>
			<h2>Item being sold</h2>
			<p>Title: ${listingBeingSold}</p>
			<p>Price: ${listingBeingSoldPrice}</p>
			<input id="someStuff" name="someStuff" type="hidden" value="${listingBeingSoldId}" required>
			<div class="billing">
				<p>Billing Information</p>
				<div class="names">
					<label>First Name: <input name="firstName" type="text" size="15" required></label>
					<label>&emsp; Last Name: <input name="lastName" type="text" size="15" required></label><br>
				</div>
				<br>
				<div class="address">
					<label>Address: <input id="billingAddress" name="billingAddress" type="text" size="25" required></label>
					<label> &emsp; City: <input name="city" type="text" required></label><br><br>
					<label for="states">&emsp; State:</label>
						<select name = "states" id="states"> 
						<option value="none" selected disabled hidden>Select State</option>
						<option value="AL">Alabama</option>
						<option value="AK">Alaska</option>
						<option value="AZ">Arizona</option>
						<option value="AR">Arkansas</option>
						<option value="CA">California</option>
						<option value="CO">Colorado</option>
						<option value="CT">Connecticut</option>
						<option value="DE">Delaware</option>
						<option value="DC">District Of Columbia</option>
						<option value="FL">Florida</option>
						<option value="GA">Georgia</option>
						<option value="HI">Hawaii</option>
						<option value="ID">Idaho</option>
						<option value="IL">Illinois</option>
						<option value="IN">Indiana</option>
						<option value="IA">Iowa</option>
						<option value="KS">Kansas</option>
						<option value="KY">Kentucky</option>
						<option value="LA">Louisiana</option>
						<option value="ME">Maine</option>
						<option value="MD">Maryland</option>
						<option value="MA">Massachusetts</option>
						<option value="MI">Michigan</option>
						<option value="MN">Minnesota</option>
						<option value="MS">Mississippi</option>
						<option value="MO">Missouri</option>
						<option value="MT">Montana</option>
						<option value="NE">Nebraska</option>
						<option value="NV">Nevada</option>
						<option value="NH">New Hampshire</option>
						<option value="NJ">New Jersey</option>
						<option value="NM">New Mexico</option>
						<option value="NY">New York</option>
						<option value="NC">North Carolina</option>
						<option value="ND">North Dakota</option>
						<option value="OH">Ohio</option>
						<option value="OK">Oklahoma</option>
						<option value="OR">Oregon</option>
						<option value="PA">Pennsylvania</option>
						<option value="RI">Rhode Island</option>
						<option value="SC">South Carolina</option>
						<option value="SD">South Dakota</option>
						<option value="TN">Tennessee</option>
						<option value="TX">Texas</option>
						<option value="UT">Utah</option>
						<option value="VT">Vermont</option>
						<option value="VA">Virginia</option>
						<option value="WA">Washington</option>
						<option value="WV">West Virginia</option>
						<option value="WI">Wisconsin</option>
						<option value="WY">Wyoming</option>
					</select>				
					<label for ="zip">&emsp; ZIP Code: </label>
					<input name="zipCode" type="tel" inputmode="numeric" size="6" required>
				</div>
				<br>
				<div class="cardInfo">
					<label for="ccn">Credit Card Number:</label>
					<input id="ccn" type="tel" inputmode="numeric" pattern="[0-9\s]{13,19}" autocomplete="cc-number" maxlength="19">
					<label for="ccpin">&emsp; PIN:</label>
					<input id="ccpin" size="4" type="password" inputmode="numeric"  autocomplete="cc-number" maxlength="4">
				</div>
			</div>
			<br><br>
			<div class="shipping">
				<p>Shipping Information</p>
				<div class="company">
					<label>Company: <input type="text" size="18" placeholder="optional"></label>
				</div>
				<br>
				<div class="names">
					<label>First Name: <input name="firstName" type="text" size="15" required></label>
					<label>&emsp; Last Name: <input name="lastName" type="text" size="15" required></label><br>
				</div>
				<br>
				<div class="address">
					<label>Address: <input name="billingAddress" type="text" size="25" required></label>
					<label> &emsp; City: <input name="city" type="text" required></label><br><br>
					<label for="states">&emsp; State:</label>
						<select name = "states" id="states"> 
						<option value="none" selected disabled hidden>Select State</option>
						<option value="AL">Alabama</option>
						<option value="AK">Alaska</option>
						<option value="AZ">Arizona</option>
						<option value="AR">Arkansas</option>
						<option value="CA">California</option>
						<option value="CO">Colorado</option>
						<option value="CT">Connecticut</option>
						<option value="DE">Delaware</option>
						<option value="DC">District Of Columbia</option>
						<option value="FL">Florida</option>
						<option value="GA">Georgia</option>
						<option value="HI">Hawaii</option>
						<option value="ID">Idaho</option>
						<option value="IL">Illinois</option>
						<option value="IN">Indiana</option>
						<option value="IA">Iowa</option>
						<option value="KS">Kansas</option>
						<option value="KY">Kentucky</option>
						<option value="LA">Louisiana</option>
						<option value="ME">Maine</option>
						<option value="MD">Maryland</option>
						<option value="MA">Massachusetts</option>
						<option value="MI">Michigan</option>
						<option value="MN">Minnesota</option>
						<option value="MS">Mississippi</option>
						<option value="MO">Missouri</option>
						<option value="MT">Montana</option>
						<option value="NE">Nebraska</option>
						<option value="NV">Nevada</option>
						<option value="NH">New Hampshire</option>
						<option value="NJ">New Jersey</option>
						<option value="NM">New Mexico</option>
						<option value="NY">New York</option>
						<option value="NC">North Carolina</option>
						<option value="ND">North Dakota</option>
						<option value="OH">Ohio</option>
						<option value="OK">Oklahoma</option>
						<option value="OR">Oregon</option>
						<option value="PA">Pennsylvania</option>
						<option value="RI">Rhode Island</option>
						<option value="SC">South Carolina</option>
						<option value="SD">South Dakota</option>
						<option value="TN">Tennessee</option>
						<option value="TX">Texas</option>
						<option value="UT">Utah</option>
						<option value="VT">Vermont</option>
						<option value="VA">Virginia</option>
						<option value="WA">Washington</option>
						<option value="WV">West Virginia</option>
						<option value="WI">Wisconsin</option>
						<option value="WY">Wyoming</option>
					</select>				
					<label for ="zip">&emsp; ZIP Code: </label>
					<input name="zipCode" type="tel" inputmode="numeric" size="6" required>
				</div>
			</div>
			<br><br>
			
			
			<button class="button" type="submit">Complete Checkout</button>

<!-- 			button needs to
			1. say "checkout success" or smth
			2. delete the listing you're trying to buy
			3. delete all input boxes? -->
			
			
		</form>
		<br>
  		</div>
  		<div>&nbsp;</div>
  		<div>&nbsp;</div>
	</body>
	<footer>
		<div class="bottomBar">
				<a href="#">About</a>
				<a href="#">FAQ</a>
				<a href="#">Policies</a>
		</div><br>
	</footer>
	<style>
	::-webkit-scrollbar {
		  width: 10px;
		  height: 10px;
		}
		
		::-webkit-scrollbar-thumb {
		  background: rgba(90, 90, 90);
		}
		
		::-webkit-scrollbar-track {
 			 background: rgba(0, 0, 0, 0.2);
		}
	body{
			background-color: SteelBlue;
			text-align: center;
		}
	.center{
  			width: 800px;
        	height: 900px;
        	background-color: CornflowerBlue;
        	justify-content: center;
        	margin: auto;
        	max-width: 100%;
        	max-height: 100%;
        	overflow: auto;
        	border-radius: 25px;
 			border: 2px solid black;
			padding: 20px;
		}
		.navigation{
			overflow: hidden;
			background-color: CornflowerBlue;
			padding: 16px 16px;
			border: 2px solid black;
		}
		label{
			color: Ivory;
			font-family: helvetica;
		}
		p{
			color: Black;
			font-family: helvetica;
			font-weight: bold;
		}
		.bottomBar{
			overflow: hidden;
			background-color: CornflowerBlue;
			padding: 16px 16px;
			border: 2px solid black;
			bottom: 0;
			position: fixed;
		}
		a.current{
			background-color: SteelBlue;
		}
		h1{
			color: Black;
			font-family: helvetica;
		}
		a{
			color: Ivory;
			font-family: helvetica;
			text-align: center;
			padding: 20px 16px;
		}
		.button{
		  background-color: gold;
		  border: none;
		  color: black;
		  padding: 15px 32px;
		  text-align: center;
		  display: inline-block;
		  font-size: 16px;
		  border: 1px solid black;
		  
		}
	</style>
</html>