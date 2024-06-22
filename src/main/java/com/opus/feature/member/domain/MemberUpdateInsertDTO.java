package com.opus.feature.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberUpdateInsertDTO {

	@NotBlank
	private String username;

	private String password;

	@NotBlank
	private String nickname;

	@NotBlank
	private String email;

	public void updatePassword(String password) {
		this.password = password;
	}

}
