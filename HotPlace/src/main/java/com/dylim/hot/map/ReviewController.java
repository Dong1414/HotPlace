package com.dylim.hot.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dylim.hot.SessionConstants;
import com.dylim.hot.file.FileVO;
import com.dylim.hot.file.service.FileUtilService;
import com.dylim.hot.map.service.ReviewService;
import com.dylim.hot.member.MemberVO;
import com.dylim.hot.member.service.MemberService;

@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService seviewService;
	@Autowired
	private FileUtilService fileUtilService;
	@Autowired
	private MemberService memberservice;
	
	@GetMapping("/")
	public String main2() throws Exception{        
    	return "views/main";
    }
	
	@GetMapping("/main.do")
	public String main() throws Exception{        
    	return "views/main";
    }
	
	//지도 보기
	@GetMapping("/map/getMyMapView.do")
	public ModelAndView getMyMapView(ModelAndView mv, 
			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberVO loginMember) throws Exception{
		
		List<ReviewVO> resultList = new ArrayList<ReviewVO>();
		
		if(loginMember != null){
			resultList = getReviews(loginMember.getMberId());
			mv.addObject("loginId", loginMember.getMberId());
		}
		
		mv.addObject("resultList", resultList);
		mv.setViewName("views/map/myMap");
    	return mv;
    }
	
	//친구 지도 보기
	@GetMapping("/map/FriendMapView.do")
	public ModelAndView FriendMapView(ModelAndView mv, MemberVO memberVO,
			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberVO loginMember) throws Exception{
		
		List<ReviewVO> resultList = new ArrayList<ReviewVO>();
		
		if(loginMember!=null) {
			
			boolean tn = memberservice.friendCheck(loginMember.getMberId(), memberVO.getMberId());
			if(tn) {
				resultList = getReviews(memberVO.getMberId());
			}else {
				mv.addObject("msg", "친구관계만 볼 수 있습니다.");
			}
		}
		
		mv.addObject("resultList", resultList);
		mv.setViewName("views/map/FriendMapView");
    	return mv;
    }
	
	//등록된 리뷰 불러오기
    public List<ReviewVO> getReviews(String loginId) throws Exception{
    	return seviewService.getReviews(loginId);
    }   
    
	//저장
    @PostMapping("/map/getMyMapView/saveReview.do")
	@ResponseBody
    public List<ReviewVO> saveReview(@ModelAttribute("searchVO")ReviewVO reviewVO, @RequestParam("attachFileIds") List<MultipartFile> multipartFiles,
    		@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)MemberVO loginMember) throws Exception{
    	
    	reviewVO.setRegistId(loginMember.getMberId());
    	
    	if(!multipartFiles.get(0).isEmpty()) {
    		reviewVO.setAttachFileMasterId(fileUtilService.multiFileUpload(multipartFiles));
    	}
    	seviewService.saveReview(reviewVO);
    	
    	List<ReviewVO> result = getReviews(reviewVO.getRegistId());
    	
    	return result;
    }
    
    //수정 페이지
    @PostMapping("/map/updateReview.do")
    public ModelAndView updateReview(ModelAndView mv, @ModelAttribute("searchVO")ReviewVO reviewVO ) throws Exception{        

    	ReviewVO result = seviewService.getReview(reviewVO.getId());
    	
    	if(Strings.isNotEmpty(result.getAttachFileMasterId()) && result.getAttachFileMasterId() != null) {
    		List<FileVO> files = fileUtilService.getImages(result.getAttachFileMasterId());
    		for(FileVO file : files) {
    			file.setUrl("/file/getImage.do?attachFileId=" + file.getAttachFileId());
    		}
    		mv.addObject("files", files);
    		mv.addObject("fileCnt", 10 - files.size());
    	}else {
    		mv.addObject("fileCnt", 10);
    	}
    	
    	mv.addObject("result", result);
    	mv.setViewName("views/map/reviewModify");
    	return mv;
    }
     
    //수정 
    @PostMapping("/map/updateReview/modifyReview.do")    
    public String modifyReview(ReviewVO reviewVO, HttpServletRequest request) throws Exception{
    	
    	HttpSession session = request.getSession(false);

    	if(!reviewVO.getRegistId().equals((String) session.getAttribute("loginMemberId"))){
    		return "redirect:" + request.getHeader("Referer");
    	};
    	
    	seviewService.modifyReview(reviewVO);
    	return "redirect:/map/getMyMapView.do";
    }
    
    //삭제 
    @PostMapping("/map/updateReview/deleteReview.do")    
    public String deleteReview(ReviewVO reviewVO, HttpServletRequest request) throws Exception{
    	
    	HttpSession session = request.getSession(false);

    	if(!reviewVO.getRegistId().equals((String) session.getAttribute("loginMemberId"))){
    		return "redirect:" + request.getHeader("Referer");
    	};
    	
    	
    	
    	seviewService.deleteReview(reviewVO);
    	return "redirect:/map/getMyMapView.do";
    }
    
    //첫 파일 등록( 수정 시 )
    @PostMapping("/map/updateReview/firstFile.do")    
    @ResponseBody
    public String firstFile(ReviewVO reviewVO) throws Exception{
    	seviewService.firstFile(reviewVO);
    	return null;
    }
    
    //좌표에 해당되는 리뷰 목록 불러오기
    @GetMapping("/map/getMyMapView/getReview.do")
    @ResponseBody
    public Map<String, Object> getReview(HttpServletRequest request) throws Exception{
    	
    	ReviewVO reviewVO = new ReviewVO();
    	reviewVO.setLat(request.getParameter("lat"));
    	reviewVO.setLng(request.getParameter("lng"));
    	if(request.getParameter("pageNo") != null && request.getParameter("pageNo") != "") {
    		reviewVO.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
    	}
    	reviewVO.setPageNo(reviewVO.getPageNo());
    	
    	ReviewVO result = seviewService.getReview(reviewVO); //리뷰 상세 데이터
    	int resultCnt = seviewService.getReviewCnt(reviewVO); //해당 좌표 리뷰 전체 데이터 수
    	
    	int limit = 5;
    	
    	// 총 페이지수
        int maxpage = resultCnt; 

        // 시작 페이지수
        int startpage = ((reviewVO.getPageNo() - 1) / 5) * 5 + 1;

        // 마지막 페이지수
        int endpage = startpage + 5 - 1;

        if (endpage > maxpage) endpage = maxpage;
            
        reviewVO.setStartPage(startpage);
        reviewVO.setEndPage(endpage);

    	List<ReviewVO> resultPaging = seviewService.getReviewPaging(reviewVO); //리뷰 데이터 페이징 넘버
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	if(Strings.isNotEmpty(result.getAttachFileMasterId())) {
    		List<FileVO> files = fileUtilService.getImages(result.getAttachFileMasterId());
    		resultMap.put("files", files);
    	}
    	resultMap.put("resultPaging", resultPaging);
    	resultMap.put("resultCnt", resultCnt);
		resultMap.put("result", result);
		resultMap.put("startpage", startpage);
		resultMap.put("endpage", endpage);
		
    	
    	return resultMap;
    }  
    
    @GetMapping("/wall/timeLineView.do")
    @ResponseBody
    public ModelAndView timeLineView(ModelAndView mv, 
			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberVO loginMember) throws Exception{
    	List<ReviewVO> resultList = new ArrayList<ReviewVO>();
		
		if(loginMember != null){
			resultList = getReviews(loginMember.getMberId());
			mv.addObject("loginId", loginMember.getMberId());
		}
		
		mv.addObject("resultList", resultList);
		mv.setViewName("views/map/myMap");
    	return mv;
    }
    
}
