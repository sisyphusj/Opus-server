package com.opus.member.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginMember") == null) {
            if (!requestURI.equals("/member/login") && !requestURI.equals("/member/signup")) {
                log.info("미인증 사용자 요청 : {}", request);

                request.setAttribute("message", "로그인이 필요한 서비스입니다.");
                request.setAttribute("exception", "AuthenticationException");
                request.getRequestDispatcher("/error").forward(request, response);
                return false;
            }
        }
        return true;
    }
}
