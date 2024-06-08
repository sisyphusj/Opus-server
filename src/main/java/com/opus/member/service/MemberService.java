package com.opus.member.service;

import com.opus.auth.SecurityUtil;
import com.opus.auth.TokenProvider;
import com.opus.common.ResponseCode;
import com.opus.exception.BusinessExceptionHandler;
import com.opus.member.domain.*;
import com.opus.member.mapper.MemberMapper;
import com.opus.member.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenMapper refreshTokenMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public void saveMember(MemberDTO memberDTO) {
        String rawPw = memberDTO.getPw();
        String encPw = passwordEncoder.encode(rawPw);
        memberDTO.setPw(encPw);

        Member member = Member.of(memberDTO);
        memberMapper.saveMember(member);
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicateId(String userId) {
        return memberMapper.checkDuplicateId(userId) > 0;
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicateNickname(String nick) {
        return memberMapper.checkDuplicateNickname(nick) > 0;
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicateEmail(String email) {
        return memberMapper.checkDuplicateEmail(email) > 0;
    }

    @Transactional
    public TokenDTO login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authToken = loginDTO.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);
        TokenDTO tokenDTO = tokenProvider.createToken(authentication);
        RefreshToken refreshToken = RefreshToken.of(authentication.getName(), tokenDTO.getRefreshToken());

        refreshTokenMapper.delete(refreshToken.getKey());
        refreshTokenMapper.save(refreshToken);

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
        RefreshToken refreshToken = refreshTokenMapper.findByKey(authentication.getName())
                .orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.USER_UNAUTHORIZED, "로그아웃된 사용자입니다. Refresh Token이 존재하지 않습니다."));

        if (!refreshToken.getValue().equals(requestTokenDTO.getRefreshToken())) {
            throw new BusinessExceptionHandler(ResponseCode.USER_UNAUTHORIZED, "토큰 정보가 일치하지 않습니다.");
        }

        // 새로운 토큰 생성
        TokenDTO tokenDTO = tokenProvider.createToken(authentication);
        refreshTokenMapper.update(refreshToken.updateValue(tokenDTO.getRefreshToken()));

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

    @Transactional(readOnly = true)
    public MemberVO getMyProfile() {
        Optional<MemberVO> member = Optional.of(memberMapper.findByMemberId(SecurityUtil.getCurrentUserId()));
        return member.orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.NOT_FOUND_ERROR, "해당 회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void updateMember(MemberDTO memberDTO) {
        String rawPw = memberDTO.getPw();
        String encPw = passwordEncoder.encode(rawPw);
        memberDTO.setPw(encPw);

        Member member = Member.of(memberDTO, SecurityUtil.getCurrentUserId());
        memberMapper.updateMember(member);
    }

    @Transactional
    public void deleteMember() {
        memberMapper.deleteMember(SecurityUtil.getCurrentUserId());
    }

}
