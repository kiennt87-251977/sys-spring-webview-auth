package com.auth.core.interceptor;

import com.auth.core.service.TokenUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthInterceptor implements HandlerInterceptor {

    String keyAuthorization = "BaseAuthCustom";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setAttribute(keyAuthorization, true);
        String authorization = request.getHeader("authorization");

        boolean validateToken = false;
        if (request.getServletPath().contains("/webview-back-end/")) {
            if (request.getServletPath().contains("/call-login")
                    || request.getServletPath().contains("/call-logout")
            ) {
                validateToken = true;
            } else {
                if (authorization.contains("Bearer ")) {
                    String bearToken = authorization.replace("Bearer ", "");
                    validateToken = TokenUtils.validateToken(bearToken);
                }
            }

        } else {
            validateToken = true;
        }

        if (!validateToken) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return validateToken;


//        return true;
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
