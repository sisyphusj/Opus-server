package com.opus.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenVO {

    // 사용자 아이디를 Key로 사용
    private String key;

    private String value;

    public static RefreshTokenVO of(String key, String value) {
        return new RefreshTokenVO(key, value);
    }

    public RefreshTokenVO updateValue(String token) {
        this.value = token;
        return this;
    }
}
