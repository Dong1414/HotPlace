package com.dylim.hot.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dylim.hot.member.MemberVO;

@Mapper
public interface MemberMapper {

	void signUpInsert(MemberVO memberVO) throws Exception;

	int idCheck(String id) throws Exception;
	
	MemberVO loadUserByUserId(String mberId);
}
