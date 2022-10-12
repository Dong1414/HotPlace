package com.dylim.hot.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dylim.hot.map.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	@Autowired
	private ReviewService seviewService;


    @PostMapping("/saveReview")
    public void saveReview(@RequestBody ReviewVO reviewVO) throws Exception{        
    	System.out.println("reviewVO: " + reviewVO.toString());
    	seviewService.saveReview(reviewVO);
    }
}
