package com.opus.component;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opus.exception.BusinessException;
import com.opus.feature.member.domain.MemberEditRequestDTO;
import com.opus.feature.member.domain.MemberRequestDTO;
import com.opus.feature.member.mapper.MemberMapper;

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
		if (memberMapper.selectCountByNickname(nickname) > 0) {
			throw new BusinessException("닉네임 중복");
		}
	}

	/**
	 * 이메일 중복 체크
	 */
	private void isDuplicatedEmail(String email) {
		if (memberMapper.selectCountByEmail(email) > 0) {
			throw new BusinessException("이메일 중복");
		}
	}
}
