package com.opus.component;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opus.exception.BusinessException;
import com.opus.feature.member.domain.MemberEditReqDTO;
import com.opus.feature.member.domain.MemberRegisterReqDTO;
import com.opus.feature.member.domain.MemberVO;
import com.opus.feature.member.mapper.MemberMapper;
import com.opus.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckMemberFieldComponent {

	private final MemberMapper memberMapper;

	/**
	 * 회원 가입 시 중복 체크
	 */
	@Transactional(readOnly = true)
	public void checkDuplicatedFields(MemberRegisterReqDTO requestDTO) {

		if (memberMapper.selectCountByUsername(requestDTO.getUsername()) > 0) {
			throw new BusinessException("아이디 중복");
		}

		if (memberMapper.selectCountByNickname(requestDTO.getNickname()) > 0) {
			throw new BusinessException("닉네임 중복");
		}

		if (memberMapper.selectCountByEmail(requestDTO.getEmail()) > 0) {
			throw new BusinessException("이메일 중복");
		}

	}

	/**
	 * 회원정보 수정 시 중복 체크
	 */
	@Transactional(readOnly = true)
	public void checkDuplicatedFields(MemberEditReqDTO memberRequestDTO) {

		checkDuplicatedNickAtUpdate(memberRequestDTO.getNickname());

		checkDuplicatedEmailAtUpdate(memberRequestDTO.getEmail());
	}

	/**
	 * 회원정보 수정 시 닉네임 중복 체크
	 */
	private void checkDuplicatedNickAtUpdate(String nickname) {

		MemberVO memberVO = memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId())
			.orElseThrow(() -> new BusinessException("해당 회원을 찾을 수 없습니다."));

		if (memberVO.getNickname().equals(nickname)) {
			return;
		}

		if (memberMapper.selectCountByNickname(nickname) > 0) {

			throw new BusinessException("닉네임 중복");
		}
	}

	/**
	 * 회원정보 수정 시 이메일 중복 체크
	 */
	private void checkDuplicatedEmailAtUpdate(String email) {

		MemberVO memberVO = memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId())
			.orElseThrow(() -> new BusinessException("해당 회원을 찾을 수 없습니다."));

		if (memberVO.getEmail().equals(email)) {
			return;
		}

		if (memberMapper.selectCountByEmail(email) > 0) {
			throw new BusinessException("이메일 중복");
		}
	}

	/*
	 * memberId에 해당하는 Member 가 존재하는지 확인
	 */
	@Transactional(readOnly = true)
	public void checkMemberExist() {
		Optional<MemberVO> memberVO = memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId());

		if (memberVO.isEmpty()) {
			throw new BusinessException("해당 회원을 찾을 수 없습니다.");
		}
	}
}
