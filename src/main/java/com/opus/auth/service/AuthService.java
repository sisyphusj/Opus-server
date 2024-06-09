package com.opus.auth.service;

import com.opus.utils.SecurityUtil;
import com.opus.auth.TokenProvider;
import com.opus.auth.domain.LoginDTO;
import com.opus.common.ResponseCode;
import com.opus.exception.BusinessExceptionHandler;
import com.opus.auth.domain.RefreshTokenVO;
import com.opus.auth.domain.TokenDTO;
import com.opus.auth.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenMapper refreshTokenMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public TokenDTO login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authToken = loginDTO.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);
        TokenDTO tokenDTO = tokenProvider.createToken(authentication);
        RefreshTokenVO refreshTokenVO = RefreshTokenVO.of(authentication.getName(), tokenDTO.getRefreshToken());

        refreshTokenMapper.delete(refreshTokenVO.getKey());
        refreshTokenMapper.save(refreshTokenVO);

        return tokenDTO;
    }

    @Transactional
    public TokenDTO reissueToken(TokenDTO requestTokenDTO) {
        // Refresh Token 유효성 검증
        if (!tokenProvider.validateToken(requestTokenDTO.getRefreshToken())) {
            throw new BusinessExceptionHandler(ResponseCode.USER_UNAUTHORIZED, "Refresh Token이 유효하지 않습니다.");
        }

        // Access Token으로부터 인증 정보 가져오기
        Authentication authentication = tokenProvider.getAuthentication(requestTokenDTO.getAccessToken());

        // 기존 Refresh Token 조회 및 검증
        RefreshTokenVO refreshTokenVO = refreshTokenMapper.findByKey(authentication.getName())
                .orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.USER_UNAUTHORIZED, "로그아웃된 사용자입니다. Refresh Token이 존재하지 않습니다."));

        if (!refreshTokenVO.getValue().equals(requestTokenDTO.getRefreshToken())) {
            throw new BusinessExceptionHandler(ResponseCode.USER_UNAUTHORIZED, "토큰 정보가 일치하지 않습니다.");
        }

        // 새로운 토큰 생성
        TokenDTO tokenDTO = tokenProvider.createToken(authentication);
        refreshTokenMapper.update(refreshTokenVO.updateValue(tokenDTO.getRefreshToken()));

        // 새 토큰 반환
        return tokenDTO;
    }

    @Transactional
    public void logout() {
        String memberId = String.valueOf(SecurityUtil.getCurrentUserId());
        refreshTokenMapper.findByKey(memberId)
                .orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.USER_UNAUTHORIZED, "로그아웃된 사용자입니다. Refresh Token이 존재하지 않습니다."));
        refreshTokenMapper.delete(memberId);
    }
}
