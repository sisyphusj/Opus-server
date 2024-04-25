package com.opus.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDTO {

    @NotBlank
    private String id;

    @NotBlank
    private String pw;

}
