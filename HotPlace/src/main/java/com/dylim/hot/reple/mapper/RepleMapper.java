package com.dylim.hot.reple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dylim.hot.reple.RepleVO;

@Mapper
public interface RepleMapper {
	
	void repleInsert(RepleVO rpleVO) throws Exception;

	List<RepleVO> getRepleList(String id) throws Exception;

	void repleDelete(RepleVO id) throws Exception;

}
