package com.opus.member.domain;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    private Integer mId;

    private String id;

    private String pw;

    private String nickname;

    private String email;

    // 불필요한 생성자 제거 및 정적 팩토리 메소드 적용
    public static Member of(MemberDTO memberDTO, Integer mId) {
        return new Member(mId, memberDTO.getId(), memberDTO.getPw(), memberDTO.getNickname(), memberDTO.getEmail());
    }

    public static Member of(MemberDTO memberDTO) {
        return new Member(null, memberDTO.getId(), memberDTO.getPw(), memberDTO.getNickname(), memberDTO.getEmail());
    }

    public static Member of(LoginDTO loginDTO) {
        return new Member(null, loginDTO.getId(), loginDTO.getPw(), null, null);
    }
}
