<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="columbusListLogin.*" %>
<%@page import = "columbusListPostListing.PostListingInstance"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BrowseItems</title>
</head>
	<body>
	<h1>Browse Items</h1>
		<div class="navigation">
			<a href="HomePageServlet">HomePage</a>
			<a class="current" href="BrowseItemsServlet">Browse Items</a>
			<a href="PostListingServlet">Post Listing</a>
			<a href="ProfilePageServlet">My Profile</a>
			<a href = "WishlistPageServlet">Wishlist</a>
			<a href="LogoutServlet">Logout</a>
		</div>
		<div>
		<form enctype="multipart/form-data" id="filter" method="get" action="<%=request.getContextPath()%>/BrowseItemsServlet">
			<table style = "padding:10px">
			<tr>
				<th style = "padding:10px">
					<label>Listing Title/ Tags: <input name="title" type="text" value="${searchTitle}"></label><br><br>
				</th>
				<th style = "padding:10px">
					<label>Price: <input name="price" type="number" size="10" value="${searchPrice}"></label><br><br>
				</th>
				<th style = "padding:10px">
					<button id="psbutton" type="submit">Filter</button>
				</th>
			</tr>
			</table>
		</form>
		<div class = "listingsList">	
			<% ArrayList<PostListingInstance> listings = (ArrayList<PostListingInstance>)request.getAttribute("listings");%>
 			
  			<c:forEach items="${listings}" var="listing">
	  			<div class = "listing">
	  				<div class = "addToWishList">
	  				<form id="addToWishList" method="post" action="<%=request.getContextPath()%>/BrowseItemsServlet">
						<input name="listingId" type="hidden" value="${listing.getId()}" required>
						<input name="action" type="hidden" value="add" required>
		  				<button class = "addToWishListButton" type="submit" id ="${listing.getId()}add" hidden = ""><strong>&#43</strong></button>
		  			</form>
		  				<script>
						    var element = document.getElementById(${listing.getId()} + "add");
						    if (${listing.getCanAddToWishList()}){
						    	element.removeAttribute("hidden");
						    }
							</script>
		  			</div>
		  			<div class = "removeFromWishList">
		  			<form id="addToWishList" method="post" action="<%=request.getContextPath()%>/BrowseItemsServlet">
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
		  			<p><font size = "+1"><strong>${listing.getTitle()}</strong></font></p>
		  			<c:forEach items="${listing.getBase64Images()}" var="picture">
		  				<img src="data:image/jpg;base64,${picture}"/>
		  			</c:forEach>
		  			<div class = "description>">
		  				<p><strong>Description:</strong> ${listing.getDescription()} </p>
		  			</div>
		  			<div class = "tags">
		  				<p><strong>Tags</strong>: ${listing.getFormattedTags()}</p>
		  			</div>
		  			<div class = "datePosted">
		  				<p><strong>Date Posted:</strong> ${listing.getFormattedDatePosted()}</p>
		  			</div>
		  			<div class = "price">
			  			<p><strong>Price:</strong> $<fmt:formatNumber type="number" minFractionDigits="2" value="${listing.getPrice()}"/></p>
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
			  			<br>
		  			</div>
	  			</div>
		 	</c:forEach>
		</div>
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
		body {
		  overflow: overlay;
		}
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
		.updatePriceButton:hover{
			cursor:pointer;
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
			overflow: auto;
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
		body{
			background-color: SteelBlue;
			text-align: center;
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
	</style>
</html>