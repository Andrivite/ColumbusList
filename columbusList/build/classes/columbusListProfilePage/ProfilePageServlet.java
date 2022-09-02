package columbusListProfilePage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class ProfilePageServlet
 * TODO: Implement profile page where profile information may be edited and reviewed. Users should also be able to see all of their listings here. 
 */
@WebServlet("/ProfilePageServlet")
public class ProfilePageServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public ProfilePageServlet() 
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
			String email = (String)session.getAttribute("email");
			ProfilePageInstance profilePage = new ProfilePageInstance();
			profilePage.setEmail(email);
			try 
			{
				if(ProfilePageConnector.loadListings(email, profilePage)) {
					request.setAttribute("listings", profilePage.getListings());
				}
				else {
					request.setAttribute("message", "Error: Could not load listings.");
					request.getRequestDispatcher("homePage.jsp").forward(request, response);
				}
				
				if (ProfilePageConnector.loadName(email, profilePage)) {
					request.setAttribute("name", profilePage.getFirstName() + " " + profilePage.getLastName());
				}else {
					request.setAttribute("message", "Error: Could not load name.");
					request.getRequestDispatcher("homePage.jsp").forward(request, response);
				}
				
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
			request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
		}
	}		
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(false);
		String email = (String)session.getAttribute("email");
		String action = request.getParameter("action");
		request.setAttribute("action", null);
		int listingId = Integer.parseInt(request.getParameter("listingId"));
		if (action.contentEquals("delete")) {
			System.out.println("Deleting...");
			try {
				ProfilePageConnector.deleteListing(listingId);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else if (action.contentEquals("post")){
			double price = Double.parseDouble(request.getParameter("price"));
			try {
				ProfilePageConnector.changePrice(price, listingId);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		doGet(request,response);
	}
	
	
}