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
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public String kakaoConnect() {
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + "8daccbe4b0ebc1030985af606321ed80");
        url.append("&redirect_uri=http://www.tripdiary.site/api/kakao/callback");
        //url.append("&redirect_uri=http://localhost:8080/api/kakao/callback");
        url.append("&response_type=code");
        return "redirect:" + url;
    }
	
	@DeleteMapping("/oauth/logout")
	public String kakaoLogoutConnect() {
		///oauth/logout?client_id=${REST_API_KEY}
		StringBuffer url = new StringBuffer();
		url.append("https://kauth.kakao.com/oauth/logout?");
        url.append("client_id=" + "8daccbe4b0ebc1030985af606321ed80");
        url.append("&logout_redirect_uri=http://www.tripdiary.site/api/kakao/auth/logout/callback");
        //url.append("&logout_redirect_uri=http://localhost:8080/api/kakao/auth/logout/callback");
        
        return "redirect:" + url;
	}
	
	@RequestMapping("/auth/logout/callback")
	public ModelAndView kakaoLogout(ModelAndView mv, HttpServletRequest request){
		HttpSession session = request.getSession(false);
  	    if (session != null) {
  	        session.invalidate();   // ?????? ??????
  	    }
  	    mv.setViewName("redirect:/");
  	    return mv;
	}
	
	@RequestMapping(value = "/callback", produces = "application/json", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView kakaoLogin(@RequestParam("code") String code, HttpServletRequest request, ModelAndView mv) throws Exception {
        String accessToken = getKakaoAccessToken(code);
        MemberVO memberVO = getKakaoUserInfo(accessToken);
        MemberVO loginVO = memberService.snsIdCheck(memberVO);
        if(loginVO == null) {
        	System.out.println(memberVO.toString());
        	mv.addObject("result", memberVO);
 	        mv.setViewName("views/login/connectSignUpView");
        }else {
        	HttpSession session = request.getSession();                         // ????????? ????????? ?????? ?????? ??????, ????????? ?????? ????????? ???????????? ??????
 		    session.setAttribute(SessionConstants.LOGIN_MEMBER, loginVO);   // ????????? ????????? ?????? ?????? ??????
 		    session.setAttribute(SessionConstants.LOGIN_MEMBER_ID, loginVO.getMberId());
 		    //session.setAttribute("access_token", accessToken); // ??????????????? ??? ???????????? (????????????)
 	        mv.setViewName("redirect:/");
        }
        return mv;
    }
    
   public String getKakaoAccessToken(String code) {
        // ???????????? ?????? api
        WebClient webClient = WebClient.builder()
            .baseUrl("https://kauth.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        
        // ????????? ????????? ?????? ????????? & ?????? ??????
        JSONObject response = webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", "8daccbe4b0ebc1030985af606321ed80")
                //.queryParam("redirect_uri", "http://www.tripdiary.site/api/kakao/callback")
                .queryParam("redirect_uri", "http://www.tripdiary.site/api/kakao/callback")
                .queryParam("code", code)
                .build())
            .retrieve().bodyToMono(JSONObject.class).block();
            
            return (String) response.get("access_token");
    }
   
   private MemberVO getKakaoUserInfo(String accessToken) {
	   MemberVO memberVO = new MemberVO();

	   // ???????????? ?????? ????????? ??? ?????? ??????
       WebClient webClient = WebClient.builder()
           .baseUrl("https://kapi.kakao.com")
           .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
           .build();
           
       JSONObject response = webClient.post()
           .uri(uriBuilder -> uriBuilder.path("/v2/user/me").build())
           .header("Authorization", "Bearer " + accessToken)
           .retrieve().bodyToMono(JSONObject.class).block();
       
       // ?????? ???????????? ????????? ?????? ???????????? (????????? ?????? ?????? ??????????????? ????????? ?????????)
       System.out.println(response.toString());
       String id = String.valueOf( response.get("id"));
       Map<String, Object> map = (Map<String, Object>) response.get("kakao_account"); 
       
       Map<String, Object> profile = (Map<String, Object>) map.get("profile");
       String nickname = (String) profile.get("nickname");
       // ????????? ????????? ????????? ????????? ???
       
       memberVO.setKakaoConnectId(id);
       memberVO.setMberNickName(nickname);
       memberVO.setMberName(nickname);
       memberVO.setSnsMod("k");
       
       return memberVO; 
   }
}
