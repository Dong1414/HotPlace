package com.dylim.hot.map;

public class ReviewVO {
	String id;
    String title;
    String address;
    Integer grade;
    String review;
    double lon;
    double lat;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getlat() {
		return lat;
	}
	public void setlat(double lat) {
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "ReviewVO [id=" + id + ", title=" + title + ", address=" + address + ", grade=" + grade + ", review="
				+ review + ", lon=" + lon + ", lat=" + lat + "]";
	}

	
}
