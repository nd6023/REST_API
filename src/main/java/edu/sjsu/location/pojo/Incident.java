package edu.sjsu.location.pojo;

public class Incident {
	
	private String id;
	private String latitude;
	private String longitude;
	private String date;
	private String time;
	private String type;
	private String streetName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public Incident(String id, String latitude, String longitude, String date, String time, String type,
			String streetName) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
		this.time = time;
		this.type = type;
		this.streetName = streetName;
	}
	public Incident() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Incident [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", date=" + date
				+ ", time=" + time + ", type=" + type + ", streetName=" + streetName + "]";
	}
	
	
	

}
