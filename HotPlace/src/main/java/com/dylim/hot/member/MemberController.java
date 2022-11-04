package com.dylim.hot.member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dylim.hot.SessionConstants;
import com.dylim.hot.member.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	//회원가입
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
    
    //로그인화면
  	@GetMapping("/login/loginView.do")
  	public ModelAndView loginView(ModelAndView mv) throws Exception{        
  		mv.setViewName("views/login/loginView"); 
  		return mv; 
	}

  	//로그인기능
  	@PostMapping("/login/loginView/loginAction.do")
  	public ModelAndView loginAction(ModelAndView mv, MemberVO memberVO, HttpServletRequest request) throws Exception{        
  		
  		MemberVO result = memberService.loadUserByUserId(memberVO);
  		
  		if(result == null) {
  			mv.addObject("loginMsg", "아이디 또는 비밀번호가 일치하지않습니다.");
  			mv.addObject("mberId", memberVO.getMberId());
  			mv.setViewName("views/login/loginView");
  			
  		}else {
  			// 로그인 성공 처리
  		    HttpSession session = request.getSession();                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
  		    session.setAttribute(SessionConstants.LOGIN_MEMBER, result);   // 세션에 로그인 회원 정보 보관
  		    session.setAttribute("loginMemberId", result.getMberId());
  			mv.setViewName("redirect:/map/getMyMapView.do"); 
  		}
  		return mv;
  	}
  	
  		
  	//회원가입 화면
  	@GetMapping("/login/signUpView.do")
  	public ModelAndView signUpView(ModelAndView mv) throws Exception{        
  		LocalDate now = LocalDate.now();
  		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
  		
  		int formatedNow = Integer.parseInt(now.format(formatter)) - 100;
  		
  		mv.addObject("year", formatedNow);
  		mv.setViewName("views/login/signUpView"); 
      	return mv; 
      }
  	
  	@PostMapping("/logout.do")
  	public String logout(HttpServletRequest request) {
  	    HttpSession session = request.getSession(false);
  	    if (session != null) {
  	        session.invalidate();   // 세션 날림
  	    }
  	    return "redirect:/";
  	}
  	@GetMapping("/member/myFriendsView.do")
  	public ModelAndView myFriends(ModelAndView mv, HttpServletRequest request) throws Exception {
  	    HttpSession session = request.getSession(false);
  	    if (session == null) {
  	    	mv.setViewName("redirect:/");
  	        return mv;
  	    }
  	    System.out.println("aaaaaaaaaqqqqqqqqqqq");
  	  System.out.println((String) session.getAttribute("loginMemberId"));
  	    //나에게 들어온 요청목록
  	    List<MemberVO> results = memberService.friendRequestList((String) session.getAttribute("loginMemberId"));
  	    for(MemberVO asd : results) {
  	    	
  	    	System.out.println("목록 : " + asd.getMberId());
  	    }
  	
  	    mv.addObject("results", results);
  	  System.out.println("aaaaaasdasdadqq");
  	    mv.setViewName("views/member/myFriendsView");
  	    return mv;
  	}
  	
  	@GetMapping("/member/myFriendsView/searchFriends.do")
  	public ModelAndView searchFriends(ModelAndView mv, HttpServletRequest request) throws Exception {
  	    HttpSession session = request.getSession(false);
  	    if (session == null) {
  	    	mv.setViewName("redirect:/");
  	        return mv;
  	    }
  	    
  	    MemberVO memberVO = new MemberVO();
  	    memberVO.setMberId(request.getParameter("searchId"));
  	    memberVO.setLoginId((String) session.getAttribute("loginMemberId"));
  	    MemberVO result = memberService.searchById(memberVO);
  	    
  	    if(Objects.isNull(result)) {
  	    	mv.addObject("result",null);
  	    }else {
  	    	mv.addObject("result",result);
  	    }
  	    
  	    mv.addObject("searchMod",1);
  	    mv.addObject("searchId",request.getParameter("searchId"));
  	    mv.setViewName("views/member/myFriendsView");
  	    return mv;
  	}
  	
  	@PostMapping("/member/myFriendsView/friendRequest.do")
  	@ResponseBody
  	public String friendRequest(HttpServletRequest request) throws Exception {
  		
  	    HttpSession session = request.getSession();
  	    //MemberVO loginVO = (MemberVO) session.getAttribute("loginMemberId");
  	    
  	    String mberFirstId = (String) session.getAttribute("loginMemberId"); //내 아이디
  	    String mberSecondId = request.getParameter("mberId"); //요청 보낼 아이디
  	    
  	    String resultMsg = memberService.friendRequest(mberFirstId, mberSecondId);
  	    
  	    return resultMsg;
  	}
  	
  	@PostMapping("/member/myFriendsView/friendAccept.do")
  	@ResponseBody
  	public String friendAccept(HttpServletRequest request) throws Exception {
  		
  	    HttpSession session = request.getSession();
  	    
  	    String loginId = (String) session.getAttribute("loginMemberId"); //내 아이디
  	    String mberId = request.getParameter("mberId"); //수락할 아이디
  	    
  	    String resultMsg = memberService.friendAccept(loginId, mberId);
  	    
  	    return resultMsg;
  	}
}
