package com.auth.core.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor());

//        registry.addInterceptor(new AuthInterceptor())//
//                .addPathPatterns("/webview_portal")//
//                .excludePathPatterns("/webview_portal/test")
        ;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/index22").setViewName("index");
//        registry.addViewController("/").setViewName("index");
//        registry.addViewController("/hello").setViewName("index");
//        registry.addViewController("/login").setViewName("index");
    }
}
