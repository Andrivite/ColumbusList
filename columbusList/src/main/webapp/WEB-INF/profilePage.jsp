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
<title>Profile Page</title>
</head>
	<body>
	<%String name = (String)request.getAttribute("name"); %>
	<h1 class = "nameTitle">${name}</h1>
		<div class="navigation">
			<a href="HomePageServlet">HomePage</a>
			<a href="BrowseItemsServlet">Browse Items</a>
			<a href="PostListingServlet">Post Listing</a>
			<a class="current" href="ProfilePageServlet">My Profile</a>
			<a href = "WishlistPageServlet">Wishlist</a>
			<a href="LogoutServlet">Logout</a>
		</div>
		<h1 class ="listingsHeader">My Listings:</h1>
			
		<div class = "listingsList">	
			<% ArrayList<PostListingInstance> listings = (ArrayList<PostListingInstance>)request.getAttribute("listings");%>
 			
  			<c:forEach items="${listings}" var="listing">
	  			<div class = "listing">
	  				
		  			<p><font size = "+1"><strong>${listing.getTitle()}</strong></font></p>
		  			<c:forEach items="${listing.getBase64Images()}" var="picture">
		  				<img src="data:image/jpg;base64,${picture}"/>
		  			</c:forEach>
		  			<div class = "deleteButton">
		  				<button class = "deleteButton" onclick = "document.getElementById('${listing.getId()}').style.display='block'"><strong>&#x2718</strong></button>
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
		  				<p><strong>Price:</strong> $<fmt:formatNumber type="number" maxFractionDigits = "2" minFractionDigits="2" value="${listing.getPrice()}"/></p>
			  			<form id="updatePrice" method="post" action="<%=request.getContextPath()%>/ProfilePageServlet">
							<input name="listingId" type="hidden" value="${listing.getId()}" required>
							<input name="price" type="number" step="0.01" style="width: 50px;" min="0" required>
							<input name="action" type="hidden" value="post" required>
							<button class = "updatePriceButton" type="submit"><small><strong>Change Price</strong></small></button>
						</form>
		  			</div>
	  			</div>
	  			<div id= "${listing.getId()}" class="modal">
				  <span onclick="document.getElementById('idDeleteConfirmation').style.display='none'" class="close" title="Close Modal"></span>
				  <div class="modal-content">
				    <div class="container">
				      <h1>Delete Listing</h1>
				      <p style="color:white"><strong><font size = "+1">Are you sure you want to delete this listing?</font></strong></p>
				        <form class = "clearfix" id= "deleteForm" method="post" action="<%=request.getContextPath()%>/ProfilePageServlet">
							<button type="button"  onsubmit="return false;" onclick="document.getElementById('${listing.getId()}').style.display='none'" class="cancelButton">Cancel</button>
							<input name="listingId" type="hidden" value="${listing.getId()}" required>
							<input name="action" type="hidden" value="delete" required>
							<button class = "confirmDeleteButton" type="submit" id = "deleteButtonID"><strong>Delete</strong></button>
						</form>
						<script>
						
						</script>
				    </div>
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
		.deleteButton{
    		position: absolute;
    		font-size: 120%;
    		background-color: transparent;
    		color: #8B0000;
			top: 2px;
			right: 3px;
			outline: none;
			border: none;
		}
		.updatePriceButton:hover{
			cursor:pointer;
		}
		.deleteButton:hover{
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
		  opacity:.95;
		}
		.cancelButton:hover, .confirmDeleteButton:hover{
			opacity:1;
			cursor: pointer;
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
	</style>
</html>