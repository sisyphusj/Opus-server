package com.opus.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenDTO {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    public static TokenDTO of(String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn) {
        return new TokenDTO(grantType, accessToken, refreshToken, accessTokenExpiresIn);
    }
}
