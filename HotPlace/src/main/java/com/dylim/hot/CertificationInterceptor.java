package com.dylim.hot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dylim.hot.member.MemberVO;

@Component
public class CertificationInterceptor implements HandlerInterceptor{
	
	//클라이언트의 요청을 컨트롤러에 전달하기 전에 호출된다. 여기서 false를 리턴하면 다음 내용(Controller)을 실행하지 않는다.
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
		System.out.println("aaaaaaaaaaaaaaaaa");
		
        HttpSession session = request.getSession();
        MemberVO loginVO = (MemberVO) session.getAttribute("loginMember");
        
        if(ObjectUtils.isEmpty(loginVO)){
//            response.sendRedirect("/login/loginView.do");
            return true;
        }else{ 
            session.setMaxInactiveInterval(30*60);
            return false;
        }
        
    }
 
	//controller 처리 이후 이벤트 작동
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        
    }
	
	//view 처리 이후 이벤트 작동
    @Override	
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        
    }
}
