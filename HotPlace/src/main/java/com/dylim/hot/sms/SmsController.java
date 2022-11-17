package com.dylim.hot.sms;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dylim.hot.sms.service.SmsService;

@Controller
public class SmsController {
	
	@Autowired
	private SmsService smsService;

	//본인확인문자발송
    @PostMapping("/sms/sendSms.do")    
    @ResponseBody
    public String sendSms(String phonNum) throws Exception{
    	
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
