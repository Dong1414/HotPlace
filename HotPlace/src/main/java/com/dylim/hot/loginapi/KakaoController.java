package com.dylim.hot.loginapi;

import java.io.IOException;
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
@RequestMapping("/api/kakao")
public class KakaoController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/oauth")
    public String naverConnect() {
		System.out.println("aaaaaa");
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + "8daccbe4b0ebc1030985af606321ed80");
        url.append("&redirect_uri=http://localhost:8082/api/kakao/callback");
        url.append("&response_type=code");
        System.out.println("dddddd");
        return "redirect:" + url;
    }
	
	@RequestMapping(value = "/callback", produces = "application/json", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView kakaoLogin(@RequestParam("code") String code, HttpServletRequest request, ModelAndView mv) throws Exception {

        String accessToken = getKakaoAccessToken(code);
        
        MemberVO memberVO = getKakaoUserInfo(accessToken);
        MemberVO loginVO = memberService.snsIdCheck(memberVO);
        
        if(loginVO == null) {
        	mv.addObject("result", memberVO);
 	        mv.setViewName("/views/login/connectSignUpView");
        }else {
        	HttpSession session = request.getSession();                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
 		    session.setAttribute(SessionConstants.LOGIN_MEMBER, loginVO);   // 세션에 로그인 회원 정보 보관
 		    session.setAttribute("loginMemberId", loginVO.getMberId());
 		    session.setAttribute("access_token", accessToken); // 로그아웃할 때 사용된다 (카카오용)
 	        mv.setViewName("redirect:/");
        }
        return mv;
    }
    
   public String getKakaoAccessToken(String code) {
        // 카카오에 보낼 api
        WebClient webClient = WebClient.builder()
            .baseUrl("https://kauth.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        
        // 카카오 서버에 요청 보내기 & 응답 받기
        JSONObject response = webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", "8daccbe4b0ebc1030985af606321ed80")
                .queryParam("redirect_uri", "http://localhost:8082/api/kakao/callback")
                .queryParam("code", code)
                .build())
            .retrieve().bodyToMono(JSONObject.class).block();
            
            return (String) response.get("access_token");
    }
   
   private MemberVO getKakaoUserInfo(String accessToken) {
	   MemberVO memberVO = new MemberVO();

	   // 카카오에 요청 보내기 및 응답 받기
       WebClient webClient = WebClient.builder()
           .baseUrl("https://kapi.kakao.com")
           .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
           .build();
           
       JSONObject response = webClient.post()
           .uri(uriBuilder -> uriBuilder.path("/v2/user/me").build())
           .header("Authorization", "Bearer " + accessToken)
           .retrieve().bodyToMono(JSONObject.class).block();
       
       // 받은 응답에서 원하는 정보 추출하기 (여기의 경우 회원 고유번호와 카카오 닉네임)
       System.out.println(response.toString());
       String id = String.valueOf( response.get("id"));
       Map<String, Object> map = (Map<String, Object>) response.get("kakao_account"); 
       
       Map<String, Object> profile = (Map<String, Object>) map.get("profile");
       String nickname = (String) profile.get("nickname");
       // 추출한 정보로 원하는 처리를 함
       
       memberVO.setKakaoConnectId(id);
       memberVO.setMberNickName(nickname);
       memberVO.setMberName(nickname);
       memberVO.setSnsMod("k");
       
       return memberVO; 
   }
}
