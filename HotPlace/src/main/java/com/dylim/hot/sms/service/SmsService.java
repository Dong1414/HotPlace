package com.dylim.hot.sms.service;

import java.util.List;
import java.util.Map;

import com.dylim.hot.map.ReviewVO;

public interface SmsService {

	void certifiedPhoneNumber(String phonNum, String numStr) throws Exception;
	
}
