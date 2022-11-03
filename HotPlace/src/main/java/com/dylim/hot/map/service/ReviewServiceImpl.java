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
	
	public ReviewVO getReview(ReviewVO reviewVO) throws Exception {
		return reviewMapper.getReview(reviewVO);
	}
	
	public int getReviewCnt(ReviewVO reviewVO) throws Exception{
		return reviewMapper.getReviewCnt(reviewVO);
	};
	
	public ReviewVO getReview(String id) throws Exception{
		return reviewMapper.getToIdReview(id);
	};
	
	public void modifyReview(ReviewVO reviewVO) throws Exception{
		reviewMapper.modifyReview(reviewVO);
	};
	
	public void deleteReview(ReviewVO reviewVO) throws Exception{
		reviewMapper.deleteReview(reviewVO);
	};
	
	public List<ReviewVO> getReviewPaging(ReviewVO reviewVO) throws Exception{
		return reviewMapper.getReviewPaging(reviewVO);
	};
}
