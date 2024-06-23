package com.opus.exception;

import com.opus.common.ResponseCode;

import lombok.Getter;

/**
 * JWT 필터 처리 중 발생하는 예외를 처리하기 위한 클래스
 */

@Getter
public class CustomException extends RuntimeException {

	private final ResponseCode responseCode;

	public CustomException(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
}
