package com.opus.member.domain;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    private Integer memberId;

    private String userId;

    private String pw;

    private String nickname;

    private String email;

    // 불필요한 생성자 제거 및 정적 팩토리 메소드 적용
    public static Member of(MemberDTO memberDTO, Integer memberId) {
        return new Member(memberId, memberDTO.getUserId(), memberDTO.getPw(), memberDTO.getNickname(), memberDTO.getEmail());
    }

    public static Member of(MemberDTO memberDTO) {
        return new Member(null, memberDTO.getUserId(), memberDTO.getPw(), memberDTO.getNickname(), memberDTO.getEmail());
    }

    public static Member of(LoginDTO loginDTO) {
        return new Member(null, loginDTO.getUserId(), loginDTO.getPw(), null, null);
    }
}
