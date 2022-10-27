package com.dylim.hot.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dylim.hot.login.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	//로그인화면
	@GetMapping("/login/loginView.do")
	public ModelAndView loginView(ModelAndView mv) throws Exception{        
		 
		mv.setViewName("views/login/loginView"); 
    	return mv; 
    }

	//회원가입 화면
	@GetMapping("/login/signUpView.do")
	public ModelAndView signUpView(ModelAndView mv) throws Exception{        
		 
		mv.setViewName("views/login/signUpView"); 
    	return mv; 
    }	
}
