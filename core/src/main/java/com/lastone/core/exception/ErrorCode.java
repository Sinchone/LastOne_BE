package com.lastone.core.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

  SYSTEM_EXCEPTION(500, "S000", "Internal Server Error"),
  NOT_FOUND_HANDLER(404, "S404", "404 NOT FOUND"),

  INVALID_INPUT_VALUE(400, "L001", "Invalid Input Value"),
  METHOD_NOT_ALLOWED(405, "L002", "Method not allowed"),
  INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
  INVALID_TYPE_VALUE(400, "C005", "Invalid Type Value"),

  HANDLE_ACCESS_DENIED(403, "B006", "Access is Denied"),
  UNAUTHORIZED(401, "U002", "권한이 없습니다."),

  NOT_FOUND_USER(404, "U001", "No such User"),
  NOT_FOUND_POST(404, "P001", "No such Post"),

  JWT_DECODE_FAILURE(500, "J001", "JWT cannot be decoded"),
  JWT_ENCODE_FAILURE(500, "J002", "DTO encode failure"),

  FILE_FETCH_FAILURE(500, "S001", "File fetch failure (from storage)"),

  /* Chatting */
  ALREADY_DELETED_CHAT_ROOM(404, "CR001", "지워진 채팅방입니다."),
  BLOCKED_CHAT_ROOM(403, "CR002", "차단된 채팅방입니다."),
  NOT_FOUNT_ROOM(404, "CR003", "채팅방을 찾을 수 없습니다."),
  NOT_CHAT_PARTICIPANT(403, "CR004", "채팅방에 참여한 사용자가 아닙니다."),


  /* MyPage 예외 */
  MEMBER_NOT_FOUND(404, "M001", "해당 회원은 존재하지 않는 회원입니다."),
  MEMBER_ALREADY_EXIST(409, "M002", "해당 닉네임을 지닌 회원이 이미 존재합니다."),
  MYPAGE_INPUT_FAILURE(400, "M003", "마이페이지 정보 입력 형태가 잘못되었습니다. Json 형식을 확인해주세요."),

  /* Securty 예외 */
  AUTHORIZATION_NOT_FOUND(404, "SH001", "해당 Authorization Header 값은 지원하지 않는 형식입니다."),
  ALREADY_LOGOUT_TOKEN(401, "ST001", "해당 토큰은 이미 로그아웃 처리 된 토큰입니다."),
  MEMBER_NOT_FOUND_IN_TOKEN(404, "ST002", "해당 토큰 안에 등록된 멤버 정보가 없습니다."),
  NOT_FOUND_REFRESH_TOKEN(404, "ST003", "서버에 해당 리프레시 토큰이 존재하지 않습니다.");

  /* Oauth2 예외 */
  OAUTH2_REGISTER_NOT_FOUND(404, "A001", "해당 registerId는 지원되지 않습니다.")

  

  ;
  private final String code;
  private final String message;
  private int status;

  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }
}