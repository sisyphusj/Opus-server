package com.opus.exception;

import com.opus.common.ResponseCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final ResponseCode responseCode;

	public CustomException(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
}
