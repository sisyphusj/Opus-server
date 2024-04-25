package com.opus.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupDTO {

    @NotBlank
    private String id;

    @NotBlank
    private String pw;

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;
}
