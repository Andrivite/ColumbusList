<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="columbusListLogin.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import = "columbusListPostListing.PostListingInstance"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HomePage</title>
</head>
	<body>
	<% ArrayList<PostListingInstance> notificationListings = (ArrayList<PostListingInstance>)request.getAttribute("notificationListings");%>
 			
	
	<h1>ColumbusList HomePage</h1>
		<div class="navigation">
			<a class="current" href="HomePageServlet">HomePage</a>
			<a href="BrowseItemsServlet">Browse Items</a>
			<a href="PostListingServlet">Post Listing</a>
			<a href="ProfilePageServlet">My Profile</a>
			<a href = "WishlistPageServlet">Wishlist</a>
			<a href="LogoutServlet">Logout</a>
		</div>
		<br>
		<div class = "notificationBox">
		<p><font size = "+3"><strong>Notifications:</strong></font></p>
		<c:forEach items="${notificationListings}" var="listing">
			<div class = "notification">
				<form id = "removeFromNotificationList" method = "post" action = "<%=request.getContextPath()%>/HomePageServlet">
				<input name = "listingIdToRemoveFromNotification" id = "listingIdToRemoveFromNotification" type = "hidden" value = "${listing.getId()}" required>
				<input name = "action" type = "hidden" value = "removeFromNotifcationList" required>
				<p>Price has been changed for listing in your wishlist: <span style="color:black;"><strong>${listing.getTitle()}</strong></span><button class = "removeFromNotificationListButton" id = "removeFromNotificationListButton" type="submit">&#8722</button></p>
				</form>
				
			</div>
		</c:forEach>
		</div>	
		<br>
		<h1 class ="listingsHeader">Recently Added Listings</h1>
		<div class = "listingsList">	
			<% ArrayList<PostListingInstance> listings = (ArrayList<PostListingInstance>)request.getAttribute("listings");%>
 			
  			<c:forEach items="${listings}" var="listing">
	  			<div class = "listing">
	  				<div class = "addToWishList">
		  				<form id="addToWishList" method="post" action="<%=request.getContextPath()%>/HomePageServlet">
						<input name="listingId" type="hidden" value="${listing.getId()}" required>
						<input name="action" type="hidden" value="add" required>
		  				<button class = "addToWishListButton" id = "${listing.getId()}add"type="submit" hidden = ""><strong>&#43</strong></button>
		  			</form>
		  				<script>
						    var element = document.getElementById(${listing.getId()} + "add");
						    if (${listing.getCanAddToWishList()}){
						    	element.removeAttribute("hidden");
						    }
							</script>
		  			</div>
		  			
		  			<div class = "removeFromWishList">
		  				<form id="addToWishList" method="post" action="<%=request.getContextPath()%>/HomePageServlet">
						<input name="listingId" type="hidden" value="${listing.getId()}" required>
						<input name="action" type="hidden" value="remove" required>
		  				<button class = "removeFromWishListButton" id = "${listing.getId()}remove" type="submit" hidden = ""><strong>&#8722</strong></button>
		  			</form>
		  				<script>
						    var element = document.getElementById(${listing.getId()} + "remove");
						    if (${listing.getCanRemoveFromWishList()}){
						    	element.removeAttribute("hidden");
						    }
							</script>
		  			</div>
		  			
		  			
		  			<p><font size = "+3"><strong>${listing.getTitle()}</strong></font></p>
		  			<c:forEach items="${listing.getBase64Images()}" var="picture">
		  				<img src="data:image/jpg;base64,${picture}"/>
		  			</c:forEach>
		  			<div class = "description>">
		  				<p><strong>Description:</strong> ${listing.getDescription()} </p>
		  			</div>
		  			<div class = "datePosted">
		  				<p><strong>Date Posted:</strong> ${listing.getFormattedDatePosted()}</p>
		  			</div>
		  			<div class = "price">
			  			<p><strong>Price:</strong> $<fmt:formatNumber type="number" maxFractionDigits = "2" minFractionDigits="2" value="${listing.getPrice()}"/></p>
		  				<form id="login" method="post" action="<%=request.getContextPath()%>/HomePageServlet">
							<input name="email" type="hidden" value="${listing.getTitle()}" required>
							<input name="listingId" type="hidden" value="${listing.getId()}" required>
							<input name="listingPrice" type="hidden" value="${listing.getPrice()}" required>
							<input name="action" type="hidden" value="checkout" required>
							<button type="submit" id = "${listing.getId()}checkout" hidden = "">Checkout</button>
						</form>
						<script>
							    var element = document.getElementById(${listing.getId()} + "checkout");
							    if (!${listing.getIsUserListing()}){
							    	element.removeAttribute("hidden");
							    }
							</script>
		  			</div>
	  			</div>
		 	</c:forEach>
		</div>
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
		.nameTitle{
		  opacity: 1;
		  animation-name: fadeInOpacity;
		  animation-iteration-count: 1;
		  animation-timing-function: ease-in;
	      animation-duration: 1s; 
		}
		.navigation{
			overflow: hidden;
			background-color: CornflowerBlue;
			padding: 16px 16px;
			border: 2px solid black;
		}
		.listingsHeader{
			display: inline-block;
			padding: 10px 10px;
			opacity: 1;
		    animation-name: fadeInOpacity;
		    animation-iteration-count: 1;
		    animation-timing-function: ease-in;
	        animation-duration: 1s; 
		}
		img {
		  width:40%;
		  height: auto;
		}
		.container {
		  padding: 16px;
		  text-align: center;
		}
		.modal {
		  display: none; /* Hidden by default */
		  position: fixed; /* Stay in place */
		  z-index: 1; /* Sit on top */
		  left: 0;
		  top: 0;
		  width: 100%; /* Full width */
		  height: 100%; /* Full height */
		  overflow: auto; /* Enable scroll if needed */
		  background-color: transparent;
		  backdrop-filter: blur(5px);
		  padding-top: 50px;
		  opacity: 1;
		  animation-name: fadeInOpacity;
		  animation-iteration-count: 1;
		  animation-timing-function: ease-in;
	      animation-duration: 0.1s; 
		}
		@keyframes fadeInOpacity {
		0% {
			opacity: 0;
		}
		100% {
			opacity: 1;
		}
		}
		.modal-content {
		  background-color: #8B0000;
		  margin: 10% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
		  border: 2px solid black;
		  width: 30%; /* Could be more or less, depending on screen size */
		  border: 2px solid black;
		  border-radius: 25px;
		}
		
		hr {
		  border: 1px solid #f1f1f1;
		  margin-bottom: 25px;
		}
		
		/* The Modal Close Button (x) */
		.close {
		  position: absolute;
		  right: 35px;
		  top: 15px;
		  font-size: 40px;
		  font-weight: bold;
		  color: #f1f1f1;
		}
		
		.close:hover,
		.close:focus {
		  color: #f44336;
		  cursor: pointer;
		}
		
		/* Clear floats */
		.clearfix::after {
		  content: "";
		  clear: both;
		  display: table;
		}
		
		.listingsList{
			display:flex;
			flex-direction:row;
			gap:10px;
			flex-wrap : wrap;
			justify-content: center;
			margin-bottom: 45px;
		}
		.listing{
			background-color: #D3D3D3;
			overflow: hidden;
			border: 2px solid black;
			border-radius: 25px;
			padding: 5px 5px;
			display: inline-block;
			text-align: center;
			width: 23%;
			height: 10%;
			position: relative;
			opacity: 1;
		    animation-name: fadeInOpacity;
		    animation-iteration-count: 1;
		    animation-timing-function: ease-in;
	        animation-duration: 2s; 
		}
		footer {
		    display:flex;
			flex-direction:row;
			gap:10px;
			flex-wrap : wrap;
			justify-content: left;
		}
		.bottomBar{
			overflow: hidden;
			background-color: CornflowerBlue;
			padding: 16px 16px;
			border: 2px solid black;
			bottom: 0;
			position: fixed;
			
		}
		.notificationBox{
			overflow: auto;
			background-color: CornflowerBlue;
			display: inline-block;
			border: 2px solid black;
			border-radius: 25px;
			width: 450px;
			max-height: 200px;
			text-align: center;
			position: relative;
			opacity: 1;
		    animation-name: fadeInOpacity;
		    animation-iteration-count: 1;
		    animation-timing-function: ease-in;
	        animation-duration: 2s; 
			
		}
		.notification{
			color: white;
		}
		
		a.current{
			background-color: SteelBlue;
		}
		body{
			background-color: SteelBlue;
			text-align: center;
			overflow: overlay;
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
		.addToWishList{
			position: absolute;
			top: 2px;
			left: 3px;
			outline: none;
			border: none;
		}
		.addToWishListButton{
			font-size: 220%;
    		position: absolute;
    		background-color: transparent;
    		color: #006400;
			outline: none;
			border: none;
		}
		.addToWishListButton:hover{
			color: black;
			cursor: pointer;
		}
		.removeFromWishList{
			position: absolute;
			top: 2px;
			right: 35px;
			outline: none;
			border: none;
		}
		.removeFromWishListButton{
			font-size: 200%;
    		position: absolute;
    		background-color: transparent;
    		color: #8B0000;
			outline: none;
			border: none;
		}
		.removeFromWishListButton:hover{
			color: black;
			cursor: pointer;
		}
		.removeFromNotificationListButton{
			font-size: 100%;
    		position: absolute;
    		background-color: transparent;
    		color: #8B0000;
			outline: none;
			border: none;
		}
		.removeFromNotificationListButton:hover{
			color: black;
			cursor: pointer;
		}
		
		
	</style>
</html>