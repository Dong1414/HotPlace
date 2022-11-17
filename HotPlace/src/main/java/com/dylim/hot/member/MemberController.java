package com.dylim.hot.member;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dylim.hot.SessionConstants;
import com.dylim.hot.file.service.FileUtilService;
import com.dylim.hot.mail.GoogleMailController;
import com.dylim.hot.member.service.MemberService;
import com.dylim.hot.sms.service.SmsServiceImpl;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private FileUtilService fileUtilService;
	@Autowired
	private SmsServiceImpl smsServiceImpl;
	
	//회원가입
	@PostMapping("/member/signUpInsert.do")
    public String signUpInsert(MemberVO memberVO, @RequestParam("file") MultipartFile meltipartFile) throws Exception{
		
		if(!meltipartFile.isEmpty()) {
			memberVO.setAttachFileMasterId(fileUtilService.fileUpload(meltipartFile, memberVO.getAttachFileMasterId()));
    	}
		
    	memberService.signUpInsert(memberVO);
    		
    	return "redirect:/map/getMyMapView.do";
    };
    
    @GetMapping("/member/idCheck.do")
	@ResponseBody
	public boolean id(String id) throws Exception{
    	
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
  			return mv;
  			
  		}else {
  			// 로그인 성공 처리
  		    HttpSession session = request.getSession();                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
  		    session.setAttribute(SessionConstants.LOGIN_MEMBER, result);   // 세션에 로그인 회원 정보 보관
  		    session.setAttribute(SessionConstants.LOGIN_MEMBER_ID, result.getMberId());
  			mv.setViewName("redirect:/map/getMyMapView.do"); 
  		}
  		
  		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 년-월-일로만 Format되게 구현
  		LocalDate now = LocalDate.now();
        LocalDate endDate = LocalDate.parse(result.getLastPwDt(), dateTimeFormatter);
        LocalDateTime date1 = now.atStartOfDay();
        LocalDateTime date2 = endDate.atStartOfDay();
        int betweenDays = Math.abs((int) Duration.between(date1, date2).toDays()) ;
  		if(betweenDays > 90) {
  			mv.setViewName("redirect:/member/lastPwDtOverView.do");
  		}
        System.out.println(betweenDays + "aaaaaa");
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
  	public ModelAndView myFriends(ModelAndView mv, HttpServletRequest request, String searchId) throws Exception {
  	    HttpSession session = request.getSession(false);
  	    if (session == null) {
  	    	mv.setViewName("redirect:/");
  	        return mv;
  	    }
  	    mv.setViewName("views/member/myFriendsView");
  	    
  	    MemberVO memberVO = new MemberVO();
	    memberVO.setLoginId((String) session.getAttribute("loginMemberId"));
	    
  	    //나에게 들어온 요청목록
  	    List<MemberVO> results = memberService.friendRequestList(memberVO.getLoginId());
  	    
	  	if(results.isEmpty()) {
	  		mv.addObject("results", null);
	  	}else {
	  		mv.addObject("results", results);
	  	}
  	    //내 친구목록
  	    List<MemberVO> friendResults = memberService.friendtList(memberVO.getLoginId());
  	    if(friendResults.isEmpty()) {
  	    	mv.addObject("friendResults", null);
	  	}else {
	  		mv.addObject("friendResults", friendResults);
	  	}
  	    
  	    if(Strings.isNotEmpty(searchId)) {
	    	memberVO.setMberNickName(request.getParameter("searchId"));
	    	List<MemberVO> resultList = memberService.searchByNickName(memberVO);
	    	
	    	if(resultList.isEmpty()) {
	  	    	mv.addObject("resultList",null);
	  	    }else {
	  	    	mv.addObject("resultList",resultList);
	  	    }
	  	    mv.addObject("searchMod",1);
	  	    mv.addObject("searchId",request.getParameter("searchId"));
	    }
  	    
  	    return mv;
  	}
  	
  	@PostMapping("/member/myFriendsView/friendRequest.do")
  	@ResponseBody
  	public String friendRequest(HttpServletRequest request) throws Exception {
  		
  		HttpSession session = request.getSession(false);
  	    //MemberVO loginVO = (MemberVO) session.getAttribute("loginMemberId");
  	    
  	    String loginId = (String) session.getAttribute("loginMemberId"); //내 아이디
  	    String mberId = request.getParameter("mberId"); //요청 보낼 아이디
  	    
  	    String resultMsg = memberService.friendRequest(loginId, mberId);
  	    
  	    return resultMsg;
  	}
  	
  	@PostMapping("/member/myFriendsView/friendAccept.do")
  	@ResponseBody
  	public String friendAccept(HttpServletRequest request) throws Exception {
  		
  	    HttpSession session = request.getSession(false);
  	    
  	    String loginId = (String) session.getAttribute("loginMemberId"); //내 아이디
  	    String mberId = request.getParameter("mberId"); //수락할 아이디
  	    
  	    String resultMsg = memberService.friendAccept(loginId, mberId);
  	    
  	    return resultMsg;
  	}

  	@PostMapping("/member/myFriendsView/friendNo.do")
  	@ResponseBody
  	public String friendNo(HttpServletRequest request) throws Exception {
  		
  	    HttpSession session = request.getSession(false);
  	    
  	    String loginId = (String) session.getAttribute("loginMemberId"); //내 아이디
  	    String mberId = request.getParameter("mberId"); //수락할 아이디
  	    
  	    String resultMsg = memberService.friendNo(loginId, mberId);
  	    
  	    return resultMsg;
  	}

  	@PostMapping("/member/myFriendsView/friendDel.do")
  	@ResponseBody
  	public String friendDel(HttpServletRequest request) throws Exception {
  		
  	    HttpSession session = request.getSession(false);
  	    
  	    String loginId = (String) session.getAttribute("loginMemberId"); //내 아이디
  	    String mberId = request.getParameter("mberId"); //수락할 아이디
  	    
  	    String resultMsg = memberService.friendDel(loginId, mberId);
  	    
  	    return resultMsg;
  	}  	
  	
  	//sns회원가입
  	@PostMapping("/member/snsSignUp")
  	public ModelAndView snsSignUp(ModelAndView mv, MemberVO memberVO, HttpServletRequest request) throws Exception {
  		int min_val = 10000000;
        int max_val = 99999999;
        SecureRandom rand = new SecureRandom();
        memberVO.setMberId(rand.nextInt((max_val - min_val) + 1) + min_val + "@" + memberVO.getSnsMod());
  			
  	    memberService.signUpInsert(memberVO);
  	    
  	    MemberVO loginVO = memberService.loadUserByUserId(memberVO);
  	    
  	  	if(loginVO != null) {
	  	  	HttpSession session = request.getSession();                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
		    session.setAttribute(SessionConstants.LOGIN_MEMBER, loginVO);   // 세션에 로그인 회원 정보 보관
		    session.setAttribute(SessionConstants.LOGIN_MEMBER_ID, loginVO.getMberId());
  	  	}
		mv.setViewName("redirect:/"); 
  	    return mv;
  	}
  	
	@GetMapping("/member/myPage.do")
  	public ModelAndView myPage(ModelAndView mv, HttpServletRequest request, String searchId) throws Exception {
  	    HttpSession session = request.getSession(false);
  	    if (session == null) {
  	    	mv.setViewName("redirect:/");
  	        return mv;
  	    }
  	    mv.setViewName("views/member/myPage");
  	    
  	    MemberVO memberVO = new MemberVO();
	    memberVO.setLoginId((String) session.getAttribute("loginMemberId"));
	    
	    memberVO = memberService.getByUserId(memberVO);
	    
	    if(!Strings.isEmpty(memberVO.getMberTelNo())) {
	    	String[] phonNum = memberVO.getMberTelNo().split("-");
	    	mv.addObject("phonNum", phonNum);
	    }
	    if(!Strings.isEmpty(memberVO.getMberBrthd())) {
	    	String[] brthd = memberVO.getMberBrthd().split("-");
	    	mv.addObject("brthd", brthd);
	    }
	    if(!Strings.isEmpty(memberVO.getAttachFileId())) {
	    	memberVO.setAttachFileUrl("/file/getImage.do?attachFileId="+memberVO.getAttachFileId());
	    }
	    
	    LocalDate now = LocalDate.now();
  		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
  		
  		int formatedNow = Integer.parseInt(now.format(formatter)) - 100;
  		
  		mv.addObject("year", formatedNow);  		
	    mv.addObject("member", memberVO);
	    
	    
  	    
  	    return mv;
  	}
  		
  		
	//내 정보 수정
	@PostMapping("/member/myPage/memberModify.do")
	public ModelAndView memberModify(ModelAndView mv, MemberVO memberVO, @RequestParam("file") MultipartFile meltipartFile,
			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER_ID, required = false) String loginMemberId) throws Exception {
		memberVO.setMberId(loginMemberId);
		if(!meltipartFile.isEmpty()) {
			System.out.println("bbb");
			fileUtilService.fileUpload(meltipartFile, memberVO.getAttachFileMasterId());
			System.out.println("ddd");
			fileUtilService.deleteImage(memberVO.getAttachFileId());
			System.out.println("Ccc");
    	}
		
		memberService.memberModify(memberVO);
		
		mv.setViewName("redirect:/"); 
		    return mv;
	}
	
	@GetMapping("/member/findLoginIdView.do")
  	public ModelAndView findLoginIdView(ModelAndView mv) throws Exception {
		mv.setViewName("views/login/findIdView");
  	    return mv;
  	}
	
	@GetMapping("/member/findLoginId.do")
  	public ModelAndView findLoginId(ModelAndView mv, MemberVO memberVO) throws Exception {
		
		MemberVO result = memberService.findLoginId(memberVO);
		if(Objects.isNull(result)) {
			mv.addObject("msg", "입력하신 정보로 가입 된 회원 아이디는 존재하지 않습니다.");
			mv.setViewName("views/login/findIdView");
			return mv;
		};
		
		if(!Strings.isEmpty(memberVO.getNaverConnectId())) {
			mv.addObject("msg", "네이버 아이디로 가입된 회원입니다. 네이버 로그인을 이용해주세요.");
			mv.setViewName("views/login/findPwView");
			return mv;
		}
		if(!Strings.isEmpty(memberVO.getKakaoConnectId())) {
			mv.addObject("msg", "카카오 아이디로 가입된 회원입니다. 카카오 로그인을 이용해주세요.");
			mv.setViewName("views/login/findPwView");
			return mv;
		}
		
		mv.addObject("result", result);
		mv.setViewName("views/login/findResultIdView");
  	    return mv;
  	}
	
	@GetMapping("/member/findLoginPwView.do")
  	public ModelAndView findLoginPwView(ModelAndView mv) throws Exception {
		mv.setViewName("views/login/findPwView");
  	    return mv;
  	}
	
	@PostMapping("/member/findLoginPw.do")
  	public ModelAndView findLoginPw(ModelAndView mv, MemberVO memberVO) throws Exception {
		
		MemberVO result = memberService.findLoginId(memberVO);
		if(Objects.isNull(result)) {
			mv.addObject("msg", "입력하신 정보로 가입 된 회원 아이디는 존재하지 않습니다.");
			mv.setViewName("views/login/findPwView");
			return mv;
		};
		
		if(!Strings.isEmpty(memberVO.getNaverConnectId())) {
			mv.addObject("msg", "네이버 아이디로 가입된 회원입니다. 네이버 로그인을 이용해주세요.");
			mv.setViewName("views/login/findPwView");
			return mv;
		}
		if(!Strings.isEmpty(memberVO.getKakaoConnectId())) {
			mv.addObject("msg", "카카오 아이디로 가입된 회원입니다. 카카오 로그인을 이용해주세요.");
			mv.setViewName("views/login/findPwView");
			return mv;
		}
		
		if(!Strings.isEmpty(memberVO.getMberEmail()) && result.getEmailYn().equals("Y")) {
            String pw = getRamdomPassword(12);
            memberVO.setMberPassword(pw);
            memberService.changePw(memberVO);
			String msg = GoogleMailController.gmailSendPw(memberVO.getMberEmail(), pw);
			
		}else if(!Strings.isEmpty(memberVO.mberTelNo) && result.getTelYn().equals("Y")) { 
			String pw = getRamdomPassword(12);
            memberVO.setMberPassword(pw);
            memberService.changePw(memberVO);
            memberVO.setMberTelNo(memberVO.getMberTelNo().replace("-", ""));
			smsServiceImpl.certifiedPw(memberVO.getMberTelNo(), pw);
		}
		
		mv.addObject("result", result);
		mv.setViewName("views/login/findResultPwView");
  	    return mv;
  	}
	
	@PostMapping("/member/myPage/memberPwModifyView.do")
  	public ModelAndView memberPwModifyView(ModelAndView mv, MemberVO memberVO) throws Exception {
		mv.setViewName("views/member/memberPwModifyView");
  	    return mv;
  	}

	@PostMapping("/member/pwCheck.do")
	@ResponseBody
  	public boolean pwCheck(ModelAndView mv, MemberVO memberVO,
  			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER_ID, required = false) String loginMemberId) throws Exception {
		memberVO.setMberId(loginMemberId);
		MemberVO result = memberService.loadUserByUserId(memberVO);
		
		if(result == null) {
			System.out.println("tlfvo");
			return false;
		}
		System.out.println("akwdma");
  	    return true;
  	}

	@PostMapping("/member/myPage/memberPwModify.do")
  	public String memberPwModify(MemberVO memberVO, HttpServletRequest request,
  			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER_ID, required = false) String loginMemberId) throws Exception {
		memberVO.setMberId(loginMemberId);
		memberService.changePw(memberVO);
		System.out.println("asdasdasd");
		HttpSession session = request.getSession(false);
  	    if (session != null) {
  	        session.invalidate();   // 세션 날림
  	    } 
  	    return "redirect:/";
  	}
	
	
	@GetMapping("/member/lastPwDtOverView.do")
  	public ModelAndView lastPwDtOverView(ModelAndView mv) throws Exception {
		mv.setViewName("views/member/lastPwDtOverView");
  	    return mv;
  	}
	
	//임시 비밀번호 생성기
	public String getRamdomPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }
	
}
