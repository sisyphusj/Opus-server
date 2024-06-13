package com.opus.auth.service;

import com.opus.utils.SecurityUtil;
import com.opus.auth.TokenProvider;
import com.opus.auth.domain.LoginDTO;
import com.opus.auth.domain.RefreshTokenVO;
import com.opus.auth.domain.TokenDTO;
import com.opus.auth.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

  private final RefreshTokenMapper refreshTokenMapper;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final TokenProvider tokenProvider;

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
  public TokenDTO reissueToken(TokenDTO requestTokenDTO) {

    if (!tokenProvider.validateToken(requestTokenDTO.getRefreshToken())) {
      throw new BadCredentialsException("Refresh Token이 유효하지 않습니다.");
    }

    Authentication authentication = tokenProvider.getAuthentication(
        requestTokenDTO.getAccessToken());

    RefreshTokenVO refreshTokenVO = refreshTokenMapper.selectRefreshToken(
            Integer.parseInt(authentication.getName()))
        .orElseThrow(() -> new BadCredentialsException("로그아웃된 사용자입니다"));

    if (!refreshTokenVO.getValue().equals(requestTokenDTO.getRefreshToken())) {
      throw new BadCredentialsException("토큰의 유저 정보가 일치하지 않습니다.");
    }

    TokenDTO tokenDTO = tokenProvider.createToken(authentication);

    refreshTokenVO.setValue(tokenDTO.getRefreshToken());

    refreshTokenMapper.updateRefreshToken(refreshTokenVO);

    return tokenDTO;
  }

  @Transactional
  public void logout() {
    refreshTokenMapper.deleteRefreshToken(SecurityUtil.getCurrentUserId());
  }

}
