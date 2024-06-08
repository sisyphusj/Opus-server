package com.opus.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDTO {

    @NotBlank
    private String userId;

    @NotBlank
    @Setter
    private String pw;

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

}
