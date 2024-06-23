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

/**
 * JwtAuthenticationEntryPoint - 401 예외 처리 클래스
 */

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

		// JWT 필터에서 정의한 예외가 없다면 REST CONTROLLER ADVISE로 AuthenticationException 예외를 던짐
		if (customException == null) {
			handlerExceptionResolver.resolveException(request, response, null, authException);
			return;
		}

		// customException 이 있다면 REST CONTROLLER ADVISE로 CustomException 예외를 던짐
		handlerExceptionResolver.resolveException(request, response, null, customException);
	}
}