package com.opus.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.opus.exception.BusinessException;
import com.opus.feature.member.domain.MemberEditRequestDTO;
import com.opus.feature.member.domain.MemberRequestDTO;
import com.opus.feature.member.domain.MemberVO;
import com.opus.feature.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckMemberField {

	private final MemberMapper memberMapper;

	private final PasswordEncoder passwordEncoder;

	/**
	 * 회원 정보 수정 시 유효성, 중복 체크 후 MemberVO 반환
	 */
	public MemberVO validateAndCheckDuplicatedFields(MemberEditRequestDTO requestDTO, MemberVO oldMemberVO) {

		MemberVO updatedMemberVO = MemberVO.of(requestDTO);

		// 아이디 유효성, 중복 체크
		if (isUsernameValid(requestDTO.getUsername(), oldMemberVO)) {

			isDuplicatedUsername(requestDTO.getUsername());

			updatedMemberVO.updateUsername(requestDTO.getUsername());
		}

		// 비밀번호 유효성 체크
		if (isPasswordValid(requestDTO.getPassword(), oldMemberVO)) {

			updatedMemberVO.updatePassword(passwordEncoder.encode(requestDTO.getPassword()));
		}

		// 닉네임 유효성, 중복 체크
		if (isNicknameValid(requestDTO.getNickname(), oldMemberVO)) {

			isDuplicatedNickname(requestDTO.getNickname());

			updatedMemberVO.updateNickname(requestDTO.getNickname());
		}

		// 이메일 유효성, 중복 체크
		if (isEmailValid(requestDTO.getEmail(), oldMemberVO)) {

			isDuplicatedEmail(requestDTO.getEmail());

			updatedMemberVO.updateEmail(requestDTO.getEmail());
		}

		return updatedMemberVO;
	}

	/**
	 * 회원 가입 시 중복 체크
	 */
	public void checkDuplicatedFields(MemberRequestDTO memberRequestDTO) {

		isDuplicatedUsername(memberRequestDTO.getUsername());

		isDuplicatedNickname(memberRequestDTO.getNickname());

		checkEmailForm(memberRequestDTO.getEmail());

		isDuplicatedEmail(memberRequestDTO.getEmail());
	}

	/**
	 * 아이디가 유효하고 기존 아이디와 다르다면 true 반환
	 */
	private boolean isUsernameValid(String newUsername, MemberVO oldMemberVO) {
		return !StringUtils.isBlank(newUsername) && !newUsername.equals(oldMemberVO.getUsername());
	}

	/**
	 * 닉네임이 유효하고 기존 닉네임과 다르다면 true 반환
	 */
	private boolean isNicknameValid(String newNickname, MemberVO oldMemberVO) {
		return !StringUtils.isBlank(newNickname) && !newNickname.equals(oldMemberVO.getNickname());
	}

	/**
	 * 이메일이 유효하고 기존 이메일과 다르다면 true 반환
	 */
	private boolean isEmailValid(String newEmail, MemberVO oldMemberVO) {

		checkEmailForm(newEmail);

		return !StringUtils.isBlank(newEmail) && !newEmail.equals(oldMemberVO.getEmail());
	}

	/**
	 * 비밀번호가 유효하고 기존 비밀번호와 다르다면 true 반환
	 */
	private boolean isPasswordValid(String newPassword, MemberVO oldMemberVO) {
		return !StringUtils.isBlank(newPassword) && !passwordEncoder.matches(newPassword, oldMemberVO.getPassword());
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

	/**
	 * 이메일 형식 체크
	 */
	private void checkEmailForm(String email) {

		// 이메일 형식 정규식
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

		// 이메일 형식 체크
		if (!email.matches(emailRegex)) {
			throw new BusinessException("유효하지 않은 이메일 형식입니다.");
		}
	}
}
