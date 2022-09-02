package columbusListCreateAccount;

import java.io.IOException;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccountServlet
 * TODO: Possibly implement email verification and password strength enforcement, if time allows. 
 */
@WebServlet("/AccountServlet")
public class AccountServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public AccountServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.sendRedirect("createAccount.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		boolean pass = false;
		AccountInstance account = new AccountInstance();
		account.setEmail(request.getParameter("email"));
		account.setPassword(request.getParameter("password"));
		account.setFirstName(request.getParameter("firstName"));
		account.setLastName(request.getParameter("lastName"));
		if(!account.getEmail().endsWith("@columbus.edu"))
		{
			request.setAttribute("message", "Bad email address.");
			request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
			return;
		}
		if(!account.getPassword().equals(request.getParameter("password2")))
		{
			request.setAttribute("message", "Passwords do not match.");
			request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
			return;
		}
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clschema","root","kspa3831");
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM clschema.accounts WHERE email = ? ;");
			preparedStatement.setString(1, account.getEmail());
			ResultSet set = preparedStatement.executeQuery();
			pass = set.next();
			if(pass)
			{
				request.setAttribute("message", "This email is already in use.");
				request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
				return;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			AccountConnector.addAccount(account);
			response.sendRedirect("loginPage.jsp?message="+URLEncoder.encode("Account created, please log in.", "UTF-8"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}