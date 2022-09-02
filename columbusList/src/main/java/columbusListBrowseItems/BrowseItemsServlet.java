package columbusListBrowseItems;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DeveloperSetup.Connector;
import columbusListPostListing.PostListingInstance;
import columbusListProfilePage.ProfilePageConnector;
import columbusListProfilePage.ProfilePageInstance;
import columbusListWishlist.WishlistPageConnector;

/**
 * Servlet implementation class BrowseItemsServlet
 * TODO: Search and browse items functionality, this should probably be built out after the PostListingServlet. 
 */
@WebServlet("/BrowseItemsServlet")
public class BrowseItemsServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    public BrowseItemsServlet() 
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
			String title = request.getParameter("title");
			String price = request.getParameter("price");
			request.setAttribute("email", email);
			request.setAttribute("searchTitle", title);
			request.setAttribute("searchPrice", price);
			ArrayList<PostListingInstance> listings = null;
			try {
				listings = BrowseItemsConnector.loadListings(title, price, email);
			} catch (NumberFormatException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	request.setAttribute("listings", listings);
			request.getRequestDispatcher("/WEB-INF/browseItems.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(false);
		String email = (String)session.getAttribute("email");
		String id = request.getParameter("listingId");
		String action = request.getParameter("action");
		request.setAttribute("action", null);
		if (session == null || email == null) 
        {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
        } 
		else
		{
			if (action.equals("add"))
				try {
						WishlistPageConnector.addTo(email, id);
						request.setAttribute("message", "added to wishlist.");
				} catch (ClassNotFoundException e) {
					request.setAttribute("message", "Error: Could not add to wishlist.");
					e.printStackTrace();
				}
			if(action.equals("remove")) {
				try {
					WishlistPageConnector.remove(email, id);
					request.setAttribute("message", "removed from wishlist.");
				} catch (ClassNotFoundException e) {
					request.setAttribute("message", "Error: Could not remove from wishlist.");
					e.printStackTrace();
				}
			}
			if(action.equals("checkout")) {
				String title = request.getParameter("email");
		        request.setAttribute("listingBeingSold", request.getParameter("email"));
		        request.setAttribute("listingBeingSoldId", request.getParameter("listingId"));
		        request.setAttribute("listingBeingSoldPrice", request.getParameter("listingPrice"));
		        request.getRequestDispatcher("/WEB-INF/checkoutPage.jsp").forward(request, response);
			}
		}
		doGet(request,response);
	}
	
}