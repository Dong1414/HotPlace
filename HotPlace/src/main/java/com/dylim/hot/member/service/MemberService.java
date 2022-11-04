package com.dylim.hot.member.service;

import java.util.List;
import java.util.Map;

import com.dylim.hot.map.ReviewVO;
import com.dylim.hot.member.MemberVO;

public interface MemberService {

	void signUpInsert(MemberVO memberVO) throws Exception;

	boolean idCheck(String id) throws Exception;

	MemberVO loadUserByUserId(MemberVO memberVO) throws Exception;

	MemberVO searchById(MemberVO memberVO) throws Exception;

	String friendRequest(String mberFirstId, String mberSecondId) throws Exception;

	List<MemberVO> friendRequestList(String loginId) throws Exception;

	String friendAccept(String loginId, String mberId) throws Exception;

}
