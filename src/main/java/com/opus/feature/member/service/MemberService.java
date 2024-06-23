package com.opus.feature.member.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.exception.BusinessException;
import com.opus.feature.member.domain.MemberEditRequestDTO;
import com.opus.feature.member.domain.MemberRequestDTO;
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

	/**
	 * 회원 가입
	 */
	@Transactional
	public void registerMember(MemberRequestDTO memberRequestDTO) {

		checkMemberDuplicated(memberRequestDTO);

		// 원본 password 인코딩
		memberRequestDTO.updatePassword(passwordEncoder.encode(memberRequestDTO.getPassword()));

		memberMapper.insertMember(MemberVO.fromRegistrationDTO(memberRequestDTO));
	}

	/**
	 * username 중복 체크
	 * username 이 포함된 정보가 1 이상 존재하면 true 반환
	 */
	@Transactional(readOnly = true)
	public boolean isUsernameDuplicated(String username) {

		return memberMapper.selectCountByUsername(username) > 0;
	}

	/**
	 * nickname 중복 체크
	 * nickname 이 포함된 정보가 1 이상 존재하면 true 반환
	 */
	@Transactional(readOnly = true)
	public boolean isNicknameDuplicated(String nickname) {

		return memberMapper.selectCountByNickname(nickname) > 0;
	}

	/**
	 * email 중복 체크
	 * email 이 포함된 정보가 1 이상 존재하면 true 반환
	 */
	@Transactional(readOnly = true)
	public boolean isEmailDuplicated(String email) {

		return memberMapper.selectCountByEmail(email) > 0;
	}

	/**
	 * memberId에 따른 회원정보 조회
	 */
	@Transactional(readOnly = true)
	public MemberResponseDTO getMyProfile() {

		// memberId에 해당하는 회원이 없다면 BusinessException 발생
		return memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId())
			.map(MemberResponseDTO::of)
			.orElseThrow(() -> new BusinessException("해당 회원을 찾을 수 없습니다."));
	}

	/**
	 * 회원 정보 업데이트
	 */
	@Transactional
	public void editMyProfile(MemberEditRequestDTO memberEditRequestDTO) {

		checkMemberDuplicated(memberEditRequestDTO);

		// password 가 입력되었다면 인코딩
		if (!StringUtils.isBlank(memberEditRequestDTO.getPassword())) {
			memberEditRequestDTO.updatePassword(passwordEncoder.encode(memberEditRequestDTO.getPassword()));
		}

		memberMapper.updateMember(MemberVO.of(memberEditRequestDTO));
	}

	/**
	 * 회원 정보 삭제
	 */
	@Transactional
	public void removeMyProfile() {

		// memberId에 해당하는 회원이 없다면 BusinessException 발생
		Optional<MemberVO> memberVO = memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId());

		if (memberVO.isEmpty()) {
			throw new BusinessException("해당 회원을 찾을 수 없습니다.");
		}

		memberMapper.deleteMember(SecurityUtil.getCurrentUserId());
	}

	/**
	 * DTO 의 password 를 제외한 각 필드 중복 체크
	 */
	private void checkMemberDuplicated(DuplicateCheckAttributes requestDTO) {

		if (memberMapper.selectCountByUsername(requestDTO.getUsername()) > 0)
			throw new BusinessException("아이디 중복");
		if (memberMapper.selectCountByNickname(requestDTO.getNickname()) > 0)
			throw new BusinessException("닉네임 중복");
		if (memberMapper.selectCountByEmail(requestDTO.getEmail()) > 0)
			throw new BusinessException("이메일 중복");

	}
}
