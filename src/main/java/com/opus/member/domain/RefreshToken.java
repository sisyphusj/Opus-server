package com.opus.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    private String key;

    private String value;

    public static RefreshToken of(String key, String value) {
        return new RefreshToken(key, value);
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
