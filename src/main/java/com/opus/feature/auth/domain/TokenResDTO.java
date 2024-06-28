package com.opus.feature.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenResDTO {

	private String accessToken;

	private String refreshToken;

	private Long accessTokenExpiresIn;

}
