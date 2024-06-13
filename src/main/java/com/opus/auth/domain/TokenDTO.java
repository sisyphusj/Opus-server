package com.opus.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDTO {

  private String grantType;

  private String accessToken;

  private String refreshToken;

  private Long accessTokenExpiresIn;

}
