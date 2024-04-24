package com.opus.common;

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
    USER_LOGIN_SUCCESS(HttpStatus.OK, "200", "사용자 로그인 성공"),
    USER_READ_SUCCESS(HttpStatus.OK, "200", "사용자 조회 성공"),
    USER_UPDATE_SUCCESS(HttpStatus.OK, "200", "사용자 정보 수정 성공"),
    USER_DELETE_SUCCESS(HttpStatus.OK, "200", "사용자 탈퇴 성공"),

    // Pin
    USER_PIN_LOAD_SUCCESS(HttpStatus.OK, "200", "사용자 pin 조회 성공"),
    PIN_LOAD_SUCCESS(HttpStatus.OK, "200", "pin 조회 성공"),
    PIN_TOTAL_LOAD_SUCCESS(HttpStatus.OK, "200", "total pin 조회 성공"),
    PIN_UPDATE_SUCCESS(HttpStatus.OK, "200", "pin 정보 수정 성공"),
    PIN_DELETE_SUCCESS(HttpStatus.OK, "200", "pin 삭제 성공"),


    /**
     * 201 CREATED
     */
    USER_SIGNUP_SUCCESS(HttpStatus.CREATED, "201", "회원가입 성공"),
    PIN_SAVE_SUCCESS(HttpStatus.CREATED, "201", "pin 저장 성공"),

    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "B001", "잘못된 요청입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "B002", "입력값이 올바르지 않습니다."),
    REQUEST_BODY_NOT_READABLE(HttpStatus.BAD_REQUEST, "B003", "요청 바디를 읽을 수 없습니다."),
    REQUEST_PARAMETER_NOT_VALID(HttpStatus.BAD_REQUEST, "B004", "요청 파라미터가 올바르지 않습니다."),
    IO_EXCEPTION(HttpStatus.BAD_REQUEST, "B005", "IO Exception 발생"),

    /**
     * 401 Unauthorized
     */
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "허가받지 않은 요청입니다."),
    SESSION_NOT_VALID(HttpStatus.UNAUTHORIZED, "A002", "세션이 종료되었습니다."),

    /**
     * 404 Not Found
     */
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "U001", "요청한 리소스가 존재하지 않습니다."),
    NULL_POINT_ERROR(HttpStatus.NOT_FOUND, "U002", "Null Point Exception 발생"),

    /**
     * 409 Conflict
     */
    USER_NAME_EXIST(HttpStatus.CONFLICT, "S001", "이미 사용중인 닉네임입니다."),
    USER_ID_EXIST(HttpStatus.CONFLICT, "S002", "이미 사용중인 아이디입니다."),

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "서버에 오류가 발생하였습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;
}
