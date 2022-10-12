package com.dylim.hot.map.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dylim.hot.map.ReviewVO;

@Mapper
public interface ReviewMapper {
	
	void saveReview(ReviewVO reviewVO) throws Exception;
}
