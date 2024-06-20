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
import com.opus.feature.auth.domain.TokenDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

	private static final String AUTHORITIES_KEY = "auth";
	private final String secret;
	private final long tokenValidityInMilliseconds;
	private Key key;

	public TokenProvider(
		@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
		this.secret = secretKey;
		this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
	}

	@Override
	public void afterPropertiesSet() {

		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public TokenDTO createToken(Authentication authentication) {

		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity = new Date(now + this.tokenValidityInMilliseconds);

		String accessToken = Jwts.builder()
			.setSubject(authentication.getName())
			.claim(AUTHORITIES_KEY, authorities)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(validity)
			.compact();

		String refreshToken = Jwts.builder()
			// 만료기한 설정
			.setExpiration(new Date(now + this.tokenValidityInMilliseconds * 2016))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		return TokenDTO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.accessTokenExpiresIn(validity.getTime())
			.build();
	}

	public Authentication getAuthentication(String token) {

		Claims claims = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();

		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.toList();

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public TokenDTO reissueTokenFromMemberId(int memberId, String refreshToken) {

		UserDetails userDetails = new User(String.valueOf(memberId), "",
			List.of(new SimpleGrantedAuthority("USER")));

		String authorities = userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity = new Date(now + this.tokenValidityInMilliseconds);

		String accessToken = Jwts.builder()
			.setSubject(userDetails.getUsername())
			.claim(AUTHORITIES_KEY, authorities)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(validity)
			.compact();

		return TokenDTO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.accessTokenExpiresIn(validity.getTime())
			.build();
	}

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
