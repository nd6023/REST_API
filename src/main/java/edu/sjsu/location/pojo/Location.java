package edu.sjsu.location.pojo;

public class Location {
	
	private String lon;
	private String device_name;
	private String ts;
	private String user;
	private String status;
	private String lat;
	private String device_address;
	private String help_msg;
	
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Location(String lon, String device_name, String ts, String user, String status, String lat,
			String device_address, String help_msg) {
		super();
		this.lon = lon;
		this.device_name = device_name;
		this.ts = ts;
		this.user = user;
		this.status = status;
		this.lat = lat;
		this.device_address = device_address;
		this.help_msg = help_msg;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getDevice_address() {
		return device_address;
	}

	public void setDevice_address(String device_address) {
		this.device_address = device_address;
	}

	public String getHelp_msg() {
		return help_msg;
	}

	public void setHelp_msg(String help_msg) {
		this.help_msg = help_msg;
	}

	@Override
	public String toString() {
		return "Location [lon=" + lon + ", device_name=" + device_name + ", ts=" + ts + ", user=" + user + ", status="
				+ status + ", lat=" + lat + ", device_address=" + device_address + ", help_msg=" + help_msg + "]";
	}

	
		
}
