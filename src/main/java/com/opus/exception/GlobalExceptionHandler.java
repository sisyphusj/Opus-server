package com.opus.exception;

import java.util.NoSuchElementException;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.opus.common.ErrorResponse;
import com.opus.common.ResponseCode;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * API 호출 시 유효하지 않은 값인 경우
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		log.error("handleMethodArgumentNotValidException", e);
		BindingResult bindingResult = e.getBindingResult();
		StringBuilder sb = new StringBuilder();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			sb.append(fieldError.getField()).append(" : ");
			sb.append(fieldError.getDefaultMessage());
			sb.append(", ");
		}

		final ErrorResponse response = ErrorResponse.of(ResponseCode.INVALID_INPUT_VALUE, String.valueOf(sb));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 인수가 잘못된 경우
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {

		log.error("handleIllegalArgumentException", e);

		final String message = e.getMessage() != null ? e.getMessage() : "Invalid arguments provided";

		final ErrorResponse response = ErrorResponse.of(ResponseCode.INVALID_INPUT_VALUE, message);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * API 호출 시 필수 요청 헤더가 없는 경우
	 */
	@ExceptionHandler(MissingRequestHeaderException.class)
	protected ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {

		log.error("handleMissingRequestHeaderException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.REQUEST_BODY_NOT_READABLE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * API 호출 시 요청 바디를 읽을 수 없는 경우
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

		log.error("handleHttpMessageNotReadableException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.REQUEST_BODY_NOT_READABLE, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * API 호출 시 필수 요청 파라미터가 없는 경우
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
		MissingServletRequestParameterException e) {

		log.error("handleMissingServletRequestParameterException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.REQUEST_PARAMETER_NOT_VALID, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 잘못된 서버 요청
	 */
	@ExceptionHandler(HttpClientErrorException.class)
	protected ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException e) {

		log.error("handleHttpClientErrorException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 인증 예외 처리
	 */
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {

		log.error("handleAuthenticationException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.USER_UNAUTHORIZED, "인증 오류");
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * 자격 증명 누락
	 */
	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationCredentialsNotFoundException(
		AuthenticationCredentialsNotFoundException e) {

		log.error("handleAuthenticationCredentialsNotFoundException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.USER_UNAUTHORIZED, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * 토큰 만료 처리
	 */
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException e) {

		log.error("handleExpiredJwtException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.USER_UNAUTHORIZED, "토큰이 만료되었습니다.");
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	/**
	 *
	 */
	@ExceptionHandler(InsufficientAuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientAuthenticationException(
		InsufficientAuthenticationException e) {

		log.error("InsufficientAuthenticationException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.USER_UNAUTHORIZED, "로그인이 필요합니다.");
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * 잘못된 주소로 요청한 경우
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {

		log.error("handleNoHandlerFoundException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.NOT_FOUND_ERROR, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * 잘못된 HTTP 메소드로 요청한 경우
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {

		log.error("handleHttpRequestMethodNotSupportedException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.METHOD_NOT_ALLOWED, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * ResponseStatusException 처리
	 */
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {

		log.error("handleResponseStatusException", e);

		final ErrorResponse response = ErrorResponse.of(e.getResponseCode(), e.getResponseCode().getMessage());
		return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.getResponseCode().getHttpStatus()));
	}

	/**
	 * BadCredentialsException 처리
	 */
	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {

		log.error("handleBadCredentialsException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.ID_OR_PASSWORD_NOT_VALID, "잘못된 자격증명입니다.");
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * UsernameNotFoundException 처리
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {

		log.error("handleUsernameNotFoundException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.USER_NOT_FOUND,
			ResponseCode.USER_NOT_FOUND.getMessage());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * NoSuchElementException 처리
	 */
	@ExceptionHandler(NoSuchElementException.class)
	protected ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {

		log.error("handleNoSuchElementException", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.NOT_FOUND_ERROR, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * 모든 예외 처리 시스템 내부 오류 처리 (사용자에게 노출되지 않음)
	 */
	@ExceptionHandler(Exception.class)
	protected final ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {

		log.error("error : ", e);

		final ErrorResponse response = ErrorResponse.of(ResponseCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 비즈니스 로직에서 발생하는 예외 처리
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {

		log.error("handleBusinessException", e);

		final ErrorResponse response = ErrorResponse.of(e.getResponseCode(), e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
