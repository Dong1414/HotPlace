package com.dylim.hot.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dylim.hot.member.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	
	@PostMapping("/member/signUpInsert.do")
    public String signUpInsert(MemberVO memberVO) throws Exception{
    	
    	memberService.signUpInsert(memberVO);
    		
    	return "redirect:/map/getMyMapView.do";
    };
    
    @GetMapping("/member/idCheck.do")
	@ResponseBody
	public boolean id(String id) throws Exception{
    	System.out.println("aaaaaaaa " + id);
    	
    	boolean result = memberService.idCheck(id);
    	return result;
    }
}
