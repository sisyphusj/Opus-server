package com.opus.feature.member.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberRegisterReqDTO {

	@NotBlank
	private String username;

	@NotBlank
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
