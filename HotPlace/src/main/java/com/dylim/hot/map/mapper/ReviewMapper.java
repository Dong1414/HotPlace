package com.dylim.hot.map.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dylim.hot.map.ReviewVO;

@Mapper
public interface ReviewMapper {
	
	void saveReview(ReviewVO reviewVO) throws Exception;

	List<ReviewVO> getReviews() throws Exception;

	List<ReviewVO> getReview(double lat, double lng) throws Exception;
}
