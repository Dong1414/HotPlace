package com.dylim.hot.map.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public ReviewVO getReview(double lat, double lng) throws Exception {
		return reviewMapper.getReview(lat, lng);
	}
	
	public List<ReviewVO> getReviewCnt(double lat, double lng) throws Exception{
		return reviewMapper.getReviewCnt(lat, lng);
	};
}
