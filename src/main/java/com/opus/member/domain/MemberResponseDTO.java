package com.opus.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseDTO {

    private String userName;

    private String nickname;

    private String email;

    public static MemberResponseDTO of(MemberVO member) {
        return new MemberResponseDTO(member.getUserName(), member.getNickname(), member.getEmail());
    }
}
