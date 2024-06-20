package com.opus.common;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

	private int status;

	private String code;

	private String msg;

	private List<FieldError> errors;

	private String reason;

	@Builder
	protected ErrorResponse(final ResponseCode responseCode) {
		this.msg = responseCode.getMessage();
		this.status = responseCode.getHttpStatus();
		this.code = responseCode.getCode();
		this.errors = new ArrayList<>();
	}

	@Builder
	protected ErrorResponse(final ResponseCode responseCode, final String reason) {
		this.msg = responseCode.getMessage();
		this.status = responseCode.getHttpStatus();
		this.code = responseCode.getCode();
		this.reason = reason;
	}

	public static ErrorResponse of(final ResponseCode responseCode) {
		return new ErrorResponse(responseCode);
	}

	public static ErrorResponse of(final ResponseCode responseCode, final String reason) {
		return new ErrorResponse(responseCode, reason);
	}
}
