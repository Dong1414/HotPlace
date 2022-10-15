package com.dylim.hot.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.dylim.hot.map.service.ReviewService;

@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService seviewService;

	@GetMapping("/")
	public String main2() throws Exception{        
    	System.out.println("aaa");
    	
    	return "views/main";
    }
	
	@GetMapping("/main.do")
	public String main() throws Exception{        
    	System.out.println("ddd");
    	
    	return "views/main";
    }
	
	@GetMapping("/map/getMyMapView.do")
	public String getMyMapView() throws Exception{        
    	System.out.println("ddd");
    	
    	return "views/map/myMap";
    }
	
//    @PostMapping("/saveReview")
//    public void saveReview(@RequestBody ReviewVO reviewVO) throws Exception{        
//    	System.out.println("reviewVO: " + reviewVO.toString());
//    	seviewService.saveReview(reviewVO);
//    }
//     
//    @GetMapping("/getReviews")
//    public List<ReviewVO> getReviews() throws Exception{        
//    	System.out.println(seviewService.getReviews().toString());
//    	
//    	return seviewService.getReviews();
//    }    
}
