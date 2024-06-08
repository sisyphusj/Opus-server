package com.opus.member.service;

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
    public Boolean checkDuplicateId(String id) {
        return memberMapper.checkDuplicateId(id) <= 0;
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicateNick(String nick) {
        return memberMapper.checkDuplicateNick(nick) <= 0;
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicateEmail(String email) {
        return memberMapper.checkDuplicateEmail(email) <= 0;
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
    public TokenDTO reissue(TokenDTO requestTokenDTO) {
        if (!tokenProvider.validateToken(requestTokenDTO.getRefreshToken())) {
            throw new BusinessExceptionHandler(ResponseCode.USER_UNAUTHORIZED, "Refresh Token이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(requestTokenDTO.getAccessToken());

        RefreshToken refreshToken = refreshTokenMapper.findByKey(authentication.getName())
                .orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.USER_UNAUTHORIZED, "로그아웃된 사용자입니다. Refresh Token이 존재하지 않습니다."));

        if (!refreshToken.getValue().equals(requestTokenDTO.getRefreshToken())) {
            throw new BusinessExceptionHandler(ResponseCode.USER_UNAUTHORIZED, "토큰 정보가 일치하지 않습니다.");
        }

        TokenDTO tokenDTO = tokenProvider.createToken(authentication);

        RefreshToken newRefreshToken = RefreshToken.of(authentication.getName(), tokenDTO.getRefreshToken());
        refreshTokenMapper.delete(newRefreshToken.getKey());

        return tokenDTO;
    }

    @Transactional
    public void logout(String mId) {
        refreshTokenMapper.findByKey(mId)
                .orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.USER_UNAUTHORIZED, "로그아웃된 사용자입니다. Refresh Token이 존재하지 않습니다."));
        refreshTokenMapper.delete(mId);
    }

    @Transactional(readOnly = true)
    public MemberVO getMyProfile(int mId) {
        Optional<MemberVO> member = Optional.of(memberMapper.findById(mId));
        return member.orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.NOT_FOUND_ERROR, "해당 회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void updateMember(MemberDTO memberDTO, int mId) {
        String rawPw = memberDTO.getPw();
        String encPw = passwordEncoder.encode(rawPw);
        memberDTO.setPw(encPw);

        Member member = Member.of(memberDTO, mId);
        memberMapper.updateMember(member);
    }

    @Transactional
    public void deleteMember(int mId) {
        memberMapper.deleteMember(mId);
    }

}
