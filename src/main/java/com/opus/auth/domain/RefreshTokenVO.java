package com.opus.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RefreshTokenVO {

  // memberId
  private int key;

  // refresh token
  private String value;

  public void updateValue(String value) {
    this.value = value;
  }

}
