package com.dylim.hot.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dylim.hot.member.service.MemberService;

@Controller
@RequestMapping("/mail")
public class GoogleMailController {
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/gmailSend.do")
	@ResponseBody
	public static String gmailSend(String fromMail) {
        String user = "ghksrlwja1@gmail.com"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
        String password = "uqjsaklleinbjgzt";   // 패스워드
        System.out.println(fromMail);
        // SMTP 서버 정보를 설정한다.
        Properties prop = new Properties();
        
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.enable", "false");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        System.out.println("aaaaaa");
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(fromMail)); 
            // Subject
            message.setSubject("TripDiary : 이메일 인증번호입니다."); //메일 제목을 입력
            // Text
            message.setText("인증번호: 1234");    //메일 내용을 입력
            // send the message
            Transport.send(message); ////전송
            System.out.println("message sent successfully...");
            return "1234";
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
