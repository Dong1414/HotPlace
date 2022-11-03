package com.dylim.hot.map;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dylim.hot.file.FileVO;
import com.dylim.hot.file.service.FileUtilService;
import com.dylim.hot.map.service.ReviewService;
import com.dylim.hot.member.MemberVO;

@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService seviewService;
	@Autowired
	private FileUtilService fileUtilService;	

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
	public ModelAndView getMyMapView(ModelAndView mv) throws Exception{        
		List<ReviewVO> resultList = getReviews();
		mv.addObject("resultList", resultList);
		
		mv.setViewName("views/map/myMap");
    	return mv;
    }
	
	//저장
    @PostMapping("/map/saveReview.do")
	@ResponseBody
    public List<ReviewVO> saveReview(@ModelAttribute("searchVO")ReviewVO reviewVO, @RequestParam("attachFileIds") List<MultipartFile> multipartFiles) throws Exception{        
    	System.out.println("reviewVO: " + reviewVO.toString());
    	
    	if(!multipartFiles.get(0).isEmpty()) {
    		reviewVO.setAttachFileMasterId(fileUtilService.multiFileUpload(multipartFiles));
    	}
    	seviewService.saveReview(reviewVO);
    	
    	List<ReviewVO> result = getReviews();
    	
    	return result;
    }
    
    //등록된 리뷰 불러오기
    @GetMapping("/map/getReviews.do")
    public List<ReviewVO> getReviews() throws Exception{
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	//HttpServletRequest request = servletContainer.getRequest();
    	HttpSession session = request.getSession();
    	MemberVO loginVO = (MemberVO) session.getAttribute("loginMember");
    	
    	return seviewService.getReviews(loginVO.getMberId());
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
    		
    	}
    	System.out.println(result.toString());
    	mv.addObject("result", result);
    	mv.setViewName("views/map/reviewModify");
    	return mv;
    }    
    
    //수정 
    @PostMapping("/map/updateReview/modifyReview.do")    
    public String modifyReview(ReviewVO reviewVO) throws Exception{
    	seviewService.modifyReview(reviewVO);
    	return "redirect:/map/getMyMapView.do";
    }
    
    //삭제 
    @PostMapping("/map/updateReview/deleteReview.do")    
    public String deleteReview(ReviewVO reviewVO) throws Exception{
    	seviewService.deleteReview(reviewVO);
    	return "redirect:/map/getMyMapView.do";
    }
    
    //좌표에 해당되는 리뷰 목록 불러오기
    @GetMapping("/map/getReview.do")
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
        System.out.println(reviewVO.getPageNo());
        System.out.println(startpage);
        System.out.println(endpage);

    	List<ReviewVO> resultPaging = seviewService.getReviewPaging(reviewVO); //리뷰 데이터 페이징 넘버
    	
    	System.out.println(result.toString());
    	System.out.println(resultPaging.toString());
    	
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
    
}
