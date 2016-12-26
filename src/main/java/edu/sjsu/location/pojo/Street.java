package edu.sjsu.location.pojo;

public class Street {
	
	private String streetName;
	private String rating;
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public Street(String streetName, String rating) {
		super();
		this.streetName = streetName;
		this.rating = rating;
	}
	public Street() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
