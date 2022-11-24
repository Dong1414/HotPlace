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
    String attachFileMasterId; 
    String attachFileId; //프로필 사진
    String mberNickName;
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
	public String getAttachFileMasterId() {
		return attachFileMasterId;
	}
	public void setAttachFileMasterId(String attachFileMasterId) {
		this.attachFileMasterId = attachFileMasterId;
	}
	
	public String getAttachFileId() {
		return attachFileId;
	}
	public void setAttachFileId(String attachFileId) {
		this.attachFileId = attachFileId;
	}
	
	public String getMberNickName() {
		return mberNickName;
	}
	public void setMberNickName(String mberNickName) {
		this.mberNickName = mberNickName;
	}
	@Override
	public String toString() {
		return "RepleVO [id=" + id + ", reviewId=" + reviewId + ", repleCts=" + repleCts + ", repleId=" + repleId
				+ ", registId=" + registId + ", registDt=" + registDt + ", updateId=" + updateId + ", updateDt="
				+ updateDt + ", attachFileMasterId=" + attachFileMasterId + "]";
	}
	
}
