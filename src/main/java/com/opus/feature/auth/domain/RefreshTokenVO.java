package com.opus.feature.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RefreshTokenVO {

	// memberId
	private int key;

	// refresh token
	private String value;

}
