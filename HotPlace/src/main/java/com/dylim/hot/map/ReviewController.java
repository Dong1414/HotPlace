package com.dylim.hot.map;

import java.math.BigInteger;
import java.security.SecureRandom;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.dylim.hot.reple.RepleVO;
import com.dylim.hot.reple.service.RepleService;

@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private FileUtilService fileUtilService;
	@Autowired
	private MemberService memberservice;
	@Autowired
	private RepleService repleService;
	
	@GetMapping("/")
	public String main2() throws Exception{

    	return "views/main";
    }
	
	@GetMapping("/main")
	public String main() throws Exception{        
    	return "views/main";
    }
	
	//지도 보기
	//@GetMapping("/map/getMyMapView.do") 
	@GetMapping("/map/my-map")
	public ModelAndView getMyMapView(ModelAndView mv, 
			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberVO loginMember) throws Exception{
		List<ReviewVO> resultList = new ArrayList<ReviewVO>();
		
		if(loginMember != null){
			resultList = getReviews(loginMember);
			mv.addObject("loginId", loginMember.getMberId());
		}
		
		mv.addObject("resultList", resultList);
		mv.setViewName("views/map/myMap");
    	return mv;
    }
	
	//친구 지도 보기
	//@GetMapping("/map/FriendMapView.do")
	@GetMapping("/map/friend-map/{memberId}")
	public ModelAndView FriendMapView(ModelAndView mv, @PathVariable String memberId,
			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberVO loginMember) throws Exception{
		List<ReviewVO> resultList = new ArrayList<ReviewVO>();
		MemberVO memberVO = new MemberVO();
		if(loginMember!=null) {
			boolean tf = memberservice.friendCheck(loginMember.getMberId(), memberId);
			memberVO.setMberId(memberId);
			if(tf) {
				memberVO.setIdentiFication("1");
				resultList = getReviews(memberVO);
			}else {
				memberVO.setIdentiFication("2");
				resultList = getReviews(memberVO);
			}
		}else {
			memberVO.setIdentiFication("2");
			resultList = getReviews(memberVO);
		}
		
		mv.addObject("resultList", resultList);
		mv.addObject("mberId", memberVO.getMberId());
		mv.setViewName("views/map/FriendMapView");
    	return mv;
    }
	
	//등록된 리뷰 불러오기
    public List<ReviewVO> getReviews(MemberVO memberVO) throws Exception{
    	return reviewService.getReviews(memberVO);
    }   
    
	//저장
    //@PostMapping("/map/getMyMapView/saveReview.do")
    @PostMapping("/map/my-map/review")
	@ResponseBody
    public List<ReviewVO> saveReview(@ModelAttribute("searchVO")ReviewVO reviewVO, @RequestParam("attachFileIds") List<MultipartFile> multipartFiles,
    		@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)MemberVO loginMember) throws Exception{
    	
    	reviewVO.setRegistId(loginMember.getMberId());
    	
    	if(!multipartFiles.get(0).isEmpty()) {
    		reviewVO.setAttachFileMasterId(fileUtilService.multiFileUpload(multipartFiles, loginMember.getMberId()));
    	}
    	reviewService.saveReview(reviewVO);
    	
    	List<ReviewVO> result = getReviews(loginMember);
    	
    	return result;
    }
    
    //수정 페이지
    //@PostMapping("/map/updateReview.do")
    @GetMapping("/map/my-map/review/{id}")
    public ModelAndView updateReview(ModelAndView mv, @PathVariable String id) throws Exception{        

    	ReviewVO result = reviewService.getReview(id);
    	if(Strings.isNotEmpty(result.getAttachFileMasterId()) && result.getAttachFileMasterId() != null) {
    		List<FileVO> files = fileUtilService.getImages(result.getAttachFileMasterId());
    		for(FileVO file : files) {
    			file.setUrl("/file?attachFileId=" + file.getAttachFileId());
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
    //@PostMapping("/map/updateReview/modifyReview.do")
    @PutMapping("/map/my-map/review/{id}")
    public String modifyReview(@PathVariable String id, @ModelAttribute ReviewVO reviewVO, HttpServletRequest request) throws Exception{
    	HttpSession session = request.getSession(false);

    	if(!reviewVO.getRegistId().equals((String) session.getAttribute("loginMemberId"))){
    		return "redirect:" + request.getHeader("Referer");
    	};
    	reviewVO.setMberId((String)session.getAttribute("loginMemberId"));
    	reviewService.modifyReview(reviewVO);
    	return "redirect:/map/my-map";
    }
    
    //삭제 
    //@PostMapping("/map/updateReview/deleteReview.do")
    @DeleteMapping("/map/my-map/review/{id}")
    public String deleteReview(@PathVariable String id, @ModelAttribute ReviewVO reviewVO, HttpServletRequest request) throws Exception{
    	
    	HttpSession session = request.getSession(false);

    	if(!reviewVO.getRegistId().equals((String) session.getAttribute("loginMemberId"))){
    		return "redirect:" + request.getHeader("Referer");
    	};
    	reviewVO.setMberId((String)session.getAttribute("loginMemberId"));
    	reviewVO.setId(id);
    	reviewService.deleteReview(reviewVO);
    	return "redirect:/map/my-map";
    }
    
    //첫 파일 등록( 수정 시 )
    //@PostMapping("/map/updateReview/firstFile.do")
    @PostMapping("/map/my-map/reviwew/{id}/file")
    @ResponseBody
    public String firstFile(@PathVariable String id, @RequestBody ReviewVO reviewVO) throws Exception{
    	reviewVO.setId(id);
    	reviewService.firstFile(reviewVO);
    	return null;
    }
    
    //좌표에 해당되는 리뷰 목록 불러오기
    //@GetMapping("/map/getMyMapView/getReview.do")
    @GetMapping("/map/my-map/reviews")
    @ResponseBody
    public Map<String, Object> getReview(HttpServletRequest request) throws Exception{
    	
    	ReviewVO reviewVO = new ReviewVO();
    	reviewVO.setLat(request.getParameter("lat"));
    	reviewVO.setLng(request.getParameter("lng"));
    	reviewVO.setRegistId(request.getParameter("registId"));
    	if(request.getParameter("pageNo") != null && request.getParameter("pageNo") != "") {
    		reviewVO.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
    	}
    	reviewVO.setPageNo(reviewVO.getPageNo());
    	
    	ReviewVO result = reviewService.getReview(reviewVO); //리뷰 상세 데이터
    	int resultCnt = reviewService.getReviewCnt(reviewVO); //해당 좌표 리뷰 전체 데이터 수
    	
    	int limit = 2;
    	
    	// 총 페이지수
        int maxpage = resultCnt; 

        // 시작 페이지수
        int startpage = ((reviewVO.getPageNo() - 1) / 5) * 5 + 1;

        // 마지막 페이지수
        int endpage = startpage + 5 - 1;

        if (endpage > maxpage) endpage = maxpage;
            
        reviewVO.setStartPage(startpage);
        reviewVO.setEndPage(endpage);

    	List<ReviewVO> resultPaging = reviewService.getReviewPaging(reviewVO); //리뷰 데이터 페이징 넘버
    	
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
    
    //@GetMapping("/wall/timeLineView.do")
    @GetMapping("/wall/time-lines")
    public ModelAndView timeLineView(ModelAndView mv,HttpServletRequest request) throws Exception{ 
    	ReviewVO reviewVO = new ReviewVO();	
    	HttpSession session = request.getSession(false);
  	    if(session != null) {
  	    	reviewVO.setMberId((String) session.getAttribute("loginMemberId"));  	    	
  	    }
  	    //전체 게시글 수
  	    int resultCnt = reviewService.getTiemLineReviewsCnt(reviewVO); //해당 좌표 리뷰 전체 데이터 수
  	  
  	  	//페이지당 게시물 갯수
  	    int pageRowCount = 4;
  	    //현재 페이지 초기화
	  	int pageNo = 1;
	  	String startpage = request.getParameter("pageNo");
	  	if(startpage != null) {
	  		pageNo = Integer.parseInt(startpage);
	  	}
	  	
	  	//몇번째 게시글부터 가져올건지
	  	int startRowNum = 0 + (pageNo-1) * pageRowCount;
	  	
	  	//몇개씩 가져올건지
	  	int rowCount = pageRowCount;
	  	
	  	//총 몇페이지인지
	  	int totalPageCount = (int) Math.ceil(resultCnt) / (int) pageRowCount;
	  	
	  	reviewVO.setStartRowNum(startRowNum);
	  	reviewVO.setRowCount(rowCount);
	  	
	  	List<ReviewVO> results = reviewService.getTiemLineReviews(reviewVO); //리뷰 상세 데이터
	  	
	  	for(int i = 0; i < results.size(); i++) {
	  		if(results.get(i).getAttachFileMasterId() != null && !results.get(i).getAttachFileMasterId().equals("")) {
	  			List<FileVO> files = fileUtilService.getImages(results.get(i).getAttachFileMasterId());
		  		results.get(i).setFiles(files);
	  		}
	  	}
	  	mv.addObject("totalPageCount", totalPageCount);
	  	mv.addObject("results", results);
	  	mv.addObject("pageNo", pageNo);
	  	mv.addObject("resultCnt", resultCnt);

	  	mv.setViewName("views/wall/timeLineView");
	  	
    	return mv;
    }
    
    
    //ajax
    //@GetMapping("/wall/timeLineAjaxPage.do")
    @GetMapping("/wall/api/time-lines/{pageNo}")
    @ResponseBody
    public Map<String, Object> timeLineAjaxPage(@PathVariable int pageNo) throws Exception{
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	ReviewVO reviewVO = new ReviewVO();	
    	
  	    //전체 게시글 수
  	    int resultCnt = reviewService.getTiemLineReviewsCnt(reviewVO); //해당 좌표 리뷰 전체 데이터 수
  	  
  	  	//페이지당 게시물 갯수
  	    int pageRowCount = 4;
	  	
	  	//몇번째 게시글부터 가져올건지
	  	int startRowNum = 0 + (pageNo-1) * pageRowCount;
	  	
	  	//몇개씩 가져올건지
	  	int rowCount = pageRowCount;
	  	
	  	
	  	reviewVO.setStartRowNum(startRowNum);
	  	reviewVO.setRowCount(rowCount);
	  	
	  	List<ReviewVO> results = reviewService.getTiemLineReviews(reviewVO); //리뷰 상세 데이터
	  	
	  	for(int i = 0; i < results.size(); i++) {
	  		if(results.get(i).getAttachFileMasterId() != null && !results.get(i).getAttachFileMasterId().equals("")) {
	  			List<FileVO> files = fileUtilService.getImages(results.get(i).getAttachFileMasterId());
		  		results.get(i).setFiles(files);
	  		}
	  	}
	  	
	  	resultMap.put("results", results);
	  	resultMap.put("pageNo", pageNo);

    	return resultMap;
    }
    
    //@GetMapping("/wall/timeLineDetailView.do")
    @GetMapping("/wall/time-lines/time-line/{id}")
    public ModelAndView timeLineDetailView(@PathVariable String id, ModelAndView mv,HttpServletRequest request) throws Exception{ 
    	ReviewVO reviewVO = new ReviewVO();	
    	HttpSession session = request.getSession(false);
  	    if(session != null) {
  	    	reviewVO.setMberId((String) session.getAttribute("loginMemberId"));  	    	
  	    }
	  	reviewVO.setId(id);
	  	ReviewVO result = reviewService.getTiemLineReview(reviewVO); //리뷰 상세 데이터
	  	
	  	if(result == null) {
	  		mv.setViewName("redirect:" + request.getHeader("Referer"));
	    	return mv;
	  	}
	  	
  		if(result.getAttachFileMasterId() != null && !result.getAttachFileMasterId().equals("")) {
  			List<FileVO> files = fileUtilService.getImages(result.getAttachFileMasterId());
	  		result.setFiles(files);
	  	}
  		
  		List<RepleVO> repleResults = repleService.getRepleList(result.getId());
  		if(repleResults.size() <= 0) {
  			mv.addObject("repleResults", null);
  		}else {
  			mv.addObject("repleResults", repleResults);
  		}
  		
	  	mv.addObject("result", result);
	  	mv.setViewName("views/wall/timeLineDetailView");
	  	
    	return mv;
    }
}