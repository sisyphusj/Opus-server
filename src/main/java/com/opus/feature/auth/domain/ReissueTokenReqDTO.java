package com.opus.feature.auth.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReissueTokenReqDTO {

	@NotBlank
	private String refreshToken;
}
