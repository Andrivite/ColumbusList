package columbusListWishlist;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DeveloperSetup.Connector;
import columbusListPostListing.PostListingInstance;

public class WishlistPageConnector{
	
	public static Boolean loadListings(String email, WishlistPageInstance profilePage, String wishlistListing) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Boolean pass = false;
		try
		{
			Connection conn = Connector.Connect();
			PreparedStatement ps;
			ResultSet rst;
			PreparedStatement ps2;
			ResultSet rst2;
			//Needs to be changed to getItems from Wish List
			ps2 = conn.prepareStatement("Select * FROM listings WHERE id = ? order by dateposted");
			ps2.setObject(1, wishlistListing);
			rst2 = ps2.executeQuery();
			if(rst2.next())
			{
			PostListingInstance listing = new PostListingInstance();
			listing.setEmail(email);
			listing.setTitle(rst2.getString("title"));
			listing.setDescription(rst2.getString("description"));
			listing.setPrice(String.valueOf(rst2.getDouble("price")));
			listing.setDateposted(rst2.getDate("dateposted"));
			listing.setTags(rst2.getString("tags"));
			listing.setId(rst2.getInt("id"));
				
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
			pass = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pass;
		
	}
	
	public static Boolean loadName(String email, WishlistPageInstance profilePage) throws ClassNotFoundException{
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
			Connection conn = Connector.Connect();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM listings WHERE id = ?");
			ps.setInt(1, id);
			if (ps.executeUpdate() > 0) {
				PreparedStatement ps2 = conn.prepareStatement("DELETE from pictures WHERE listing id = ?");
				ps2.executeUpdate();
				pass = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pass;
	}
	
	public static void addTo(String email, String id) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection conn = Connector.Connect();
			int listId = Integer.valueOf(id);
			ResultSet rs;
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM clschema.accounts WHERE email=?;");
			preparedStatement.setString(1, email);
			rs = preparedStatement.executeQuery();
			
			StringBuilder wishlist = new StringBuilder("");
			int userId = 0;
			if(rs.next()) {
				userId = rs.getInt("id");
				if(rs.getString("wishlist") != null) {
					String list = rs.getString("wishlist");
					if(!list.isEmpty()) {
						wishlist.append(list);
						wishlist.append(id);
						wishlist.append(",");
					}
					else {
						wishlist.append(id);
						wishlist.append(",");
					}
				}
				else {
					wishlist.append(id);
					wishlist.append(",");
				}
			}
			
			PreparedStatement pst = conn.prepareStatement("UPDATE clschema.accounts SET wishlist=? WHERE id=?;");
			pst.setString(1, wishlist.toString());
			pst.setInt(2, userId);
			pst.executeUpdate();	
			
			preparedStatement = conn.prepareStatement("SELECT * FROM clschema.listings WHERE id=?;");
			preparedStatement.setInt(1, listId);
			rs = preparedStatement.executeQuery();
			
			StringBuilder wishAccounts = new StringBuilder("");
			if(rs.next()) {
				if(rs.getString("wishingAccounts") != null) {
					String list = rs.getString("wishingAccounts");
					if(!list.isEmpty()) {
						wishAccounts.append(list);
						wishAccounts.append(userId);
						wishAccounts.append(",");
					}
					else {
						wishAccounts.append(userId);
						wishAccounts.append(",");
					}
				}
				else {
					wishAccounts.append(userId);
					wishAccounts.append(",");
				}
			}
			
			pst = conn.prepareStatement("UPDATE clschema.listings SET wishingAccounts=? WHERE id=?;");
			pst.setString(1, wishAccounts.toString());
			pst.setInt(2, listId);
			pst.executeUpdate();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void remove(String email, String id) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println(id);
		try {
			Connection conn = Connector.Connect();
			int listId = Integer.valueOf(id);
			ResultSet rs;
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM clschema.accounts WHERE email=?;");
			preparedStatement.setString(1, email);
			rs = preparedStatement.executeQuery();
			
			StringBuilder wishlist = new StringBuilder("");
			String[] newList;
			int userId = 0;
			if(rs.next()) {
				userId = rs.getInt("id");
				String list = rs.getString("wishlist");
				if(!list.isEmpty()) {
					newList = list.split(",");
					for (String s : newList) {
						if(!s.equals(id)) {
							System.out.println(s + ",");
							wishlist.append(s + ",");
						}
					}
				}
			}
			
			PreparedStatement pst = conn.prepareStatement("UPDATE clschema.accounts SET wishlist=? WHERE id=?;");
			pst.setString(1, wishlist.toString());
			pst.setInt(2, userId);
			pst.executeUpdate();	
			
			preparedStatement = conn.prepareStatement("SELECT * FROM clschema.listings WHERE id=?;");
			preparedStatement.setInt(1, listId);
			rs = preparedStatement.executeQuery();
			
			StringBuilder wishAccounts = new StringBuilder("");
			String[] newListA;
			if(rs.next()) {
				String list = rs.getString("wishingAccounts");
				if(!list.isEmpty()) {
					newListA = list.split(",");
					for (String s : newListA) {
						if(!s.equals(Integer.toString(userId))) {
							wishAccounts.append(s + ",");
						}
					}
				}
			}
			
			pst = conn.prepareStatement("UPDATE clschema.listings SET wishingAccounts=? WHERE id=?;");
			pst.setString(1, wishAccounts.toString());
			pst.setInt(2, listId);
			pst.executeUpdate();
		}		
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}	
}