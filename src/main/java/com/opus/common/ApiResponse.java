package com.opus.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * ApiResponse - API 응답 클래스
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

	private int status;
	private T data;

	public static <T> ResponseEntity<T> of(T data) {
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
