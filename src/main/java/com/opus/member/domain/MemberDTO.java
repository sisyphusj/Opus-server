package com.opus.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberDTO {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

}
