package columbusListBrowseItems;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import DeveloperSetup.Connector;
import columbusListPostListing.PostListingInstance;

public class BrowseItemsConnector{
	
	public static ArrayList<PostListingInstance> loadListings(String title, String price, String email) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Boolean pass = false;
		ArrayList<PostListingInstance> listings = new ArrayList<PostListingInstance>();
		try
		{
			Connection conn = Connector.Connect();
			PreparedStatement preparedStatement;
			ResultSet rs;
			PreparedStatement ps2;
			ResultSet rst2;
			if(title != null || price != null) {
				if (title.isEmpty() && price.isEmpty()) {
			        preparedStatement = conn.prepareStatement("SELECT * FROM listings");
				}
				else if(!title.isEmpty() && price.isEmpty()) {
					preparedStatement = conn.prepareStatement("SELECT * FROM listings WHERE title LIKE concat('%' , ?, '%') || tags LIKE concat('%' , ?, '%');");
			        preparedStatement.setString(1, title);
			        preparedStatement.setString(2, title);
				}
				else if(title.isEmpty() && !price.isEmpty()) {
					preparedStatement = conn.prepareStatement("SELECT * FROM listings WHERE price <= ?;");
			        preparedStatement.setDouble(1, Double.valueOf(price));
				}
				else {
					preparedStatement = conn.prepareStatement("SELECT * FROM listings WHERE (title LIKE concat('%' , ?, '%') || tags LIKE concat('%' , ?, '%')) && price <= ?;");
			        preparedStatement.setString(1, title);
			        preparedStatement.setString(2, title);
			        preparedStatement.setDouble(3, Double.valueOf(price));
				}
			}
			else {
				preparedStatement = conn.prepareStatement("SELECT * FROM listings");
			}
	        rs = preparedStatement.executeQuery();
			

	        while (rs.next()) {
	        	PostListingInstance item = new PostListingInstance();
	        	item.setId(rs.getInt("id"));
	        	item.setTitle(rs.getString("title"));
	        	item.setPrice(String.valueOf(rs.getDouble("price")));
	        	item.setDescription(rs.getString("description"));
	        	item.setDateposted(rs.getDate("dateposted"));
				item.setTags(rs.getString("tags"));
				
				item.setIsUserListing(email.contentEquals(rs.getString("email")));
				item.setInUserWishList(false);
				if (rs.getString("wishingAccounts")!=null) {
					String wishingAccounts = rs.getString("wishingAccounts").trim();
					if (!wishingAccounts.contentEquals("")) {
						ArrayList<String >wishingAccountsArr = new ArrayList<String>(Arrays.asList(wishingAccounts.split(",")));
						PreparedStatement ps1 = conn.prepareStatement("SELECT id from accounts WHERE email = ?");
						ps1.setString(1, email);
						ResultSet rst1 = ps1.executeQuery();
						if (rst1.next()) {
							int accountId = rst1.getInt("id");
							item.setInUserWishList(wishingAccountsArr.contains(String.valueOf(accountId)));
						}
					}
				}
	        	
	        	ps2 = conn.prepareStatement("Select picture, picname FROM pictures WHERE listingid = ?");
				ps2.setObject(1, item.getId());
				rst2 = ps2.executeQuery();
				if (rst2.next()) {
					item.addBase64Image(rst2.getBlob("picture"));
					if (rst2.next()) {
						item.addBase64Image(rst2.getBlob("picture"));
						if (rst2.next()) {
							item.addBase64Image(rst2.getBlob("picture"));
							if (rst2.next()) {
								item.addBase64Image(rst2.getBlob("picture"));
							}
						}
					}
				}
				listings.add(item);
	        }
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return listings;
	}	
}