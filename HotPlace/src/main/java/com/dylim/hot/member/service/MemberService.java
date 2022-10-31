package com.dylim.hot.member.service;

import java.util.List;
import java.util.Map;

import com.dylim.hot.map.ReviewVO;
import com.dylim.hot.member.MemberVO;

public interface MemberService {

	void signUpInsert(MemberVO memberVO) throws Exception;

	boolean idCheck(String id) throws Exception;

	MemberVO loadUserByUserId(MemberVO memberVO) throws Exception;
	
}
