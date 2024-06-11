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
    public void registerMember(MemberDTO memberDTO) {
        MemberVO member = MemberVO.of(memberDTO, passwordEncoder);
        memberMapper.insertMember(member);
    }

    @Transactional(readOnly = true)
    public Boolean isUsernameDuplicated(String userName) {
        return memberMapper.selectCountByUsername(userName) > 0;
    }

    @Transactional(readOnly = true)
    public Boolean isNicknameDuplicated(String nickname) {
        return memberMapper.selectCountByNickname(nickname) > 0;
    }

    @Transactional(readOnly = true)
    public Boolean isEmailDuplicated(String email) {
        return memberMapper.selectCountByEmail(email) > 0;
    }

    @Transactional(readOnly = true)
    public MemberResponseDTO getMyProfile() {
        MemberVO memberVO = memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId());
        Optional<MemberResponseDTO> member = Optional.of(MemberResponseDTO.of(memberVO));
        return member.orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.NOT_FOUND_ERROR, "해당 회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void editMyProfile(MemberDTO memberDTO) {
        MemberVO member = MemberVO.of(memberDTO, SecurityUtil.getCurrentUserId(), passwordEncoder);
        memberMapper.updateMember(member);
    }

    @Transactional
    public void removeMyProfile() {
        MemberVO memberVO = memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId());
        if (memberVO == null) {
            throw new BusinessExceptionHandler(ResponseCode.BUSINESS_ERROR, "해당 회원을 찾을 수 없습니다.");
        }
        memberMapper.deleteMember(SecurityUtil.getCurrentUserId());
    }

}
