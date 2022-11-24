package com.dylim.hot.map;

import java.util.List;

import com.dylim.hot.file.FileVO;

public class ReviewVO {
	String id; //아이디
    String title; //제목
    String address; //주소
    Integer rating; //별점
    String review; //후기내용
    String lat; // 위도
    String lng; // 경도
    String visitDt; //방문일자
    String registDt; //등록일자
    String updateDt; //수정일자
    String registId; //등록자
    String updatId; //수정자
    String attachFileMasterId = ""; //파일 마스터 아이디
    String attachFileId = ""; //파일 마스터 아이디
    String openType;
    String mberId;
    String mberNickName;
    
    //상세페이지용 페이징
    int pageNo = 1;
    int startPage = 0;
    int endPage = 0;
    
    //타임라인용 페이징
    int startRowNum = 1;
  	int endRowNum;
  	int rowCount = 4;
  	int totalPageCount;

  	
  	List<FileVO> files;
  	
  	String repleCnt;
  	
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

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
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
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}
	
	public String getMberId() {
		return mberId;
	}

	public void setMberId(String mberId) {
		this.mberId = mberId;
	}

	public int getStartRowNum() {
		return startRowNum;
	}

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	public int getEndRowNum() {
		return endRowNum;
	}

	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
	public List<FileVO> getFiles() {
		return files;
	}

	public void setFiles(List<FileVO> files) {
		this.files = files;
	}
	
	public String getRepleCnt() {
		return repleCnt;
	}

	public void setRepleCnt(String repleCnt) {
		this.repleCnt = repleCnt;
	}
	
	public String getMberNickName() {
		return mberNickName;
	}

	public void setMberNickName(String mberNickName) {
		this.mberNickName = mberNickName;
	}

	@Override
	public String toString() {
		return "ReviewVO [id=" + id + ", title=" + title + ", address=" + address + ", rating=" + rating + ", review="
				+ review + ", lat=" + lat + ", lng=" + lng + ", visitDt=" + visitDt + ", registDt=" + registDt
				+ ", updateDt=" + updateDt + ", registId=" + registId + ", updatId=" + updatId + ", attachFileMasterId="
				+ attachFileMasterId + ", attachFileId=" + attachFileId + ", pageNo=" + pageNo + "]";
	}
	
	
}
