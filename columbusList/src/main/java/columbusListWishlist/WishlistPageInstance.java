package columbusListWishlist;


import java.io.Serializable;
import java.util.ArrayList;

import columbusListPostListing.PostListingInstance;

public class WishlistPageInstance implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String email;
	private ArrayList<PostListingInstance> listings = new ArrayList<PostListingInstance>();
	
	
	@SuppressWarnings("unused")
	public void addListing(PostListingInstance listing) {
		//load listings into listings ArrayList
		listings.add(listing);
	
	}
	
	@SuppressWarnings("unused")
	public ArrayList<PostListingInstance> getListings(){
		return listings;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
		
}