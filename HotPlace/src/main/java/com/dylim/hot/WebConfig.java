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
                .addPathPatterns("/map/getMyMapView/*")// 해당 경로에 접근하기 전에 인터셉터가 가로챈다.
                .addPathPatterns("/map/updateReview/*")
                .addPathPatterns("/member/myFriendsView*")
        		.excludePathPatterns("/*");// 해당 경로는 인터셉터가 가로채지 않는다.
    }
}
