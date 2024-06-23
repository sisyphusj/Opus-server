package com.opus.feature.member.service;

/**
 * 중복 체크 속성을 가지는 DTO가 구현해야 하는 인터페이스
 */

public interface DuplicateCheckAttributes {

	String getUsername();

	String getNickname();

	String getEmail();
}


