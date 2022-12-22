package com.dylim.hot.sms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dylim.hot.map.ReviewVO;
import com.dylim.hot.map.mapper.ReviewMapper;

import org.json.simple.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class SmsServiceImpl implements SmsService {
	
	
	public void certifiedPhoneNumber(String phonNum, String numStr) throws Exception {
		String api_key = "NCSNREH0ZRINIUBV";
        String api_secret = "K8AD9W9AX0LYTWPA2SOFSYHOOD9JINGT";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phonNum);    // 수신전화번호
        params.put("from", "01041825878");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "TripDariy 본인인증번호 : 인증번호는" + "["+numStr+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version
        System.out.println(params.toString());
        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

	}
	
	public void certifiedPw(String phonNum, String numStr) throws Exception {
		System.out.println(phonNum);
		System.out.println(numStr);
		
		String api_key = "NCSNREH0ZRINIUBV";
        String api_secret = "K8AD9W9AX0LYTWPA2SOFSYHOOD9JINGT";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phonNum);    // 수신전화번호
        params.put("from", "01041825878");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "TripDariy 임시비밀번호 : 임시비밀번호는" + "["+numStr+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

	}	
	
}
