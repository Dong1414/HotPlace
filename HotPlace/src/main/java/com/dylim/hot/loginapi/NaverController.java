package com.dylim.hot.loginapi;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.dylim.hot.SessionConstants;
import com.dylim.hot.member.MemberVO;
import com.dylim.hot.member.service.MemberService;

@Controller
@RequestMapping("/api/naver")
public class NaverController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/oauth")
    public String naverConnect() {
        // state용 난수 생성
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);
        
        // redirect
        StringBuffer url = new StringBuffer();
        url.append("https://nid.naver.com" + "/oauth2.0/authorize?");
        url.append("client_id=" + "BnnFdxOxX_s_KECsc0rX");
        url.append("&response_type=code");
        url.append("&redirect_uri=http://localhost:8082/api/naver/callback");
        url.append("&state=" + state);

        return "redirect:" + url;
    }
	
	@RequestMapping(value = "/callback", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	    public ModelAndView naverLogin(@RequestParam(value = "code") String code, @RequestParam(value = "state") String state
	    		,HttpServletRequest request) throws Exception {
	        ModelAndView mv = new ModelAndView();
	        // 네이버에 요청 보내기
	        WebClient webclient = WebClient.builder()
	            .baseUrl("https://nid.naver.com")
	            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	            .build();

	        JSONObject response = webclient.post()
	            .uri(uriBuilder -> uriBuilder
	                .path("/oauth2.0/token")
	                .queryParam("client_id", "BnnFdxOxX_s_KECsc0rX")
	                .queryParam("client_secret", "q1hTdnKqK1")
	                .queryParam("grant_type", "authorization_code")
	                .queryParam("state", state)
	                .queryParam("code", code)
	                .build())
	            .retrieve().bodyToMono(JSONObject.class).block();
	            
	        // 네이버에서 온 응답에서 토큰을 추출
	        String token = (String) response.get("access_token");
	        MemberVO resultToken = getUserInfo(token);
	        
	        MemberVO loginVO = memberService.snsIdCheck(resultToken);
	        
	        if(loginVO == null) {
	        	mv.addObject("result", resultToken);
		        mv.setViewName("/views/login/connectSignUpView");
	        }else {
	        	HttpSession session = request.getSession();                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
			    session.setAttribute(SessionConstants.LOGIN_MEMBER, loginVO);   // 세션에 로그인 회원 정보 보관
			    session.setAttribute(SessionConstants.LOGIN_MEMBER_ID, loginVO.getMberId());
		        mv.setViewName("redirect:/");
	        }
	        return mv; 
	    }
	
	     public MemberVO getUserInfo(String accessToken) {
	        // 사용자 정보 요청하기
	        WebClient webclient = WebClient.builder()
	            .baseUrl("https://openapi.naver.com")
	            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	            .build();

	        JSONObject response = webclient.get()
	            .uri(uriBuilder -> uriBuilder
	                .path("/v1/nid/me")
	                .build())
	            .header("Authorization", "Bearer " + accessToken)
	            .retrieve()
	            .bodyToMono(JSONObject.class).block();
	        
	        // 원하는 정보 추출하기
	        Map<String, Object> res = (Map<String, Object>) response.get("response");
	        MemberVO memberVO = new MemberVO();
	        String birth = (String)res.get("birthyear") + "-" + (String)res.get("birthday");
	        String tel = (String)res.get("mobile");

	        memberVO.setNaverConnectId((String)res.get("id"));
	        memberVO.setMberNickName((String)res.get("nickname"));
	        memberVO.setMberEmail((String)res.get("email"));
	        memberVO.setMberName((String)res.get("name"));
	        memberVO.setMberTelNo(tel.replace("-", ""));
	        memberVO.setMberBrthd(birth.replace("-", ""));
	        memberVO.setSnsMod("n");
	        return memberVO;
	     }
			
}
