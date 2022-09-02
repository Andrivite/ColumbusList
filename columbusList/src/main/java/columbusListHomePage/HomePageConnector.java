package columbusListHomePage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import DeveloperSetup.Connector;
import columbusListLogin.LoginInstance;
import columbusListPostListing.PostListingInstance;

public class HomePageConnector {
	public static boolean login(LoginInstance li) throws ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		boolean pass = false;
		try
		{
			Connection conn = Connector.Connect();
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM clschema.accounts WHERE email = ? and password = ? ;");
			preparedStatement.setString(1, li.getEmail());
			preparedStatement.setString(2, li.getPassword());
			ResultSet set = preparedStatement.executeQuery();
			pass = set.next();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pass;
	}
	
	public static Boolean loadListings(HomePageInstance HomePage, String email) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Boolean pass = false;
		try
		{
			Connection conn = Connector.Connect();
			PreparedStatement ps;
			ResultSet rst;
			PreparedStatement ps2;
			ResultSet rst2;
			ps = conn.prepareStatement("Select * FROM listings order by id DESC LIMIT 12");
			//ps.setObject(1, email);
			rst = ps.executeQuery();
			
			String wishingAccounts;
			ArrayList<String> wishingAccountsArr;
			PreparedStatement ps1;
			ResultSet rst1;
			while (rst.next()) {
				PostListingInstance listing = new PostListingInstance();
				listing.setTitle(rst.getString("title"));
				listing.setPrice(String.valueOf(rst.getDouble("price")));
				listing.setDateposted(rst.getDate("dateposted"));
				listing.setId(rst.getInt("id"));
				listing.setDescription(rst.getString("description"));
				
				listing.setIsUserListing(email.contentEquals(rst.getString("email")));
				listing.setInUserWishList(false);
				if (rst.getString("wishingAccounts")!=null) {
					wishingAccounts = rst.getString("wishingAccounts").trim();
					if (!wishingAccounts.contentEquals("")) {
						wishingAccountsArr = new ArrayList<String>(Arrays.asList(wishingAccounts.split(",")));
						ps1 = conn.prepareStatement("SELECT id from accounts WHERE email = ?");
						ps1.setString(1, email);
						rst1 = ps1.executeQuery();
						if (rst1.next()) {
							int accountId = rst1.getInt("id");
							listing.setInUserWishList(wishingAccountsArr.contains(String.valueOf(accountId)));
						}
					}
				}
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
				HomePage.addListing(listing);
			}
			pass = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pass;
		
	}
	
	public static boolean loadNotifications(HomePageInstance HomePage, String email) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Boolean pass = false;
		try
		{
			Connection conn = Connector.Connect();
			PreparedStatement ps = conn.prepareStatement("SELECT notificationlist from accounts WHERE email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("notificationlist")!=null&&!rs.getString("notificationlist").contentEquals("")) {
					String notificationList = rs.getString("notificationlist");
					String[] notificationListArr = notificationList.split(",");
					int listingId;
					PreparedStatement ps2;
					ResultSet rs2;
					for (int i = 0; i < notificationListArr.length; i++) {
						listingId = Integer.parseInt(notificationListArr[i]);
						ps2 = conn.prepareStatement("Select title,id from listings WHERE id = ?");
						ps2.setInt(1, listingId);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							PostListingInstance listing = new PostListingInstance();
							listing.setTitle(rs2.getString("title"));
							listing.setId(rs2.getInt("id"));
							HomePage.addNotificationListing(listing);
						}
					}
					pass = true;
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return true;
		
	}
	
	public static boolean removeListingFromNotificationList(String email, String listingId) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		boolean pass = false;
		try {
			Connection conn = Connector.Connect();
			PreparedStatement ps = conn.prepareStatement("SELECT notificationlist from accounts WHERE email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("notificationlist")!= null && !rs.getString("notificationlist").isEmpty()) {
					String notificationList = rs.getString("notificationlist").trim();
					StringBuilder newNotificationList = new StringBuilder();
					String[] notificationListArr = notificationList.split(",");
					System.out.println("Listing id:" + listingId);
					for (String s : notificationListArr) {
						if(!s.equals(listingId)) {
									System.out.println("Adding: " + s + ",");
									newNotificationList.append(s + ",");
						}
					}
					PreparedStatement ps1 = conn.prepareStatement("Update accounts set notificationlist=? WHERE email=?");
					ps1.setString(1,newNotificationList.toString());
					ps1.setString(2, email);
					ps1.executeUpdate();
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