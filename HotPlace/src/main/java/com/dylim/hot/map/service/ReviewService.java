package com.dylim.hot.map.service;

import java.util.List;
import java.util.Map;

import com.dylim.hot.map.ReviewVO;
import com.dylim.hot.member.MemberVO;

public interface ReviewService {

	void saveReview(ReviewVO reviewVO) throws Exception;

	List<ReviewVO> getReviews(MemberVO memberVO) throws Exception;

	ReviewVO getReview(ReviewVO reviewVO) throws Exception;

	int getReviewCnt(ReviewVO reviewVO) throws Exception;

	ReviewVO getReview(String id) throws Exception;

	void modifyReview(ReviewVO reviewVO) throws Exception;

	void deleteReview(ReviewVO reviewVO) throws Exception;

	List<ReviewVO> getReviewPaging(ReviewVO reviewVO) throws Exception;

	void firstFile(ReviewVO reviewVO) throws Exception;

	List<ReviewVO> getTiemLineReviews(ReviewVO reviewVO) throws Exception;

	int getTiemLineReviewsCnt(ReviewVO reviewVO) throws Exception;

	ReviewVO getTiemLineReview(ReviewVO reviewVO) throws Exception;
	
}
