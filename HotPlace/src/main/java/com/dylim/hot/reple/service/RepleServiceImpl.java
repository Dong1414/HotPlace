package com.dylim.hot.reple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dylim.hot.map.ReviewVO;
import com.dylim.hot.map.mapper.ReviewMapper;
import com.dylim.hot.member.MemberVO;
import com.dylim.hot.reple.RepleVO;
import com.dylim.hot.reple.mapper.RepleMapper;

@Service
public class RepleServiceImpl implements RepleService {
	
	@Autowired
	private RepleMapper repleMapper;
	
	public void repleInsert(RepleVO repleVO) throws Exception{
		repleMapper.repleInsert(repleVO);	
	};
}
