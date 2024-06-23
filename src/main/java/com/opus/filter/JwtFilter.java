package com.opus.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.opus.component.TokenProvider;
import com.opus.exception.CustomException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 필터
 */

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";

	private final TokenProvider tokenProvider;

	/**
	 * JWT 토큰을 이용해 인증 정보를 SecurityContext 에 저장
	 */
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws IOException, ServletException {

		// 헤더에서 토큰 추출
		String jwt = resolveToken(request);

		// 요청 URI
		String requestURI = request.getRequestURI();

		try {

			// 헤더에 토큰이 있고 유효한 토큰인 경우
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

				// 토큰을 이용해 인증 정보를 가져옴
				Authentication authentication = tokenProvider.getAuthentication(jwt);

				// SecurityContext 에 인증 정보 저장
				SecurityContextHolder.getContext().setAuthentication(authentication);

				log.debug("{} 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
			}

		} catch (CustomException e) {
			SecurityContextHolder.clearContext();
			request.setAttribute("exception", e);
		}
		filterChain.doFilter(request, response);
	}

	// 헤더에서 토큰 값 추출
	private String resolveToken(HttpServletRequest request) {

		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}

		return null;
	}
}
