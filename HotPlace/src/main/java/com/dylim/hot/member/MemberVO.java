package com.dylim.hot.member;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class MemberVO{


	String id ="";
	String mberId ="";
	String mberPassword;
	String mberName ="";
	String mberBrthd="";
	String mberTelNo="";
	String mberEmail="";
	String registId="";
	String registDt="";
	String updateId="";
	String updateDt="";
	String delYn="";
	String mberAuth="";
	String mberNickName="";
	String attachFileMasterId;
	String attachFileId;
	String attachFileUrl;
	
	String loginId = "";
	String relationType = ""; //요청기록
	String relation = ""; //친구인가
	
	String mberFirstId = "";
	String mberSecondId = "";
	String identiFication = ""; //본인확인용 없을 시 본인 1일시 친구 2일시 남
	
	String kakaoConnectId;
	String naverConnectId;
	String snsMod;
	
	String emailYn;
	String telYn;
	
	
	public String getMberAuth() {
		return mberAuth;
	}
	public void setMberAuth(String mberAuth) {
		this.mberAuth = mberAuth;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMberId() {
		return mberId;
	}
	public void setMberId(String mberId) {
		this.mberId = mberId;
	}
	public String getMberPassword() {
		return mberPassword;
	}
	public void setMberPassword(String mberPassword) {
		this.mberPassword = mberPassword;
	}
	public String getMberName() {
		return mberName;
	}
	public void setMberName(String mberName) {
		this.mberName = mberName;
	}
	public String getMberBrthd() {
		return mberBrthd;
	}
	public void setMberBrthd(String mberBrthd) {
		this.mberBrthd = mberBrthd;
	}
	public String getMberTelNo() {
		return mberTelNo;
	}
	public void setMberTelNo(String mberTelNo) {
		this.mberTelNo = mberTelNo;
	}
	public String getMberEmail() {
		return mberEmail;
	}
	public void setMberEmail(String mberEmail) {
		this.mberEmail = mberEmail;
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
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	public String getMberFirstId() {
		return mberFirstId;
	}
	public void setMberFirstId(String mberFirstId) {
		this.mberFirstId = mberFirstId;
	}
	public String getMberSecondId() {
		return mberSecondId;
	}
	public void setMberSecondId(String mberSecondId) {
		this.mberSecondId = mberSecondId;
	}
	
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public String getIdentiFication() {
		return identiFication;
	}
	public void setIdentiFication(String identiFication) {
		this.identiFication = identiFication;
	}
	
	public String getMberNickName() {
		return mberNickName;
	}
	public void setMberNickName(String mberNickName) {
		this.mberNickName = mberNickName;
	}
	
	public String getKakaoConnectId() {
		return kakaoConnectId;
	}
	public void setKakaoConnectId(String kakaoConnectId) {
		this.kakaoConnectId = kakaoConnectId;
	}
	public String getNaverConnectId() {
		return naverConnectId;
	}
	public void setNaverConnectId(String naverConnectId) {
		this.naverConnectId = naverConnectId;
	}
	
	public String getSnsMod() {
		return snsMod;
	}
	public void setSnsMod(String snsMod) {
		this.snsMod = snsMod;
	}
	
	public String getEmailYn() {
		return emailYn;
	}
	public void setEmailYn(String emailYn) {
		this.emailYn = emailYn;
	}
	public String getTelYn() {
		return telYn;
	}
	public void setTelYn(String telYn) {
		this.telYn = telYn;
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
	
	public String getAttachFileUrl() {
		return attachFileUrl;
	}
	public void setAttachFileUrl(String attachFileUrl) {
		this.attachFileUrl = attachFileUrl;
	}
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", mberId=" + mberId + ", mberPassword=" + mberPassword + ", mberName=" + mberName
				+ ", mberBrthd=" + mberBrthd + ", mberTelNo=" + mberTelNo + ", mberEmail=" + mberEmail + ", registId="
				+ registId + ", registDt=" + registDt + ", updateId=" + updateId + ", updateDt=" + updateDt + ", delYn="
				+ delYn + "]";
	}
}
