package com.opus.feature.member.domain;

import com.opus.feature.member.service.DuplicateCheckAttributes;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberRequestDTO implements DuplicateCheckAttributes {

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
