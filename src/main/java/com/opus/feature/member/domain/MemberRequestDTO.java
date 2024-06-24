package com.opus.feature.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberRequestDTO {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private String nickname;

	@NotBlank
	private String email;

	public void updatePassword(String password) {
		this.password = password;
	}

}
