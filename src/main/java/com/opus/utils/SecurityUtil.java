package com.opus.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {}

    public static int getCurrentUserId() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("call getCurrentUserId, authentication={}", authentication);

        if(authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        
//        String username = null;
//        if(authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
//            username = springSecurityUser.getUsername();
//        } else if(authentication.getPrincipal() instanceof String string) {
//            username = string;
//        }
        if (authentication.getName().equals("anonymousUser")) {
            return 0;
        }

        return Integer.parseInt(authentication.getName());
    }
}
