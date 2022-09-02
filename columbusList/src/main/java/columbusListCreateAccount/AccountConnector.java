package columbusListCreateAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DeveloperSetup.Connector;

public class AccountConnector 
{
	public static void addAccount(AccountInstance account) throws ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		try
		{
			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clschema","root","kspa3831");
			Connection conn = Connector.Connect();
			PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO accounts (email, password, firstName, lastName) VALUES (?, ?, ?, ?);");
			preparedStatement.setString(1, account.getEmail());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setString(3, account.getFirstName());
            preparedStatement.setString(4, account.getLastName());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}