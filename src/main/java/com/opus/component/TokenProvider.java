package com.opus.component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.opus.common.ResponseCode;
import com.opus.exception.CustomException;
import com.opus.feature.auth.domain.TokenResDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * TokenProvider - JWT 토큰 생성 및 유효성 검증 클래스
 */

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

	// 토큰에 담을 권한 정보 키
	private static final String AUTHORITIES_KEY = "auth";

	// JWT 기본 암호화 키
	private final String secret;

	// 토큰 유효시간
	private final long tokenValidityInMilliseconds;

	// 디코딩한 secret 값을 HMAC SHA 알고리즘으로 생성한 key
	private Key key;

	/**
	 * 생성자로 secretKey, tokenValidityInSeconds를 주입받음
	 */
	public TokenProvider(
		@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {

		this.secret = secretKey;
		this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
	}

	/**
	 * Bean 초기화 메서드
	 */
	@Override
	public void afterPropertiesSet() {

		// Base64로 인코딩된 secret 값을 디코딩하여 key 변수에 할당
		byte[] keyBytes = Decoders.BASE64.decode(secret);

		// key 변수에 할당된 값을 HMAC SHA 알고리즘으로 생성
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * 토큰 생성 메서드
	 */
	public TokenResDTO createToken(Authentication authentication) {

		// 권한 정보를 콤마로 구분하여 authorities 변수에 할당
		// 토큰 생성에 사용되는 권한 정보는 문자열 형태로 저장
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		// 현재 시간을 now 변수에 할당
		long now = (new Date()).getTime();

		// 토큰 만료 시간 설정
		Date validity = new Date(now + this.tokenValidityInMilliseconds);

		// 토큰 생성
		String accessToken = Jwts.builder()
			.setSubject(authentication.getName())
			.claim(AUTHORITIES_KEY, authorities)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(validity)
			.compact();

		// 리프레시 토큰 생성
		String refreshToken = Jwts.builder()
			// 만료기한 설정
			.setExpiration(new Date(now + this.tokenValidityInMilliseconds * 336))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		// 토큰 DTO 생성
		return TokenResDTO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.accessTokenExpiresIn(validity.getTime())
			.build();
	}

	/**
	 * 토큰에서 인증 정보 추출 메서드
	 */
	public Authentication getAuthentication(String token) {

		// 토큰에서 권한 정보 추출
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();

		// 권한 정보를 콤마로 구분하여 authorities 변수에 할당
		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.toList();

		// 권한 정보를 사용자 객체로 변환
		User principal = new User(claims.getSubject(), "", authorities);

		// 사용자 객체, 토큰, 권한 정보를 담은 UsernamePasswordAuthenticationToken 객체 생성
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	/**
	 * 리프레시 토큰에서 인증 정보 추출 메서드
	 */
	public TokenResDTO reissueTokenFromMemberId(int memberId, String refreshToken) {

		// User 객체 생성
		UserDetails userDetails = new User(String.valueOf(memberId), "",
			List.of(new SimpleGrantedAuthority("USER")));

		// 권한 정보를 콤마로 구분하여 authorities 변수에 할당
		String authorities = userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		// 현재 시간을 now 변수에 할당
		long now = (new Date()).getTime();

		// 토큰 만료 시간 설정
		Date validity = new Date(now + this.tokenValidityInMilliseconds);

		// 토큰 생성
		String accessToken = Jwts.builder()
			.setSubject(userDetails.getUsername())
			.claim(AUTHORITIES_KEY, authorities)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(validity)
			.compact();

		// 토큰 DTO 생성
		return TokenResDTO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.accessTokenExpiresIn(validity.getTime())
			.build();
	}

	/**
	 * 토큰 유효성 검증 메서드
	 */
	public boolean validateToken(String authToken) {

		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(authToken);
			return true;
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException e) {
			log.error("토큰이 유효하지 않습니다.");
			throw new CustomException(ResponseCode.INVALID_TOKEN);
		} catch (ExpiredJwtException e) {
			log.error("토큰이 만료되었습니다.");
			throw new CustomException(ResponseCode.TOKEN_EXPIRED);
		}
	}
}
