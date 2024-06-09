package com.opus.member.service;

import com.opus.utils.SecurityUtil;
import com.opus.common.ResponseCode;
import com.opus.exception.BusinessExceptionHandler;
import com.opus.member.domain.*;
import com.opus.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public void saveMember(MemberDTO memberDTO) {
        MemberVO member = MemberVO.of(memberDTO, passwordEncoder);
        memberMapper.saveMember(member);
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicateUserName(String userName) {
        return memberMapper.checkDuplicateUserName(userName) > 0;
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicateNickname(String nickname) {
        return memberMapper.checkDuplicateNickname(nickname) > 0;
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicateEmail(String email) {
        return memberMapper.checkDuplicateEmail(email) > 0;
    }

    @Transactional(readOnly = true)
    public MemberResponseDTO getMyProfile() {
        MemberVO memberVO = memberMapper.findByMemberId(SecurityUtil.getCurrentUserId());
        Optional<MemberResponseDTO> member = Optional.of(MemberResponseDTO.of(memberVO));
        return member.orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.NOT_FOUND_ERROR, "해당 회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void updateMember(MemberDTO memberDTO) {
        MemberVO member = MemberVO.of(memberDTO, SecurityUtil.getCurrentUserId(), passwordEncoder);
        memberMapper.updateMember(member);
    }

    @Transactional
    public void deleteMember() {
        memberMapper.deleteMember(SecurityUtil.getCurrentUserId());
    }

}
