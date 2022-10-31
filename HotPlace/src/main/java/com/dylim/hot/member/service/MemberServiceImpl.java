package com.dylim.hot.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dylim.hot.member.MemberVO;
import com.dylim.hot.member.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	public void signUpInsert(MemberVO memberVO)throws Exception{
		memberMapper.signUpInsert(memberVO);
	};
	
	public boolean idCheck(String id) throws Exception{

		int chk = memberMapper.idCheck(id);
		
		return chk > 0 ? false : true;
	};
}
