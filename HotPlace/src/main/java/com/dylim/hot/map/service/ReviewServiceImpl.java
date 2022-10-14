package com.dylim.hot.map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dylim.hot.map.ReviewVO;
import com.dylim.hot.map.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	public void saveReview(ReviewVO reviewVO) throws Exception {
		reviewMapper.saveReview(reviewVO);
	}
	
	public List<ReviewVO> getReviews() throws Exception {
		return reviewMapper.getReviews();
	}
}
