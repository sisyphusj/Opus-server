package com.opus.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {}

    public static int getCurrentUserId() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("call getCurrentUserId, authentication={}", authentication);

        if(authentication == null || authentication.getName() == null) {
            throw new AuthenticationCredentialsNotFoundException("Security Context 에 인증 정보가 없습니다.");
        }

        if (authentication.getName().equals("anonymousUser")) {
            return 0;
            // TODO : 권한 관련 기능을 추가한 후 익명 사용자의 ID를 0으로 처리할지 예외를 던질지 결정
        }

        return Integer.parseInt(authentication.getName());
    }
}
