package com.opus.feature.member.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.component.CheckMemberFieldComponent;
import com.opus.exception.BusinessException;
import com.opus.feature.member.domain.MemberEditReqDTO;
import com.opus.feature.member.domain.MemberRegisterReqDTO;
import com.opus.feature.member.domain.MemberResDTO;
import com.opus.feature.member.domain.MemberVO;
import com.opus.feature.member.domain.PasswordConfirmReqDTO;
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

	private final CheckMemberFieldComponent checkMemberFieldComponent;

	/**
	 * 회원 가입
	 */
	@Transactional
	public void registerMember(MemberRegisterReqDTO memberRegisterReqDTO) {

		// member 필드 유효, 중복 체크
		checkMemberFieldComponent.checkDuplicatedFields(memberRegisterReqDTO);

		// 원본 password 인코딩
		memberRegisterReqDTO.updatePassword(passwordEncoder.encode(memberRegisterReqDTO.getPassword()));

		memberMapper.insertMember(MemberVO.fromRegistrationDTO(memberRegisterReqDTO));
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
	public MemberResDTO getMyProfile() {

		// memberId에 해당하는 회원이 없다면 BusinessException 발생
		return memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId())
			.map(MemberResDTO::of)
			.orElseThrow(() -> new BusinessException("해당 회원을 찾을 수 없습니다."));
	}

	/**
	 * password 확인
	 */
	@Transactional(readOnly = true)
	public boolean confirmPassword(PasswordConfirmReqDTO passwordDTO) {

		// memberId에 해당하는 회원이 없다면 BusinessException 발생
		MemberVO memberVO = memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId())
			.orElseThrow(() -> new BusinessException("해당 회원을 찾을 수 없습니다."));

		// password 일치 여부 반환
		return passwordEncoder.matches(passwordDTO.getPassword(), memberVO.getPassword());
	}

	/**
	 * 회원 정보 업데이트
	 */
	@Transactional
	public void editMyProfile(MemberEditReqDTO memberEditReqDTO) {

		// 중복 체크
		checkMemberFieldComponent.checkDuplicatedFields(memberEditReqDTO);

		if (StringUtils.isNotBlank(memberEditReqDTO.getPassword())) {
			// password 인코딩
			memberEditReqDTO.updatePassword(passwordEncoder.encode(memberEditReqDTO.getPassword()));
		}

		memberMapper.updateMember(MemberVO.of(memberEditReqDTO));

	}

	/**
	 * 회원 정보 삭제
	 */
	@Transactional
	public void removeMyProfile() {

		// memberId에 해당하는 회원이 없다면 BusinessException 발생
		checkMemberFieldComponent.checkMemberExist();

		memberMapper.deleteMember(SecurityUtil.getCurrentUserId());
	}

}
