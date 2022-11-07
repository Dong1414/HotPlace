package com.dylim.hot.member.service;

import java.util.List;

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
	
	public MemberVO searchById(MemberVO memberVO) throws Exception{
		return memberMapper.searchById(memberVO);
	};
	
	public String friendRequest(String mberFirstId, String mberSecondId) throws Exception{
		//이미 친구사이인지 확인
		int fChack = memberMapper.friendCheck(mberFirstId,mberSecondId); 
		if(fChack > 0){
			return "이미 친구관계인 사용자 입니다.";
		}
		
		//이미 보낸 신청이 있는지 확인
		int rChack = memberMapper.requestCheck(mberFirstId,mberSecondId);
		if(rChack > 0) {
			return "이미 요청으르 보냈거나 상대가 보낸 요청이 있습니다.";
		}
		//신청 보내기
		memberMapper.friendRequest(mberFirstId,mberSecondId);
		return "요청 보냈습니다.";
	};
	
	public List<MemberVO> friendRequestList(String loginId) throws Exception{
		return memberMapper.friendRequestList(loginId);
	};
	
	public String friendAccept(String loginId, String mberId) throws Exception{
		
		System.out.println(loginId + " , " + mberId);
		//수락 히스토리 기록
		memberMapper.friendAcceptHistory(loginId,mberId);
		//수락 친구 추가
		memberMapper.friendAccept(loginId,mberId);
		
		return "수락되었습니다.";
	};
	
	public List<MemberVO> friendtList(String loginId) throws Exception{
		return memberMapper.friendtList(loginId);
	};
}
