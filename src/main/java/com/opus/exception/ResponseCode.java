package com.opus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    /**
     * 200 OK
     */

    // User
    USER_LOGIN_SUCCESS(HttpStatus.OK, "사용자 로그인 성공"),
    USER_READ_SUCCESS(HttpStatus.OK, "사용자 조회 성공"),
    USER_UPDATE_SUCCESS(HttpStatus.OK, "사용자 정보 수정 성공"),
    USER_DELETE_SUCCESS(HttpStatus.OK, "사용자 탈퇴 성공"),

    // Pin
    USER_PIN_LOAD_SUCCESS(HttpStatus.OK, "사용자 pin 조회 성공"),
    PIN_LOAD_SUCCESS(HttpStatus.OK, "pin 조회 성공"),
    PIN_TOTAL_LOAD_SUCCESS(HttpStatus.OK, "total pin 조회 성공"),
    PIN_UPDATE_SUCCESS(HttpStatus.OK, "pin 정보 수정 성공"),
    PIN_DELETE_SUCCESS(HttpStatus.OK, "pin 삭제 성공"),


    /**
     * 201 CREATED
     */
    USER_SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    PIN_SAVE_SUCCESS(HttpStatus.CREATED, "pin 저장 성공");



    private final HttpStatus httpStatus;
    private final String message;
}
