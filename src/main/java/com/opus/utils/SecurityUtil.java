package com.opus.utils;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * JWT 유틸리티
 */

@Slf4j
public class SecurityUtil {

	private SecurityUtil() {
	}

	/**
	 * 현재 로그인한 사용자의 memberId 를 반환
	 */
	public static int getCurrentUserId() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 인증 정보가 없는 경우 예외 발생
		if (authentication == null || authentication.getName() == null) {
			throw new AuthenticationCredentialsNotFoundException("로그인이 필요합니다.");
		}

		// 인증되지 않은 사용자가 접근 시도할 경우 예외 발생
		if (authentication.getName().equals("anonymousUser")) {
			throw new AuthenticationCredentialsNotFoundException("로그인이 필요합니다.");
		}

		return Integer.parseInt(authentication.getName());
	}
}
