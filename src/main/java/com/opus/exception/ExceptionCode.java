package com.opus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionCode {

    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "B001", "잘못된 요청입니다."),

    /**
     * 401 Unauthorized
     */
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "허가받지 않은 요청입니다."),
    SESSION_NOT_VALID(HttpStatus.UNAUTHORIZED, "A002", "세션이 종료되었습니다."),

    /**
     * 404 Not Found
     */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),

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
    private final String message;
}
