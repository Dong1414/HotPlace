package com.dylim.hot.sms;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dylim.hot.sms.service.SmsService;

@Controller
public class SmsController {
	
	@Autowired
	private SmsService smsService;

	//본인확인문자발송
    @PostMapping("/sms")    
    @ResponseBody
    public String sendSms(@RequestBody Map<String,String> param) throws Exception{
    	String phonNum = param.get("phonNum");
    	Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }
        phonNum = phonNum.replace("-","");
        System.out.println("수신자 번호 : " + phonNum);
        System.out.println("인증번호 : " + numStr);
        smsService.certifiedPhoneNumber(phonNum,numStr);
        
    	return numStr;
    }
    
}
