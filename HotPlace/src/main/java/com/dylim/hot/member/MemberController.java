package com.dylim.hot.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dylim.hot.login.service.LoginService;

@Controller
public class MemberController {
	
	@Autowired
	private LoginService loginService;

}
