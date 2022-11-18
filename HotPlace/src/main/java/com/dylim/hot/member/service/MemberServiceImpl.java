package com.dylim.hot.member.service;

import java.util.List;

import org.apache.groovy.parser.antlr4.util.StringUtils;
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
		if(!StringUtils.isEmpty(memberVO.getMberPassword())) {
			memberVO.setMberPassword(passwordEncoder.encode(memberVO.getMberPassword()));
		}
		memberMapper.signUpInsert(memberVO);
	};
	
	public boolean idCheck(String id) throws Exception{

		int chk = memberMapper.idCheck(id);
		
		return chk > 0 ? false : true;
	};
	
	public MemberVO loadUserByUserId(MemberVO memberVO) throws Exception{
		MemberVO result = new MemberVO();
		
		if(StringUtils.isEmpty(memberVO.getSnsMod())) {
			result = memberMapper.loadUserByUserId(memberVO);

			if(result == null) {
				return null;
			}else {
				if(!passwordEncoder.matches(memberVO.getMberPassword(), result.getMberPassword())) {
				      System.out.println("비밀번호가 일치하지 않습니다.");
				      return null;
				   }
			}
		}else {
			result = memberMapper.loadUserByUserId(memberVO);
		}
				
		return result;
	};
	
	public List<MemberVO> searchByNickName(MemberVO memberVO) throws Exception{
		return memberMapper.searchByNickName(memberVO);
	};
	
	public String friendRequest(String loginId, String mberId) throws Exception{
		//이미 친구사이인지 확인
		int fChack = memberMapper.friendCheck(loginId,mberId); 
		if(fChack > 0){
			return "이미 친구관계인 사용자 입니다.";
		}
		System.out.println(fChack + "이미친구");
		//이미 보낸 신청이 있는지 확인
		int rChack = memberMapper.requestCheck(loginId,mberId);
		if(rChack > 0) {
			return "상대가 보낸 신청이 있습니다.";
		}
		System.out.println(fChack + "신청있음");
		//신청 보내기
		memberMapper.friendRequest(loginId,mberId);
		return "친구신청을 보냈습니다.";
	};
	
	public List<MemberVO> friendRequestList(String loginId) throws Exception{
		return memberMapper.friendRequestList(loginId);
	};
	
	public String friendAccept(String loginId, String mberId) throws Exception{
		
		//요청 삭제
		memberMapper.friendDelHistory(loginId,mberId);
		//수락 친구 추가
		memberMapper.friendAccept(loginId,mberId);
		
		return "수락되었습니다.";
	};
	
	public List<MemberVO> friendtList(String loginId) throws Exception{
		return memberMapper.friendtList(loginId);
	};

	public String friendNo(String loginId, String mberId) throws Exception{
		
		//요청 삭제
		memberMapper.friendDelHistory(loginId,mberId);
		
		return "거절되었습니다.";
	};

	public String friendDel(String loginId, String mberId) throws Exception{
		
		//친구 삭제
		memberMapper.friendDel(loginId,mberId);
		
		return mberId + "님과 친구관계를 끊었습니다.";
	};
	
	public boolean friendCheck(String loginId, String mberId) throws Exception{
		int fChack = memberMapper.friendCheck(loginId,mberId); 
		return fChack > 0 ? true : false;
	};
	
	public MemberVO snsIdCheck(MemberVO resultToken) throws Exception{
		MemberVO result = memberMapper.snsIdCheck(resultToken);
		if(result == null) {
			return null;
		}
			
		return result; 
	};
	
	public MemberVO getByUserId(MemberVO memberVO) throws Exception{
		return memberMapper.getByUserId(memberVO);
	};

	public void memberModify(MemberVO memberVO) throws Exception{
		memberMapper.memberModify(memberVO);
	};
	
	public MemberVO findLoginId(MemberVO memberVO) throws Exception{
		return memberMapper.findLoginId(memberVO);
	};
	
	public void changePw(MemberVO memberVO) throws Exception{
		memberVO.setMberPassword(passwordEncoder.encode(memberVO.getMberPassword()));
		memberMapper.changePw(memberVO);
	};
	
	public void deleteMember(MemberVO memberVO) throws Exception{
		memberMapper.deleteMember(memberVO);
	};
}
