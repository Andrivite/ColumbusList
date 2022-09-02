package columbusListProfilePage;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import DeveloperSetup.Connector;
import columbusListHomePage.HomePageConnector;
import columbusListPostListing.PostListingInstance;

public class ProfilePageConnector{
	
	public static Boolean loadListings(String email, ProfilePageInstance profilePage) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Boolean pass = false;
		try
		{
			Connection conn = Connector.Connect();
			PreparedStatement ps;
			ResultSet rst;
			PreparedStatement ps2;
			ResultSet rst2;
			ps = conn.prepareStatement("Select * FROM listings WHERE email = ? order by dateposted");
			ps.setObject(1, email);
			rst = ps.executeQuery();
			while (rst.next()) {
				PostListingInstance listing = new PostListingInstance();
				listing.setEmail(email);
				listing.setTitle(rst.getString("title"));
				listing.setDescription(rst.getString("description"));
				listing.setPrice(String.valueOf(rst.getDouble("price")));
				listing.setDateposted(rst.getDate("dateposted"));
				listing.setTags(rst.getString("tags"));
				listing.setId(rst.getInt("id"));
				
				ps2 = conn.prepareStatement("Select picture, picname FROM pictures WHERE listingid = ?");
				ps2.setObject(1, listing.getId());
				rst2 = ps2.executeQuery();
				if (rst2.next()) {
					listing.addBase64Image(rst2.getBlob("picture"));
					if (rst2.next()) {
						listing.addBase64Image(rst2.getBlob("picture"));
						if (rst2.next()) {
							listing.addBase64Image(rst2.getBlob("picture"));
							if (rst2.next()) {
								listing.addBase64Image(rst2.getBlob("picture"));
							}
						}
					}
				}
				profilePage.addListing(listing);
			}
			pass = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pass;
		
	}
	
	public static Boolean loadName(String email, ProfilePageInstance profilePage) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Boolean pass = false;
		try
		{
			Connection conn = Connector.Connect();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT firstName, lastName FROM accounts WHERE email = ?");
			ps.setObject(1, email);
			ResultSet rst = ps.executeQuery();
			if (rst.next()) {
				profilePage.setFirstName(rst.getString("firstName"));
				profilePage.setLastName(rst.getString("lastName"));
			}
			pass = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pass;
		
	}
	
	public static Boolean deleteListing(int id) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Boolean pass = false;
		try
		{
			deleteListingFromAllWishList(id);
			Connection conn = Connector.Connect();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM listings WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("DELETE from pictures WHERE listingid = ?");
			ps2.setInt(1, id);
			ps2.executeUpdate();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pass;
	}
	
	public static Boolean changePrice(double price, int id) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Boolean pass = false;
		try
		{
			Connection conn = Connector.Connect();
			PreparedStatement ps;
			ps = conn.prepareStatement("UPDATE listings SET price = ? WHERE (id=?)");
			ps.setDouble(1, price);
			ps.setInt(2, id);
			ps.executeUpdate();
			sendNotification(id);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pass;
	}
	
	public static Boolean sendNotification(int listingId) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Boolean pass = false;
		
		try {
			Connection conn = Connector.Connect();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT wishingAccounts from listings WHERE id = ?");
			ps.setInt(1, listingId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("wishingAccounts")!=null && !rs.getString("wishingAccounts").contentEquals("")) {
					String wishingAccounts = rs.getString("wishingAccounts");
					String[] wishingAccountsArr = wishingAccounts.split(",");
					PreparedStatement ps2;
					ResultSet rs2;
					PreparedStatement ps3;
					int accountId;
					String notificationList;
					String updatedNotificationList;
					for (int i = 0; i < wishingAccountsArr.length; i++) {
						accountId = Integer.parseInt(wishingAccountsArr[i]);
						ps2 = conn.prepareStatement("Select notificationlist FROM accounts WHERE id = ?");
						ps2.setInt(1, accountId);
						
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							if (rs2.getString("notificationlist")!=null) {
								notificationList = rs2.getString("notificationlist");
								updatedNotificationList = notificationList + Integer.toString(listingId) + ",";
							}else {
								updatedNotificationList = Integer.toString(listingId) + ",";
							}
							
							ps3 = conn.prepareStatement("UPDATE accounts SET notificationlist = ? WHERE id = ?");
							ps3.setString(1, updatedNotificationList);
							ps3.setInt(2, accountId);
							ps3.executeUpdate();
						}
					}
				}
				
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		return pass;
	}
	
	public static Boolean deleteListingFromAllWishList(int listingId) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Boolean pass = false;
		try {
			Connection conn = Connector.Connect();
			PreparedStatement ps = conn.prepareStatement("Select wishingAccounts FROM listings WHERE id = ?");
			ps.setInt(1, listingId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if(rs.getString("wishingAccounts")!=null)
				{
				if (!rs.getString("wishingAccounts").contentEquals("")) {
					String wishingAccounts = rs.getString("wishingAccounts").trim();
					String[] wishingAccountsArr = wishingAccounts.split(",");
					PreparedStatement ps2;
					ResultSet rs2;
					int accountId;
					ArrayList<String> wishlistArr;
					ArrayList<String> notificationListArr;
					String wishlist;
					StringBuilder str;
					PreparedStatement ps3;
					PreparedStatement ps4;
					String notificationList;
					for (int i = 0; i < wishingAccountsArr.length; i++) {
						accountId = Integer.parseInt(wishingAccountsArr[i]);
						ps2 = conn.prepareStatement("Select wishlist,notificationlist FROM accounts WHERE id = ?");
						ps2.setInt(1,  accountId);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							wishlist = rs2.getString("wishlist");
							wishlistArr = new ArrayList<String>(Arrays.asList(wishlist.split(",")));
							wishlistArr.remove(Integer.toString(listingId));
							str = new StringBuilder("");
						    for (String eachstring : wishlistArr) {
						    	System.out.println();
								System.out.println("Adding to str:" + eachstring + ",");
								System.out.println();
							    str.append(eachstring + ",");
							}
						    wishlist = str.toString();
							ps3 = conn.prepareStatement("UPDATE accounts SET wishlist = ? WHERE id = ?");
							ps3.setString(1, wishlist);
							ps3.setInt(2, accountId);
							ps3.executeUpdate();
							
							if (rs2.getString("notificationlist")!=null && !rs2.getString("notificationlist").isEmpty()) {
								notificationList = rs2.getString("notificationlist");
								notificationListArr = new ArrayList<String>(Arrays.asList(notificationList.split(",")));
								str = new StringBuilder("");
								for (String eachstring: notificationListArr) {
									if (!eachstring.contentEquals(Integer.toString(listingId))) {
										str.append(eachstring + ",");
									}
								}
								ps4 = conn.prepareStatement("UPDATE accounts SET notificationlist = ? WHERE id = ?");
								ps4.setString(1, str.toString());
								ps4.setInt(2, accountId);
								ps4.executeUpdate();
							}
							
						}
					}
				}
			}
		}
	
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pass;
	}
}