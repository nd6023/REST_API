package edu.sjsu.location.pojo;

import java.util.List;

import edu.sjsu.location.pojo.Location;

public class LocationList {

	List<Location> ll;

	public LocationList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LocationList(List<Location> ll) {
		//super();
		this.ll = ll;
	}

	public List<Location> getLl() {
		return ll;
	}

	public void setLl(List<Location> ll) {
		this.ll = ll;
	}

	@Override
	public String toString() {
		return "LocationList [ll=" + ll + "]";
	}
	
}
