package DeveloperSetup;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connector 
{
	public static Connection Connect() throws ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clschema","root","MySQLPasswordGoesHere");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}
}