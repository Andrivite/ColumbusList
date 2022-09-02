package columbusListLogin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DeveloperSetup.Connector;

public class LoginConnector 
{
	public static boolean login(LoginInstance li) throws ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		boolean pass = false;
		try
		{
			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clschema","root","kspa3831");
			Connection conn = Connector.Connect();
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM clschema.accounts WHERE email = BINARY ? and password = BINARY ? ;");
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
}
