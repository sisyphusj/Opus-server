package com.opus.feature.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordConfirmDTO {

	@NotBlank
	private String password;
}
