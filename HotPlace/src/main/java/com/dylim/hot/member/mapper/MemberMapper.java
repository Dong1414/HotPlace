package com.dylim.hot.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dylim.hot.member.MemberVO;

@Mapper
public interface MemberMapper {

	void signUpInsert(MemberVO memberVO) throws Exception;

	int idCheck(String id) throws Exception;
	
	MemberVO loadUserByUserId(String mberId) throws Exception;

	void friendRequest(String mberFirstId, String mberSecondId) throws Exception;

	int friendCheck(String mberFirstId, String mberSecondId) throws Exception;

	int requestCheck(String mberFirstId, String mberSecondId) throws Exception;

	MemberVO searchById(MemberVO memberVO) throws Exception;

	List<MemberVO> friendRequestList(String loginId) throws Exception;
	
	void friendAccept(String loginId, String mberId) throws Exception;
	
	void friendAcceptHistory(String loginId, String mberId) throws Exception;

	List<MemberVO> friendtList(String loginId) throws Exception;
}
