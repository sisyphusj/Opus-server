package com.opus.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberVO {
    private Integer memberId;

    private String username;

    private String password;

    private String nickname;

    private String email;

    // 불필요한 생성자 제거 및 정적 팩토리 메소드 적용
    public static MemberVO of(MemberDTO memberDTO, Integer memberId, PasswordEncoder passwordEncoder) {
        String rawPassword = memberDTO.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);

        return new MemberVO(memberId, memberDTO.getUsername(), encPassword, memberDTO.getNickname(), memberDTO.getEmail());
    }

    public static MemberVO of(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
        String rawPassword = memberDTO.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);

        return new MemberVO(null, memberDTO.getUsername(), encPassword, memberDTO.getNickname(), memberDTO.getEmail());
    }
}
