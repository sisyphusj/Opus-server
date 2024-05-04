package com.opus.exception;

import com.opus.common.ErrorResponse;
import com.opus.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * API 호출 시 유효하지 않은 값인 경우
     *
     * @param e MethodArgumentNotValidException
     * @return ResponseEntity<ErrorResponse>
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
     * @param e IllegalArgumentException
     * @return ResponseEntity<ErrorResponse>
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
     *
     * @param e MissingRequestHeaderException
     *          return ResponseEntity<ErrorResponse>
     */

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.error("handleMissingRequestHeaderException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.REQUEST_BODY_NOT_READABLE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * API 호출 시 요청 바디를 읽을 수 없는 경우
     *
     * @param e HttpMessageNotReadableException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.REQUEST_BODY_NOT_READABLE, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * API 호출 시 필수 요청 파라미터가 없는 경우
     *
     * @param e MissingServletRequestParameterException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("handleMissingServletRequestParameterException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.REQUEST_PARAMETER_NOT_VALID, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * IO Exception
     *
     * @param e IOException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ErrorResponse> handleIOException(IOException e) {
        log.error("handleIOException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.IO_EXCEPTION, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 잘못된 서버 요청
     *
     * @param e HttpClientErrorException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException e) {
        log.error("handleHttpClientErrorException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 인증 예외 처리
     *
     * @param e AuthenticationException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        log.error("handleAuthenticationException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.USER_UNAUTHORIZED, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 잘못된 주소로 요청한 경우
     *
     * @param e NoHandlerFoundException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("handleNoHandlerFoundException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.NOT_FOUND_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Null Point Exception
     *
     * @param e NullPointerException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
        log.error("handleNullPointerException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.NULL_POINT_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * 잘못된 HTTP 메소드로 요청한 경우
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.METHOD_NOT_ALLOWED, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 중복된 데이터가 있는 경우
     *
     * @param e DuplicateEntryException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(DuplicateEntryException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicateEntryException(DuplicateEntryException e) {
        log.error("handleDuplicateEntryException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.DATA_ALREADY_EXIST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * 모든 예외 처리
     *
     * @param e Exception
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        log.error("handleException {} , {}", e, e.toString());
        final ErrorResponse response = ErrorResponse.of(ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 비즈니스 로직에서 발생하는 예외 처리
     *
     * @param e BusinessExceptionHandler
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(BusinessExceptionHandler.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessExceptionHandler e) {
        log.error("handleBusinessException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.BUSINESS_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
