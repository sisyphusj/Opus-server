package com.opus.feature.auth.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.feature.auth.TokenProvider;
import com.opus.feature.auth.domain.LoginDTO;
import com.opus.feature.auth.domain.RefreshTokenVO;
import com.opus.feature.auth.domain.TokenDTO;
import com.opus.feature.auth.mapper.AuthMapper;
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
	private final AuthMapper authMapper;

	@Transactional
	public TokenDTO login(LoginDTO loginDTO) {

		UsernamePasswordAuthenticationToken authToken = loginDTO.toAuthentication();

		Authentication authentication = authenticationManagerBuilder.getObject()
			.authenticate(authToken);

		TokenDTO tokenDTO = tokenProvider.createToken(authentication);

		RefreshTokenVO refreshTokenVO = RefreshTokenVO.builder()
			.key(Integer.parseInt(authentication.getName()))
			.value(tokenDTO.getRefreshToken())
			.build();

		refreshTokenMapper.deleteRefreshToken(refreshTokenVO.getKey());

		refreshTokenMapper.insertRefreshToken(refreshTokenVO);

		return tokenDTO;
	}

	@Transactional
	// TODO REDIS 캐시 적용
	public TokenDTO reissueToken(TokenDTO requestTokenDTO) {

		// Refresh Token 유효성 검증
		if (!tokenProvider.validateToken(requestTokenDTO.getRefreshToken())) {
			throw new BadCredentialsException("Refresh Token이 유효하지 않습니다.");
		}

		// DB 저장된 Refresh Token 조회
		RefreshTokenVO refreshTokenVO = refreshTokenMapper.selectRefreshTokenByToken(requestTokenDTO.getRefreshToken())
			.orElseThrow(() -> new BadCredentialsException("로그아웃된 사용자입니다"));

		// Access Token을 재발급
		return tokenProvider.reissueTokenFromMemberId(refreshTokenVO.getKey(), refreshTokenVO.getValue());
	}

	@Transactional
	public void logout() {
		refreshTokenMapper.deleteRefreshToken(SecurityUtil.getCurrentUserId());
	}

}
