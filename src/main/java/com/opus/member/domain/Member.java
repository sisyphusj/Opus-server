package com.opus.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    private Integer m_id;

    private String id;

    private String pw;

    private String nickname;

    private String email;
}
