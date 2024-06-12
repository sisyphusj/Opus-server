package com.opus.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthVO {

    private Integer memberId;

    private String username;

    private String password;

    private String nickname;

    private String email;

}
