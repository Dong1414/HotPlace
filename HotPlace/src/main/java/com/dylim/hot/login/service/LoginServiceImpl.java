package com.dylim.hot.login.service;

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
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private LoginMapper loginMapper;
	
}
