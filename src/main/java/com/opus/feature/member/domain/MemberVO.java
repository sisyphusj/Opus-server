package com.opus.feature.member.domain;

import com.opus.utils.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberVO {

	private Integer memberId;

	private String username;

	private String password;

	private String nickname;

	private String email;

	public static MemberVO fromRegistrationDTO(MemberRequestDTO memberRequestDTO) {

		return MemberVO.builder()
			.username(memberRequestDTO.getUsername())
			.password(memberRequestDTO.getPassword())
			.nickname(memberRequestDTO.getNickname())
			.email(memberRequestDTO.getEmail())
			.build();
	}

	public static MemberVO of(MemberEditRequestDTO memberEditRequestDTO) {

		return MemberVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.username(memberEditRequestDTO.getUsername())
			.password(memberEditRequestDTO.getPassword())
			.nickname(memberEditRequestDTO.getNickname())
			.email(memberEditRequestDTO.getEmail())
			.build();
	}

	public void updateUsername(String username) {
		this.username = username;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateEmail(String email) {
		this.email = email;
	}
}
