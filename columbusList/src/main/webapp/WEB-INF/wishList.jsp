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
<title>My Wish List</title>
</head>
	<body>
	<%String name = (String)request.getAttribute("name"); %>
	<h1 class = "nameTitle">${name}</h1>
		<div class="navigation">
			<a href="HomePageServlet">HomePage</a>
			<a href="BrowseItemsServlet">Browse Items</a>
			<a href="PostListingServlet">Post Listing</a>
			<a href="ProfilePageServlet">My Profile</a>
			<a class="current" href="WishlistPageServlet">Wishlist</a>
			<a href="LogoutServlet">Logout</a>
		</div>
		<h1 class ="listingsHeader">My Wish List:</h1>
			
		<div class = "listingsList">	
			<% ArrayList<PostListingInstance> listings = (ArrayList<PostListingInstance>)request.getAttribute("listings");%>
 			
  			<c:forEach items="${listings}" var="listing">
	  			<div class = "listing">
	  				
		  			<p><font size = "+1"><strong>${listing.getTitle()}</strong></font></p>
		  			<c:forEach items="${listing.getBase64Images()}" var="picture">
		  				<img src="data:image/jpg;base64,${picture}"/>
		  			</c:forEach>
		  			<div class = "removeFromWishList">
		  				<form id="addToWishList" method="post" action="<%=request.getContextPath()%>/WishlistPageServlet">
						<input name="listingId" type="hidden" value="${listing.getId()}" required>
						<input name="action" type="hidden" value ="remove" required>
		  				<button class = "removeFromWishListButton" id = "${listing.getId()}remove" type="submit"><strong>&#8722</strong></button>
		  			</form>
		  			</div>
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
			  			<form id="checkout" method="post" action="<%=request.getContextPath()%>/WishlistPageServlet">
							<input name="email" type="hidden" value="${listing.getTitle()}" required>
							<input name="listingId" type="hidden" value="${listing.getId()}" required>
							<input name="listingPrice" type="hidden" value="${listing.getPrice()}" required>
							<input name="action" type="hidden" value ="checkout" required>
							<button type="submit">Checkout</button>
						</form>
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
		.cancelButton, .confirmDeleteButton {
		  background-color: #ccc;
		  padding: 20px;
		  text-align: center;
		  text-decoration: none;
		  display: inline-block;
		  font-size: 12px;
		  margin: 4px 2px;
		  border-radius: 12px;
		  font-weight: bold;
		  opacity:.9;
		}
		.cancelButton:hover, .confirmDeleteButton:hover{
			opacity:1;
			cursor: pointer;
		}
		.container {
		  padding: 16px;
		  text-align: center;
		}
	
		@keyframes fadeInOpacity {
		0% {
			opacity: 0;
		}
		100% {
			opacity: 1;
		}
		}
		
		hr {
		  border: 1px solid #f1f1f1;
		  margin-bottom: 25px;
		}
		
		/* The Modal Close Button (x) */
		
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
	</style>
</html>