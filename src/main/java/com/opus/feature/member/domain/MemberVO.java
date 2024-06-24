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

	public static MemberVO fromRegistrationDTO(MemberRegisterReqDTO memberRegisterReqDTO) {

		return MemberVO.builder()
			.username(memberRegisterReqDTO.getUsername())
			.password(memberRegisterReqDTO.getPassword())
			.nickname(memberRegisterReqDTO.getNickname())
			.email(memberRegisterReqDTO.getEmail())
			.build();
	}

	public static MemberVO of(MemberEditReqDTO memberEditReqDTO) {

		return MemberVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.password(memberEditReqDTO.getPassword())
			.nickname(memberEditReqDTO.getNickname())
			.email(memberEditReqDTO.getEmail())
			.build();
	}
}
