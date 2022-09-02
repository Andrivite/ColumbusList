package columbusListHomePage;
import java.util.*;
import columbusListPostListing.PostListingInstance;

public class HomePageInstance {
	private ArrayList<PostListingInstance> listings = new ArrayList<PostListingInstance>();
	private ArrayList<PostListingInstance> notificationListings = new ArrayList<PostListingInstance>();
	
	public void addNotificationListing(PostListingInstance listing) {
		notificationListings.add(listing);
	}
	
	public ArrayList<PostListingInstance> getNotificationListings(){
		return notificationListings;
	}
	
	public void addListing(PostListingInstance listing) {
        //load listings into listings ArrayList
        listings.add(listing);
    }
	@SuppressWarnings("unused")
    public ArrayList<PostListingInstance> getListings(){
        return listings;
    }
}