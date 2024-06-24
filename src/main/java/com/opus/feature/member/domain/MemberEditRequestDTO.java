package com.opus.feature.member.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 회원 정보 수정 요청 DTO
 */

@Getter
public class MemberEditRequestDTO {

	private String password;

	@NotBlank
	private String nickname;

	@NotBlank
	@Email
	private String email;

	public void updatePassword(String password) {
		this.password = password;
	}
}
