package com.opus.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberInsertDTO {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String nickname;

  @NotBlank
  private String email;

  public void updatePassword(String password) {
    this.password = password;
  }

}
