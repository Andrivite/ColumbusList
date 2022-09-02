package columbusListPostListing;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

public class PostListingInstance implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private String email;
	private String title;
	private String description; 
	private double price;
	private Date dateposted;
	private String tags;
	private InputStream picture1;
	private InputStream picture2;
	private InputStream picture3;
	private InputStream picture4;
	private String pic1Name;
	private String pic2Name;
	private String pic3Name;
	private String pic4Name;
	private int id;
	private ArrayList<String> base64Images = new ArrayList<String>(); 
	private Boolean inUserWishList;
	private Boolean isUserListing;
	private Boolean canRemoveFromWishList;
	private Boolean canAddToWishList;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() 
	{
        return email;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }
    public String getTitle() 
    {
        return title;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }
	public String getDescription() 
	{
        return description;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }
    public double getPrice() 
    {
        return price;
    }
    public void setPrice(String price) 
    {
        this.price = Double.parseDouble(price);
    }
    public Date getDateposted()
	{
        return dateposted;
    }
    
    @SuppressWarnings("deprecation")
	public String getFormattedDatePosted()
    {
    	String formattedDatePosted = "";
    	formattedDatePosted = String.valueOf(dateposted.getMonth()+1) + "/" + String.valueOf(dateposted.getDay() + 1) + "/" + String.valueOf(dateposted.getYear() + 1900);
    	return formattedDatePosted;
    }
    
    public void setDateposted(Date dateposted) 
    {
        this.dateposted = dateposted;
    }
    public String getTags() 
	{
        return tags;
    }
    
    public String getFormattedTags()
    {
    	String formattedTags = tags;
    	if (formattedTags == null) {
    		return "";
    	}
    	formattedTags = formattedTags.replaceAll("null,", "");
    	if (formattedTags.contentEquals("")) {
    		return "";
    	}
    	formattedTags = formattedTags.replaceAll(" ", "");
    	formattedTags = formattedTags.trim();
		if (formattedTags.charAt(formattedTags.length() -1 ) == ','){
			formattedTags = formattedTags.substring(0, formattedTags.length() - 1);
		}
		formattedTags = formattedTags.replaceAll(",", ", ");
		
		return formattedTags;
    }
    
    public void setTags(String tags) 
    {
        this.tags = tags;
    }
    public void setPicture1(InputStream picture1, String pic1Name) 
    {
        this.picture1 = picture1;
        this.pic1Name = pic1Name;
    }
	public InputStream getPicture1() 
	{
        return picture1;
    }
	public void setPicture2(InputStream picture2, String pic2Name) 
    {
        this.picture2 = picture2;
        this.pic2Name = pic2Name;
    }
	public InputStream getPicture2() 
	{
        return picture2;
    }
	public void setPicture3(InputStream picture3, String pic3Name) 
    {
        this.picture3 = picture3;
        this.pic3Name = pic3Name;
    }
	public InputStream getPicture3() 
	{
        return picture3;
    }
	public void setPicture4(InputStream picture4, String pic4Name) 
    {
        this.picture4 = picture4;
        this.pic4Name = pic4Name;
    }
	public InputStream getPicture4() 
	{
        return picture4;
    }
	public String getPic1Name() 
	{
        return pic1Name;
    }
	public String getPic2Name() 
	{
        return pic2Name;
    }
	public String getPic3Name() 
	{
        return pic3Name;
    }
	public String getPic4Name() 
	{
        return pic4Name;
    }
	
	public void addBase64Image(Blob blob) {
		try {
			byte byteArray[] = blob.getBytes(1, (int) blob.length());
			OutputStream os = new ByteArrayOutputStream();
			os.write(byteArray);
			byte[] imageBytes = ((ByteArrayOutputStream) os).toByteArray();
			base64Images.add(Base64.getEncoder().encodeToString(imageBytes));
			os.flush();
			os.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getBase64Images(){
		return base64Images;
	}

	public Boolean getInUserWishList() {
		return this.inUserWishList;
	}
	
	public Boolean getIsUserListing() {
		return this.isUserListing;
	}

	public void setIsUserListing(Boolean isUserListing) {
		this.isUserListing = isUserListing;
	}

	public void setInUserWishList(Boolean inUserWishList) {
		this.inUserWishList = inUserWishList;
		setCanAddToWishList(this.isUserListing, this.inUserWishList);
	}

	public Boolean getCanRemoveFromWishList() {
		return this.canRemoveFromWishList;
	}
	
	public Boolean getCanAddToWishList() {
		return this.canAddToWishList;
	}

	public void setCanAddToWishList(Boolean isUserListing, Boolean inUserWishList) {
		if (isUserListing || inUserWishList) {
			this.canAddToWishList = false;
		}else this.canAddToWishList = true;
		System.out.println(this.canAddToWishList);
		setCanRemoveFromWishList(isUserListing, canAddToWishList);
	}

	public void setCanRemoveFromWishList(Boolean isUserListing, Boolean canAddToWishList) {
		if (isUserListing || canAddToWishList) {
			this.canRemoveFromWishList = false;
		}else this.canRemoveFromWishList = true;
		
	}

	
}