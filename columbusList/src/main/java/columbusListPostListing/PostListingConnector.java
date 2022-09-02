package columbusListPostListing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DeveloperSetup.Connector;


public class PostListingConnector 
{
	
	
	public static void addListing(PostListingInstance listing) throws ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		int id = 0;
		try
		{
			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clschema","root","JZf3udVHk8@PSdm");
			Connection conn = Connector.Connect();
			PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO listings (email, title, description, price, dateposted, tags) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, listing.getEmail());
            preparedStatement.setString(2, listing.getTitle());
            preparedStatement.setString(3, listing.getDescription());
            preparedStatement.setDouble(4, listing.getPrice());
            preparedStatement.setDate(5, listing.getDateposted());
            preparedStatement.setString(6, listing.getTags());
			preparedStatement.executeUpdate();
			ResultSet result = preparedStatement.getGeneratedKeys();
			if(result.next())
				id = result.getInt(1);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		
		
		if(listing.getPicture1() != null)
		{
			try
			{
				//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clschema","root","JZf3udVHk8@PSdm");
				Connection conn = Connector.Connect();
				PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO pictures (listingid, picture, picname) VALUES (?, ?, ?);");
				preparedStatement.setInt(1, id);
				preparedStatement.setBlob(2, listing.getPicture1());
				preparedStatement.setString(3, listing.getPic1Name());
				preparedStatement.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(listing.getPicture2() != null)
		{
			try
			{
				//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clschema","root","JZf3udVHk8@PSdm");
				Connection conn = Connector.Connect();
				PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO pictures (listingid, picture, picname) VALUES (?, ?, ?);");
				preparedStatement.setInt(1, id);
				preparedStatement.setBlob(2, listing.getPicture2());
				preparedStatement.setString(3, listing.getPic2Name());
				preparedStatement.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(listing.getPicture3() != null)
		{
			try
			{
				//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clschema","root","JZf3udVHk8@PSdm");
				Connection conn = Connector.Connect();
				PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO pictures (listingid, picture, picname) VALUES (?, ?, ?);");
				preparedStatement.setInt(1, id);
				preparedStatement.setBlob(2, listing.getPicture3());
				preparedStatement.setString(3, listing.getPic3Name());
				preparedStatement.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(listing.getPicture4() != null)
		{
			try
			{
				//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clschema","root","JZf3udVHk8@PSdm");
				Connection conn = Connector.Connect();
				PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO pictures (listingid, picture, picname) VALUES (?, ?, ?);");
				preparedStatement.setInt(1, id);
				preparedStatement.setBlob(2, listing.getPicture4());
				preparedStatement.setString(3, listing.getPic4Name());
				preparedStatement.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}