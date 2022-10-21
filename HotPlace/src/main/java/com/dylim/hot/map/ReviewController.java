package com.dylim.hot.map;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dylim.hot.file.service.FileUtilService;
import com.dylim.hot.map.service.ReviewService;

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
    	return seviewService.getReviews();
    }    
    
    //좌표에 해당되는 리뷰 목록 불러오기
    @GetMapping("/map/getReview.do")
    @ResponseBody
    public List<ReviewVO> getReview(HttpServletRequest request) throws Exception{
    	return seviewService.getReview(Double.parseDouble(request.getParameter("lat")), Double.parseDouble(request.getParameter("lng")));
    }  
}
