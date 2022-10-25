package com.dylim.hot.map.service;

import java.util.List;
import java.util.Map;

import com.dylim.hot.map.ReviewVO;

public interface ReviewService {

	void saveReview(ReviewVO reviewVO) throws Exception;

	List<ReviewVO> getReviews() throws Exception;

	ReviewVO getReview(double lat, double lng) throws Exception;

	List<ReviewVO> getReviewCnt(double lat, double lng) throws Exception;

	ReviewVO getReview(String id) throws Exception;

	void modifyReview(ReviewVO reviewVO) throws Exception;
	
}
