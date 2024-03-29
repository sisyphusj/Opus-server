package com.opus.member.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private int m_id;
    private String id;
    private String pw;
    private String nickname;
    private String email;
}
