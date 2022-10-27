package com.dylim.hot.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dylim.hot.login.mapper.LoginMapper;
import com.dylim.hot.map.ReviewVO;
import com.dylim.hot.map.mapper.ReviewMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private LoginMapper loginMapper;
	
}
