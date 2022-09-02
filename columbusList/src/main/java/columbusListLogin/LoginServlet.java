package columbusListLogin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ColumbusListServlet
 * TODO: Feature is complete. 
 */
@WebServlet("/LoginServlet")
@MultipartConfig
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.sendRedirect("loginPage.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		LoginInstance lm = new LoginInstance();
		lm.setEmail(email);
		lm.setPassword(password);
		if(!email.endsWith("@columbus.edu"))
		{
			request.setAttribute("message", "Invalid email.");
			request.getRequestDispatcher("loginPage.jsp").forward(request, response);
			return;
		}
		try
		{
			if(LoginConnector.login(lm))
			{
				HttpSession session = request.getSession();
				session.setAttribute("email", email);
				session.setMaxInactiveInterval(30*60);
				response.sendRedirect(request.getContextPath() + "/HomePageServlet");
				return;
			}
			else
			{
				request.setAttribute("message", "Either your email or password are wrong.");
				request.getRequestDispatcher("loginPage.jsp").forward(request, response);
				return;
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}