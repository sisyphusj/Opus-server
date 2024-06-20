package com.opus.component;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.opus.exception.CustomException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final HandlerExceptionResolver handlerExceptionResolver;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) {

		// JWT 필터에서 넘긴 예외를 처리
		CustomException customException = (CustomException)request.getAttribute("exception");

		if (customException == null) {
			handlerExceptionResolver.resolveException(request, response, null, authException);
			return;
		}

		handlerExceptionResolver.resolveException(request, response, null, customException);
	}
}