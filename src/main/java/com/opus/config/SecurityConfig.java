package com.opus.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.opus.common.PermittedUrls;
import com.opus.component.JwtAccessDeniedHandler;
import com.opus.component.JwtAuthenticationEntryPoint;
import com.opus.component.TokenProvider;
import com.opus.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

/**
 * SecurityConfig - Spring Security 설정 클래스
 */

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		// 비밀번호 암호화 방식을 지정하여 PasswordEncoder 빈을 생성
		return new BCryptPasswordEncoder();
	}

	// CORS 설정 추가
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("https://sisyphusj.me"));
		configuration.setAllowedMethods(
			Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(
			Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
		configuration.setAllowCredentials(true);

		// 모든 요청에 대해 CORS 설정을 적용
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity

			// CSRF 보안 설정 비활성화
			.csrf(AbstractHttpConfigurer::disable)

			// CORS 설정 추가
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))

			// 예외 처리를 위한 핸들러 설정
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			)

			// X-Frame-Options 설정
			.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
			)

			// 세션 관리 설정
			.sessionManagement(
				sessionManagement -> sessionManagement
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)

			// 인증 요청 설정
			.authorizeHttpRequests(
				authorizeRequests -> authorizeRequests
					.requestMatchers(HttpMethod.GET, "/api/auth/test", "/api/likes/pin/**", "/api/likes/comment/**")
					.permitAll()
					.requestMatchers(PermittedUrls.PERMITTED_URLS)
					.permitAll()
					.requestMatchers(HttpMethod.OPTIONS, "/**")
					.permitAll()
					.anyRequest()
					.authenticated()
			)

			// JWT 필터 설정
			.addFilterBefore(
				new JwtFilter(tokenProvider),
				UsernamePasswordAuthenticationFilter.class
			);

		return httpSecurity.build();
	}

}
