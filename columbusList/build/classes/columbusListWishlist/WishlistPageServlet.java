package columbusListWishlist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DeveloperSetup.Connector;


/**
 * Servlet implementation class ProfilePageServlet
 * TODO: Implement profile page where profile information may be edited and reviewed. Users should also be able to see all of their listings here. 
 */
@WebServlet("/WishlistPageServlet")
public class WishlistPageServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public WishlistPageServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection conn;
		try {
			conn = Connector.Connect();
		PreparedStatement ps;
		ResultSet rst;
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) 
        {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
        } 
		else
		{
			String email = (String)session.getAttribute("email");
			WishlistPageInstance wishListPage = new WishlistPageInstance();
			wishListPage.setEmail(email);
			try 
			{
				ps = conn.prepareStatement("Select * FROM accounts WHERE email = ?");
				ps.setObject(1, email);
				rst = ps.executeQuery();
				if(rst.next())
				{
				String wishlist = rst.getString("wishlist");
				if(wishlist!=null)
				{
					if (!wishlist.contentEquals("")) {
				String wishlistListings[] = wishlist.split(",");
				for(int i = 0; i < wishlistListings.length; i++)
				{
				if(WishlistPageConnector.loadListings(email, wishListPage, wishlistListings[i])) {
					request.setAttribute("listings", wishListPage.getListings());
				}
				else {
					request.setAttribute("message", "Error: Could not load listings.");
					request.getRequestDispatcher("homePage.jsp").forward(request, response);
				}
				}
				}
				}
				
				if (WishlistPageConnector.loadName(email, wishListPage)) {
					request.setAttribute("name", wishListPage.getFirstName() + " " + wishListPage.getLastName());
				}else {
					request.setAttribute("message", "Error: Could not load name.");
					request.getRequestDispatcher("homePage.jsp").forward(request, response);
					}
				}
				System.out.println("done loading");
			}
			catch (ClassNotFoundException | SQLException e)
			{
				e.printStackTrace();
			}
			request.getRequestDispatcher("/WEB-INF/wishList.jsp").forward(request, response);
		}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}		
		
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
//	{
//		HttpSession session = request.getSession(false);
//		String email = (String)session.getAttribute("email");
//		String listingId = request.getParameter("listingId");
//		String action = request.getParameter("action");
//		request.setAttribute("action", null);
//		if (action == null) {
//			System.out.println("IS NULL");
//			doGet(request,response);
//		}
//			if (action.contentEquals("remove")) {
//				try {
//					WishlistPageConnector.remove(email, listingId);
//					request.setAttribute("message", "removed from wishlist.");
//					doGet(request,response);
//				} catch (ClassNotFoundException e) {
//					request.setAttribute("message", "Error: Could not remove from wishlist.");
//					e.printStackTrace();
//				}
//			}else if(action.equals("checkout")) {
//		        request.setAttribute("listingBeingSold", request.getParameter("email"));
//		        request.setAttribute("listingBeingSoldId", request.getParameter("listingId"));
//		        request.setAttribute("listingBeingSoldPrice", request.getParameter("listingPrice"));
//		        request.getRequestDispatcher("/WEB-INF/checkoutPage.jsp").forward(request, response);
//			}
//		
//		
//	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(false);
		String email = (String)session.getAttribute("email");
		String listingId = request.getParameter("listingId");
		String action = request.getParameter("action");
		request.setAttribute("action", null);
		if (action == null) {
			doGet(request, response);
		}
		
		if (action.contentEquals("remove")) {
			try {
				WishlistPageConnector.remove(email, listingId);
				request.setAttribute("message", "removed from wishlist.");
				doGet(request,response);
			} catch (ClassNotFoundException e) {
				request.setAttribute("message", "Error: Could not remove from wishlist.");
				e.printStackTrace();
			}
		}else if(action.equals("checkout")) {
	        request.setAttribute("listingBeingSold", request.getParameter("email"));
	        request.setAttribute("listingBeingSoldId", request.getParameter("listingId"));
	        request.setAttribute("listingBeingSoldPrice", request.getParameter("listingPrice"));
	        request.getRequestDispatcher("/WEB-INF/checkoutPage.jsp").forward(request, response);
		}
		
	}
		
	
	
}