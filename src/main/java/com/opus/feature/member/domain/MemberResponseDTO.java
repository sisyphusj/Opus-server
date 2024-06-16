package com.opus.feature.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponseDTO {

	private String username;

	private String nickname;

	private String email;

	public static MemberResponseDTO of(MemberVO member) {
		return MemberResponseDTO.builder()
			.username(member.getUsername())
			.nickname(member.getNickname())
			.email(member.getEmail())
			.build();
	}
}
