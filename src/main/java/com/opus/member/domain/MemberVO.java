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

  public static MemberVO of(MemberInsertDTO memberInsertDTO, Integer memberId) {
    return MemberVO.builder()
        .memberId(memberId)
        .username(memberInsertDTO.getUsername())
        .password(memberInsertDTO.getPassword())
        .nickname(memberInsertDTO.getNickname())
        .email(memberInsertDTO.getEmail())
        .build();
  }

  public static MemberVO of(MemberInsertDTO memberInsertDTO) {
    return MemberVO.builder()
        .username(memberInsertDTO.getUsername())
        .password(memberInsertDTO.getPassword())
        .nickname(memberInsertDTO.getNickname())
        .email(memberInsertDTO.getEmail())
        .build();
  }
}
