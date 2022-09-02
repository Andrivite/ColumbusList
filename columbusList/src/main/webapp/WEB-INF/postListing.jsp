<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="columbusListLogin.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PostListing</title>
</head>
	<body>
	<h1>Post a Listing</h1>
		<div class="navigation">
			<a href="HomePageServlet">HomePage</a>
			<a href="BrowseItemsServlet">Browse Items</a>
			<a class="current" href="PostListingServlet">Post Listing</a>
			<a href="ProfilePageServlet">My Profile</a>
			<a href = "WishlistPageServlet">Wishlist</a>
			<a href="LogoutServlet">Logout</a>
		</div>
		<div>&nbsp;</div> 
		<div class="center">
		<form enctype="multipart/form-data" id="postListing" method="post" action="<%=request.getContextPath()%>/PostListingServlet">
			<p>Listing Information: </p>
			<div class="gen">
				<label>Listing Title: <input name="title" type="text" required></label><br><br>
				<label>Price: <input name="price" step="0.01" type="number" required></label><br><br>
			</div>
			<label>Description: <br><textarea class="desc" name="description" required></textarea></label><br><br>
			<p>Categories: </p>
			<div class="cats">
				<label>Clothes<input name="clothes" type="checkbox" value="clothes"></label><br>	
				<label>Electronics<input name="electronics" type="checkbox" value="electronics"></label><br>
				<label>Free Items<input name="freeitems" type="checkbox" value="freeitems"></label><br>
				<label>Furniture<input name="furniture" type="checkbox" value="furniture"></label><br>
				<label>Games<input name="games" type="checkbox" value="games"></label><br>
				<label>Groceries<input name="groceries" type="checkbox" value="groceries"></label><br>
				<label>Hobbies<input name="hobbies" type="checkbox" value="hobbies"></label><br>
				<label>Hygiene<input name="hygiene" type="checkbox" value="hygiene"></label><br>
				<label>Miscellaneous<input name="miscellaneouos" type="checkbox" value="miscellaneouos"></label><br>
				<label>Musical Instruments<input name="musicalinstruments" type="checkbox" value="musicalinstruments"></label><br>
				<label>Pet Supplies<input name="petsupplies" type="checkbox" value="petsupplies"></label><br>
				<label>Sports<input name="sports" type="checkbox" value="sports"></label><br>
				<label>Stationary<input name="stationary" type="checkbox" value="stationary"></label><br>
				<label>Tools<input name="tools" type="checkbox" value="tools"></label><br><br>
			</div>
				<label>Other: <input name="other" type="text" placeholder="Enter multiple tags using commas" size="25"></label><br><br>
			    <p>Pictures: </p>
			<div class="pics">
				<input id="pic1" name="picture1" type="file"><br>
				<input id="pic2" name="picture2" type="file"><br>
				<input id="pic3" name="picture3" type="file"><br>
				<input id="pic4" name="picture4" type="file"><br><br>
			</div>
			<button id="psbutton" type="submit">Post Listing</button>
		</form>
		<p id="picMessage"></p><br>
		<br><p class="message">${message}</p>
  		</div>
  		<div class="bottomBar">
			<a href="#">About</a>
			<a href="#">FAQ</a>
			<a href="#">Policies</a>
		</div><br>
		<div>&nbsp;</div> 
	</body>
	<footer>
		<div class="bottomBar">
				<a href="#">About</a>
				<a href="#">FAQ</a>
				<a href="#">Policies</a>
		</div><br>
	</footer>
	<script>
	 document.getElementById('pic2').style.visibility = 'hidden';
	 document.getElementById('pic3').style.visibility = 'hidden';
	 document.getElementById('pic4').style.visibility = 'hidden';
	 function picHider()
	 {
	 if(document.getElementById('pic1').files.length != 0)
	 {
		 document.getElementById('pic2').style.visibility = 'visible';
     }
	 else
	 {
		 document.getElementById('pic2').style.visibility = 'hidden';
     }
	 if(document.getElementById('pic2').files.length != 0)
	 {
	 	document.getElementById('pic3').style.visibility = 'visible';
	 }
 	 else
	 {
	 	document.getElementById('pic3').style.visibility = 'hidden';
	 }
	 if(document.getElementById('pic3').files.length != 0)
	 {
	 	document.getElementById('pic4').style.visibility = 'visible';
	 	document.getElementById('picMessage').innerHTML = "You may only upload four pictures per listing.";
	 }
 	 else
	 {
	 	document.getElementById('pic4').style.visibility = 'hidden';
	 	document.getElementById('picMessage').innerHTML = "";
	 }
	 }
	 var t = setInterval(picHider,1000);
	</script>
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
			overflow: overlay;
		}
	.center{
  			width: 800px;
        	height: 950px;
        	justify-content: center;
        	background-color: CornflowerBlue;
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
		.cats{
			text-align: right;
			margin-right: 350px;
			
		}
		.pics{
			text-align: right;
			margin-right: 191px;
			
		}
		.desc{
			height: 100px;
			width: 300px;
			margin-right: 0px;
			color: Black;
			font-family: helvetica;
		}
		.gen{
			text-align: right;
			margin-right: 300px;
			
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
		.message{
			font-weight: bold;
			color: Darkred;
		}
	</style>
</html>