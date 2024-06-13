package com.opus.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class RefreshTokenVO {

    // memberId
    private int key;

    // refresh token
    @Setter
    private String value;

}
