package com.dylim.hot.reple.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dylim.hot.reple.RepleVO;

@Mapper
public interface RepleMapper {
	
	void repleInsert(RepleVO rpleVO) throws Exception;

}
