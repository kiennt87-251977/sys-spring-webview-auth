package com.auth.core.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class StatictInterceptor implements HandlerInterceptor {
    protected Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    String keyAuthorization = "BaseAuthCustom";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setAttribute(keyAuthorization, true);
        String authorization = request.getHeader("authorization");

//        response.setHeader("Location", "https://spring.io/guides/gs/securing-web/");
//        response.setStatus(302);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, //
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, //
                                Object handler, Exception ex) throws Exception {
    }

}
