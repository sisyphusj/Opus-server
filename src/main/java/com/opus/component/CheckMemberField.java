package com.opus.component;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opus.exception.BusinessException;
import com.opus.feature.member.domain.MemberEditRequestDTO;
import com.opus.feature.member.domain.MemberRequestDTO;
import com.opus.feature.member.domain.MemberVO;
import com.opus.feature.member.mapper.MemberMapper;
import com.opus.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckMemberField {

	private final MemberMapper memberMapper;

	/**
	 * 회원 정보 수정 시 중복 체크
	 */
	@Transactional(readOnly = true)
	public void checkDuplicatedFields(MemberEditRequestDTO requestDTO) {

		isDuplicatedNickname(requestDTO.getNickname());

		isDuplicatedEmail(requestDTO.getEmail());
	}

	/**
	 * 회원 가입 시 중복 체크
	 */
	@Transactional(readOnly = true)
	public void checkDuplicatedFields(MemberRequestDTO memberRequestDTO) {

		isDuplicatedUsername(memberRequestDTO.getUsername());

		isDuplicatedNickname(memberRequestDTO.getNickname());

		isDuplicatedEmail(memberRequestDTO.getEmail());
	}

	/**
	 * 아이디 중복 체크
	 */
	private void isDuplicatedUsername(String username) {

		if (memberMapper.selectCountByUsername(username) > 0) {
			throw new BusinessException("아이디 중복");
		}
	}

	/**
	 * 닉네임 중복 체크
	 */
	private void isDuplicatedNickname(String nickname) {

		if (getExistingMember().getNickname().equals(nickname)) {
			return;
		}

		if (memberMapper.selectCountByNickname(nickname) > 0) {

			throw new BusinessException("닉네임 중복");
		}
	}

	/**
	 * 이메일 중복 체크
	 */
	private void isDuplicatedEmail(String email) {

		if (getExistingMember().getEmail().equals(email)) {
			return;
		}

		if (memberMapper.selectCountByEmail(email) > 0) {
			throw new BusinessException("이메일 중복");
		}
	}

	/**
	 * memberId에 해당하는 MemberVO 반환
	 */
	public MemberVO getExistingMember() {
		return memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId())
			.orElseThrow(() -> new BusinessException("해당 회원을 찾을 수 없습니다."));
	}

	/*
	 * memberId에 해당하는 Member 가 존재하는지 확인
	 */
	public void checkMemberExist() {
		Optional<MemberVO> memberVO = memberMapper.selectMemberByMemberId(SecurityUtil.getCurrentUserId());

		if (memberVO.isEmpty()) {
			throw new BusinessException("해당 회원을 찾을 수 없습니다.");
		}
	}
}
