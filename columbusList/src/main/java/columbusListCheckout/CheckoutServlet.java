package columbusListCheckout;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckoutServlet
 * TODO: Safe transaction function. 
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public CheckoutServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) 
        {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
        } 
		else
		{
			request.getRequestDispatcher("/WEB-INF/checkoutPage.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}