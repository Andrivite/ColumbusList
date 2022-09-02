package columbusListPostListing;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import DeveloperSetup.Connector;

/**
 * Servlet implementation class PostListingServlet
 * TODO: Feature is complete.
 */
@WebServlet("/PostListingServlet")
@MultipartConfig()
public class PostListingServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public PostListingServlet()
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
			request.getRequestDispatcher("/WEB-INF/postListing.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(false);
		boolean pass = false;
		PostListingInstance listing = new PostListingInstance();
		String email = (String)session.getAttribute("email");
		InputStream input1 = null, input2 = null, input3 = null, input4 = null;
		Part file1 = request.getPart("picture1");
		Part file2 = request.getPart("picture2");
		Part file3 = request.getPart("picture3");
		Part file4 = request.getPart("picture4");
		listing.setPicture1(null, null);
		listing.setPicture2(null, null);
		listing.setPicture3(null, null);
		listing.setPicture4(null, null);
		if(!file1.getSubmittedFileName().equals(""))
		{
			input1 = file1.getInputStream();
			listing.setPicture1(input1, file1.getSubmittedFileName());
		}
		if(!file2.getSubmittedFileName().equals(""))
		{
			input2 = file2.getInputStream();
			listing.setPicture2(input2, file2.getSubmittedFileName());
		}
		if(!file3.getSubmittedFileName().equals(""))
		{
			input3 = file3.getInputStream();
			listing.setPicture3(input3, file3.getSubmittedFileName());
		}
		if(!file4.getSubmittedFileName().equals(""))
		{
			input4 = file4.getInputStream();
			listing.setPicture4(input4, file4.getSubmittedFileName());
		}
		listing.setEmail(email);
		listing.setTitle(request.getParameter("title"));
		listing.setDescription(request.getParameter("description"));
		listing.setPrice(request.getParameter("price"));
		listing.setDateposted(new Date(System.currentTimeMillis()));
		String tags = 
				request.getParameter("clothes")+","+
				request.getParameter("electronics")+","+
				request.getParameter("freeitems")+","+
				request.getParameter("furniture")+","+
				request.getParameter("games")+","+
				request.getParameter("groceries")+","+
				request.getParameter("hobbies")+","+
				request.getParameter("hygiene")+","+
				request.getParameter("miscellaneouos")+","+
				request.getParameter("musicalinstruments")+","+
				request.getParameter("petsupplies")+","+
				request.getParameter("sports")+","+
				request.getParameter("stationary")+","+
				request.getParameter("tools")+","+
				request.getParameter("other");
		listing.setTags(tags);
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clschema","root","JZf3udVHk8@PSdm");
			Connection conn = Connector.Connect();
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM clschema.listings WHERE email = ? AND title = ?;");
			preparedStatement.setString(1, listing.getEmail());
			preparedStatement.setString(2, listing.getTitle());
			ResultSet set = preparedStatement.executeQuery();
			pass = set.next();
			if(pass)
			{
				request.setAttribute("message", "A listing with this title under your account already exists, please choose a different title.");
				request.getRequestDispatcher("/WEB-INF/postListing.jsp").forward(request, response);
				return;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			PostListingConnector.addListing(listing);
			request.setAttribute("message", "Listing created.");
			request.getRequestDispatcher("/WEB-INF/postListing.jsp").forward(request, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}