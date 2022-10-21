package com.dylim.hot.map.service;

import java.util.List;

import com.dylim.hot.map.ReviewVO;

public interface ReviewService {

	void saveReview(ReviewVO reviewVO) throws Exception;

	List<ReviewVO> getReviews() throws Exception;

	List<ReviewVO> getReview(double lat, double lng) throws Exception;
	
}
