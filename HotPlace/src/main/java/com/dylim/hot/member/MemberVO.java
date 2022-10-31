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
	String mberPassword ="";
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
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", mberId=" + mberId + ", mberPassword=" + mberPassword + ", mberName=" + mberName
				+ ", mberBrthd=" + mberBrthd + ", mberTelNo=" + mberTelNo + ", mberEmail=" + mberEmail + ", registId="
				+ registId + ", registDt=" + registDt + ", updateId=" + updateId + ", updateDt=" + updateDt + ", delYn="
				+ delYn + "]";
	}
}
