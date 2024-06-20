package com.opus.common;

import java.util.List;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldError {

	private final String field;

	private final String value;

	private final String reason;

	/**
	 * 검증 오류 보관 객체인 BindingResult를 변환
	 */
	public static List<FieldError> of(final BindingResult bindingResult) {
		final List<org.springframework.validation.FieldError> errors = bindingResult.getFieldErrors();

		return errors.stream()
			.map(error -> new FieldError(error.getField(),
				error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
				error.getDefaultMessage())).toList();
	}
}
