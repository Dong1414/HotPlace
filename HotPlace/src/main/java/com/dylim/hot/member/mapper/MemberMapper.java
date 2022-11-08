package com.dylim.hot.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dylim.hot.member.MemberVO;

@Mapper
public interface MemberMapper {

	void signUpInsert(MemberVO memberVO) throws Exception;

	int idCheck(String id) throws Exception;
	
	MemberVO loadUserByUserId(String mberId) throws Exception;

	void friendRequest(String loginId, String mberId) throws Exception;

	int friendCheck(String loginId, String mberId) throws Exception;

	int requestCheck(String loginId, String mberId) throws Exception;

	MemberVO searchById(MemberVO memberVO) throws Exception;

	List<MemberVO> friendRequestList(String loginId) throws Exception;
	
	void friendAccept(String loginId, String mberId) throws Exception;
	
	List<MemberVO> friendtList(String loginId) throws Exception;

	void friendDelHistory(String loginId, String mberId);

	void friendDel(String loginId, String mberId);
}
