package com.opus.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberVO {

  private Integer memberId;

  private String username;

  private String password;

  private String nickname;

  private String email;

  public static MemberVO of(MemberDTO memberDTO, Integer memberId) {
    return MemberVO.builder()
        .memberId(memberId)
        .username(memberDTO.getUsername())
        .password(memberDTO.getPassword())
        .nickname(memberDTO.getNickname())
        .email(memberDTO.getEmail())
        .build();
  }

  public static MemberVO of(MemberDTO memberDTO) {
    return MemberVO.builder()
        .username(memberDTO.getUsername())
        .password(memberDTO.getPassword())
        .nickname(memberDTO.getNickname())
        .email(memberDTO.getEmail())
        .build();
  }
}
