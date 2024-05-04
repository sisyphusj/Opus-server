package com.opus.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDTO {

    @NotBlank
    private String id;

    @NotBlank
    @Setter
    private String pw;

    @NotBlank
    private String nick;

    @NotBlank
    private String email;

}
