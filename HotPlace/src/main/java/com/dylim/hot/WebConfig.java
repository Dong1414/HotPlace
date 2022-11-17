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
        		.addPathPatterns("/map/getMyMapView/saveReview.do")
        		.addPathPatterns("/member/myFriendsView*")
        		.addPathPatterns("/member/myPage*")
                .addPathPatterns("/map/updateReview*")
        		.excludePathPatterns("/*");// 해당 경로는 인터셉터가 가로채지 않는다.
    }
}
