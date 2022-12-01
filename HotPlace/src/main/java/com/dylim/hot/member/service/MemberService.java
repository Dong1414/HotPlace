package com.dylim.hot.member.service;

import java.util.List;
import java.util.Map;

import com.dylim.hot.map.ReviewVO;
import com.dylim.hot.member.MemberVO;

public interface MemberService {

	void signUpInsert(MemberVO memberVO) throws Exception;

	boolean idCheck(String id) throws Exception;

	MemberVO loadUserByUserId(MemberVO memberVO) throws Exception;

	List<MemberVO> searchByNickName(MemberVO memberVO) throws Exception;

	String friendRequest(String loginId, String mberId) throws Exception;
	
	List<MemberVO> friendRequestList(String loginId) throws Exception;

	String friendAccept(String loginId, String mberId) throws Exception;

	List<MemberVO> friendtList(String loginId) throws Exception;

	String friendNo(String loginId, String mberId) throws Exception;

	String friendDel(String loginId, String mberId) throws Exception;
	
	boolean friendCheck(String loginId, String mberId) throws Exception;

	MemberVO snsIdCheck(MemberVO resultToken) throws Exception;

	MemberVO getByUserId(MemberVO memberVO) throws Exception;

	void memberModify(MemberVO memberVO) throws Exception;

	MemberVO findLoginId(MemberVO memberVO) throws Exception;

	void changePw(MemberVO memberVO) throws Exception;

	void deleteMember(MemberVO memberVO) throws Exception;

	String signUpGetId(MemberVO memberVO) throws Exception;

}
