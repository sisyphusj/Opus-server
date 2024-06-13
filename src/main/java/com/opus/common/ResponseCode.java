package com.opus.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

  /**
   * 200 OK
   */
  SUCCESS(200, "200", "성공"),

  // User
  USER_LOGIN_SUCCESS(200, "200", "사용자 로그인 성공"),
  USER_LOGOUT_SUCCESS(200, "200", "사용자 로그아웃 성공"),
  USER_READ_SUCCESS(200, "200", "사용자 조회 성공"),
  USER_UPDATE_SUCCESS(200, "200", "사용자 정보 수정 성공"),
  USER_DELETE_SUCCESS(200, "200", "사용자 탈퇴 성공"),

  // Pin
  USER_PIN_LOAD_SUCCESS(200, "200", "사용자 pin 조회 성공"),
  PIN_LOAD_SUCCESS(200, "200", "pin 조회 성공"),
  PIN_TOTAL_LOAD_SUCCESS(200, "200", "total pin 조회 성공"),
  PIN_UPDATE_SUCCESS(200, "200", "pin 정보 수정 성공"),
  PIN_DELETE_SUCCESS(200, "200", "pin 삭제 성공"),

  // Business Error
  BUSINESS_ERROR(200, "999", "비즈니스 에러 발생"),

  /**
   * 201 CREATED
   */
  USER_SIGNUP_SUCCESS(201, "201", "회원가입 성공"),
  PIN_SAVE_SUCCESS(201, "201", "pin 저장 성공"),

  /**
   * 400 Bad Request
   */
  BAD_REQUEST(400, "B001", "잘못된 요청입니다."),
  INVALID_INPUT_VALUE(400, "B002", "입력값이 올바르지 않습니다."),
  REQUEST_BODY_NOT_READABLE(400, "B003", "요청 바디를 읽을 수 없습니다."),
  REQUEST_PARAMETER_NOT_VALID(400, "B004", "요청 파라미터가 올바르지 않습니다."),
  IO_EXCEPTION(400, "B005", "IO Exception 발생"),

  /**
   * 401 Unauthorized
   */
  USER_UNAUTHORIZED(401, "A001", "허가받지 않은 요청입니다."),
  SESSION_NOT_VALID(401, "A002", "세션이 유효하지 않습니다."),
  ID_OR_PASSWORD_NOT_VALID(401, "A003", "아이디 또는 비밀번호가 올바르지 않습니다."),

  /**
   * 404 Not Found
   */
  NOT_FOUND_ERROR(404, "U001", "요청한 리소스가 존재하지 않습니다."),
  NULL_POINT_ERROR(404, "U002", "Null Point Exception 발생"),

  /**
   * 405 Method Not Allowed
   */
  METHOD_NOT_ALLOWED(405, "M001", "허용되지 않은 메소드입니다."),

  /**
   * 409 Conflict
   */
//    USER_NICK_EXIST(409, "S001", "이미 사용중인 닉네임입니다."),
//    USER_ID_EXIST(409, "S002", "이미 사용중인 아이디입니다."),
//    USER_EMAIL_EXIST(409, "S003", "이미 사용중인 이메일입니다."),
  DATA_ALREADY_EXIST(409, "S004", "이미 존재하는 데이터입니다."),

  /**
   * 500 Internal Server Error
   */
  INTERNAL_SERVER_ERROR(500, "E001", "서버에 오류가 발생하였습니다.");


  private final int httpStatus;
  private final String code;
  private final String message;
}
