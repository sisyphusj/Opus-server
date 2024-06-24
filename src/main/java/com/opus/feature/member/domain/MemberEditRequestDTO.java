package com.opus.feature.member.domain;

import lombok.Getter;

/**
 * 회원 정보 수정 요청 DTO
 */

@Getter
public class MemberEditRequestDTO {

	private String username;

	private String password;

	private String nickname;

	private String email;

}
