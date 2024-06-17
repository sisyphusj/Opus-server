package com.opus.feature.member.service;

import java.util.NoSuchElementException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.feature.member.domain.MemberInsertDTO;
import com.opus.feature.member.domain.MemberResponseDTO;
import com.opus.feature.member.domain.MemberVO;
import com.opus.feature.member.mapper.MemberMapper;
import com.opus.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberMapper memberMapper;
	
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void registerMember(MemberInsertDTO memberInsertDTO) {
		memberInsertDTO.updatePassword(passwordEncoder.encode(memberInsertDTO.getPassword()));
		memberMapper.insertMember(MemberVO.of(memberInsertDTO));
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
		return memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId())
			.map(MemberResponseDTO::of)
			.orElseThrow(() -> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));
	}

	@Transactional
	public void editMyProfile(MemberInsertDTO memberInsertDTO) {
		memberInsertDTO.updatePassword(passwordEncoder.encode(memberInsertDTO.getPassword()));
		memberMapper.updateMember(MemberVO.of(memberInsertDTO, SecurityUtil.getCurrentUserId()));
	}

	@Transactional
	public void removeMyProfile() {
		memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId())
			.orElseThrow(() -> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));

		memberMapper.deleteMember(SecurityUtil.getCurrentUserId());
	}

}
