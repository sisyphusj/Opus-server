package com.opus.utils;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {

	private SecurityUtil() {
	}

	public static int getCurrentUserId() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			throw new AuthenticationCredentialsNotFoundException("로그인이 필요합니다.");
		}

		if (authentication.getName().equals("anonymousUser")) {
			throw new AuthenticationCredentialsNotFoundException("로그인이 필요합니다.");
		}

		return Integer.parseInt(authentication.getName());
	}
}
