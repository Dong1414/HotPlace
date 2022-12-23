package com.dylim.hot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CertificationInterceptor())
                //.addPathPatterns("/login/*")
        		//.addPathPatterns("/map/getMyMapView/saveReview.do")
        		//.addPathPatterns("/member/myFriendsView*")
        		//.addPathPatterns("/member/myPage*")
        		//.addPathPatterns("/member/lastPwDtOverView.do")
        		//.addPathPatterns("/map/updateReview*")
        		.addPathPatterns("/map/my-map/review*")	
        		.addPathPatterns("/member/myFriendsView*")
        		.addPathPatterns("/member/mypage*")
        		.addPathPatterns("/member/oldpw")
        		.excludePathPatterns("/*");// 해당 경로는 인터셉터가 가로채지 않는다.
    }
}
