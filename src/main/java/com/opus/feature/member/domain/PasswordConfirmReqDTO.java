package com.opus.feature.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordConfirmReqDTO {

	@NotBlank
	private String password;
}
