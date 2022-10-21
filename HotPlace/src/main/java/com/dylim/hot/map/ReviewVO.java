package com.dylim.hot.map;

public class ReviewVO {
	String id; //아이디
    String title; //제목
    String address; //주소
    Integer rating; //별점
    String review; //후기내용
    double lat; // 위도
    double lng; // 경도
    String visitDt; //방문일자
    String registDt; //등록일자
    String updateDt; //수정일자
    String registId; //등록자
    String updatId; //수정자
    String attachFileMasterId = ""; //파일 마스터 아이디
    String attachFileId = ""; //파일 마스터 아이디
    
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

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getRegistDt() {
		return registDt;
	}

	public void setRegistDt(String registDt) {
		this.registDt = registDt;
	}

	public String getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}

	public String getRegistId() {
		return registId;
	}

	public void setRegistId(String registId) {
		this.registId = registId;
	}

	public String getUpdatId() {
		return updatId;
	}

	public void setUpdatId(String updatId) {
		this.updatId = updatId;
	}

	public String getAttachFileMasterId() {
		return attachFileMasterId;
	}

	public void setAttachFileMasterId(String attachFileMasterId) {
		this.attachFileMasterId = attachFileMasterId;
	}
	
	public String getVisitDt() {
		return visitDt;
	}

	public void setVisitDt(String visitDt) {
		this.visitDt = visitDt;
	}
	
	public String getAttachFileId() {
		return attachFileId;
	}

	public void setAttachFileId(String attachFileId) {
		this.attachFileId = attachFileId;
	}

	@Override
	public String toString() {
		return "ReviewVO [id=" + id + ", title=" + title + ", address=" + address + ", rating=" + rating + ", review="
				+ review + ", lat=" + lat + ", lng=" + lng + ", visitDt=" + visitDt + ", registDt=" + registDt
				+ ", updateDt=" + updateDt + ", registId=" + registId + ", updatId=" + updatId + ", attachFileMasterId="
				+ attachFileMasterId + "]";
	}
	
	
}
