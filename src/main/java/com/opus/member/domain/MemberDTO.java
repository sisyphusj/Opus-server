package com.opus.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDTO {

  @NotBlank
  private String username;

  @NotBlank
  @Setter
  private String password;

  @NotBlank
  private String nickname;

  @NotBlank
  private String email;

}
