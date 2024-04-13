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

        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("loginMember") == null) {
            // 수정이 필요함 아래의 조건문의 필요성을 따져야 한다.
            if(!requestURI.equals("/member/login")) {
                log.info("미인증 사용자 요청");
                return false;
            }
        }
        return true;
    }
}
