package com.opus.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberDTO {

    @NotBlank
    private String id;

    @NotBlank
    private String pw;

    @NotBlank
    private String nick;

    @NotBlank
    private String email;
}
