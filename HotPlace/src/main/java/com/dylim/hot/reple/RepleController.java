package com.dylim.hot.reple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dylim.hot.SessionConstants;
import com.dylim.hot.file.FileVO;
import com.dylim.hot.file.service.FileUtilService;
import com.dylim.hot.map.ReviewVO;
import com.dylim.hot.map.service.ReviewService;
import com.dylim.hot.member.MemberVO;
import com.dylim.hot.member.service.MemberService;
import com.dylim.hot.reple.service.RepleService;

@Controller
public class RepleController {
	
	@Autowired
	private ReviewService seviewService;
	@Autowired
	private FileUtilService fileUtilService;
	@Autowired
	private MemberService memberservice;
	@Autowired
	private RepleService repleService;
	
	//등록 
    @PostMapping("/reple/repleInsert.do")    
    @ResponseBody
    public String repleInsert(RepleVO repleVO, HttpServletRequest request) throws Exception{
    	
    	HttpSession session = request.getSession(false);
  	    if(session == null) {
  	    	return "로그인 후 이용할 수 있습니다.";  	    	
  	    }else {
  	    	repleVO.setRegistId((String)session.getAttribute("loginMemberId"));
  	    }
    	
  	    repleService.repleInsert(repleVO);
    	return "등록되었습니다.";
    }
    
    //ajax 조회 
    @PostMapping("/reple/getRepleList.do")    
    @ResponseBody
    public List<RepleVO> repleInsert(HttpServletRequest request) throws Exception{
    	
    	List<RepleVO> repleResults = repleService.getRepleList((String)request.getParameter("reviewId"));
  	    
    	return repleResults;
    }
    
    
	@PostMapping("/reple/repleDelete.do")    
    @ResponseBody
    public String repleDelete(RepleVO repleVO, HttpServletRequest request) throws Exception{
		
		HttpSession session = request.getSession(false);
  	    if(session == null) {
  	    	return "로그인 후 이용할 수 있습니다.";  	    	
  	    }else {
  	    	
  	    	if(repleVO.getRegistId().equals((String)session.getAttribute("loginMemberId"))) {
  	    		repleVO.setRegistId((String)session.getAttribute("loginMemberId"));
  	    	}else {
  	    		return "등록자만 삭제 할 수 있습니다.";
  	    	}
  	    }
  	    
    	repleService.repleDelete(repleVO);
  	    
    	return "삭제되었습니다.";
    }
}