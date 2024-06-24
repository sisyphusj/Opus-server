package com.opus.feature.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberResDTO {

	private String username;

	private String nickname;

	private String email;

	public static MemberResDTO of(MemberVO member) {

		return MemberResDTO.builder()
			.username(member.getUsername())
			.nickname(member.getNickname())
			.email(member.getEmail())
			.build();
	}
}
