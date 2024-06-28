package com.opus.feature.auth.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.component.TokenProvider;
import com.opus.feature.auth.domain.LoginReqDTO;
import com.opus.feature.auth.domain.RefreshTokenVO;
import com.opus.feature.auth.domain.ReissueTokenReqDTO;
import com.opus.feature.auth.domain.TokenResDTO;
import com.opus.feature.auth.mapper.RefreshTokenMapper;
import com.opus.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final RefreshTokenMapper refreshTokenMapper;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final TokenProvider tokenProvider;

	/**
	 * 로그인 처리
	 */
	@Transactional
	public TokenResDTO login(LoginReqDTO loginReqDTO) {

		// 로그인 정보를 기반으로 AuthenticationToken 생성
		UsernamePasswordAuthenticationToken authToken = loginReqDTO.toAuthentication();

		// 실제 인증 과정
		Authentication authentication = authenticationManagerBuilder.getObject()
			.authenticate(authToken);

		// 인증 정보를 기반으로 JWT 토큰 생성
		TokenResDTO tokenResDTO = tokenProvider.createToken(authentication);

		// Refresh Token 저장
		RefreshTokenVO refreshTokenVO = RefreshTokenVO.builder()
			.key(Integer.parseInt(authentication.getName()))
			.value(tokenResDTO.getRefreshToken())
			.build();

		refreshTokenMapper.deleteRefreshToken(refreshTokenVO.getKey());

		refreshTokenMapper.insertRefreshToken(refreshTokenVO);

		return tokenResDTO;
	}

	/**
	 * Refresh Token을 이용한 토큰 재발급
	 */
	@Transactional
	public TokenResDTO reissueToken(ReissueTokenReqDTO reissueTokenReqDTO) {

		// Refresh Token 유효성 검증
		if (!tokenProvider.validateToken(reissueTokenReqDTO.getRefreshToken())) {
			throw new BadCredentialsException("Refresh Token이 유효하지 않습니다.");
		}

		// DB 저장된 Refresh Token 조회
		RefreshTokenVO refreshTokenVO = refreshTokenMapper.selectRefreshTokenByToken(
				reissueTokenReqDTO.getRefreshToken())
			.orElseThrow(() -> new BadCredentialsException("로그아웃된 사용자입니다"));

		// Access Token을 재발급
		return tokenProvider.reissueTokenFromMemberId(refreshTokenVO.getKey(), refreshTokenVO.getValue());
	}

	/**
	 * 로그아웃
	 */
	@Transactional
	public void logout() {
		refreshTokenMapper.deleteRefreshToken(SecurityUtil.getCurrentUserId());
	}

}
