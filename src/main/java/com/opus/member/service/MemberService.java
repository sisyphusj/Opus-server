package com.opus.member.service;

import com.opus.utils.SecurityUtil;
import com.opus.member.domain.*;
import com.opus.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberMapper memberMapper;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void registerMember(MemberInsertDTO memberInsertDTO) {
    memberInsertDTO.setPassword(passwordEncoder.encode(memberInsertDTO.getPassword()));
    MemberVO member = MemberVO.of(memberInsertDTO);

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
    MemberVO memberVO = memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId())
        .orElseThrow(() -> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));

    return MemberResponseDTO.of(memberVO);
  }

  @Transactional
  public void editMyProfile(MemberInsertDTO memberInsertDTO) {
    memberInsertDTO.setPassword(passwordEncoder.encode(memberInsertDTO.getPassword()));
    MemberVO member = MemberVO.of(memberInsertDTO, SecurityUtil.getCurrentUserId());

    memberMapper.updateMember(member);
  }

  @Transactional
  public void removeMyProfile() {
    memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId())
        .orElseThrow(() -> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));

    memberMapper.deleteMember(SecurityUtil.getCurrentUserId());
  }

}
