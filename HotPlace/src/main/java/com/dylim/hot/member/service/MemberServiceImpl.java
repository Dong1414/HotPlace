package com.dylim.hot.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dylim.hot.member.MemberVO;
import com.dylim.hot.member.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void signUpInsert(MemberVO memberVO)throws Exception{
		memberVO.setMberPassword(passwordEncoder.encode(memberVO.getMberPassword()));
		memberMapper.signUpInsert(memberVO);
	};
	
	public boolean idCheck(String id) throws Exception{

		int chk = memberMapper.idCheck(id);
		
		return chk > 0 ? false : true;
	};
	
	public MemberVO loadUserByUserId(MemberVO memberVO) throws Exception{
		MemberVO result = memberMapper.loadUserByUserId(memberVO.getMberId());

		if(result == null) {
			return null;
		}else {
			if(!passwordEncoder.matches(memberVO.getMberPassword(), result.getMberPassword())) {
			      System.out.println("비밀번호가 일치하지 않습니다.");
			      return null;
			   }
		}
		
		return result;
	};
}
