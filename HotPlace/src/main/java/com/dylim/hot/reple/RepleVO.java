package com.dylim.hot.reple;

import java.util.List;

import com.dylim.hot.file.FileVO;

public class RepleVO {
	
    
	String id; //아이디
    String reviewId; //제목
    String repleCts; //주소
    String repleId; //별점
    String registId; //후기내용
    String registDt; // 위도
    String updateId; // 경도
    String updateDt; //방문일자
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public String getRepleCts() {
		return repleCts;
	}
	public void setRepleCts(String repleCts) {
		this.repleCts = repleCts;
	}
	public String getRepleId() {
		return repleId;
	}
	public void setRepleId(String repleId) {
		this.repleId = repleId;
	}
	public String getRegistId() {
		return registId;
	}
	public void setRegistId(String registId) {
		this.registId = registId;
	}
	public String getRegistDt() {
		return registDt;
	}
	public void setRegistDt(String registDt) {
		this.registDt = registDt;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public String getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
    
    
}
