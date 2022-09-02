package columbusListHomePage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import columbusListWishlist.WishlistPageConnector;

/**
 * Servlet implementation class HomePageServlet
 * TODO: Implement additional features like recently added listings and recently joined users. 
 */
@WebServlet("/HomePageServlet")
public class HomePageServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public HomePageServlet() 
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
			String email = (String) session.getAttribute("email");
			HomePageInstance instance = new HomePageInstance();
			try {
				HomePageConnector.loadNotifications(instance, email);
				request.setAttribute("notificationListings", instance.getNotificationListings());
				if(HomePageConnector.loadListings(instance, email)) {
					request.setAttribute("listings", instance.getListings());
				}
				else {
					request.setAttribute("message", "Error: Could not load listings.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("/WEB-INF/homePage.jsp").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		HttpSession session = request.getSession(false);
		System.out.println(request.getAttribute("action"));
		String email = (String)session.getAttribute("email");
		String id = request.getParameter("listingId");
		String action = request.getParameter("action");
		String listingIdToRemoveFromNotification = request.getParameter("listingIdToRemoveFromNotification");
		if (action== null) {
			System.out.println("IS NULL");
			doGet(request,response);
		}
		request.setAttribute("action", null);
		
		if (session == null || email == null) 
        {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
        } 
		else
		{

				if (action != null && action.equals("add"))
					try {
							WishlistPageConnector.addTo(email, id);
							request.setAttribute("message", "added to wishlist.");
							doGet(request,response);
					} catch (ClassNotFoundException e) {
						request.setAttribute("message", "Error: Could not add to wishlist.");
						e.printStackTrace();
					}
				else if(action != null && action.equals("remove") ) {
					try {
						WishlistPageConnector.remove(email, id);
						request.setAttribute("message", "removed from wishlist.");
						doGet(request,response);
					} catch (ClassNotFoundException e) {
						request.setAttribute("message", "Error: Could not remove from wishlist.");
						e.printStackTrace();
					}
				}else if(action != null && action.equals("removeFromNotifcationList")) {
					try {
						HomePageConnector.removeListingFromNotificationList(email, listingIdToRemoveFromNotification);
						request.setAttribute("message", "removed from notification list.");
						doGet(request, response);
					}
				
				 catch (ClassNotFoundException e) {
					request.setAttribute("message", "Error: Could not remove from notification list.");
					e.printStackTrace();
				}
				}
				else if(action != null && action.equals("checkout")) {
			        request.setAttribute("listingBeingSold", request.getParameter("email"));
			        request.setAttribute("listingBeingSoldId", request.getParameter("listingId"));
			        request.setAttribute("listingBeingSoldPrice", request.getParameter("listingPrice"));
			        request.getRequestDispatcher("/WEB-INF/checkoutPage.jsp").forward(request, response);
				}else {
					doGet(request,response);
				}
			}
		
		
    }
}