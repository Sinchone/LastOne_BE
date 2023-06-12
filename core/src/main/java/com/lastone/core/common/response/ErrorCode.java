package com.lastone.core.common.response;

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
  NOT_CHAT_USER(404, "CR005", "채팅방에 참여한 사용자를 찾을 수 없습니다."),

 /* 이미지 타입 예외 */
  IMG_NOT_SUPPORTED(415, "I001", "해당 이미지 타입은 제공할 수 없는 타입입니다."),

  /* MyPage 예외 */
  MEMBER_NOT_FOUND(404, "M001", "해당 회원은 존재하지 않는 회원입니다."),
  MEMBER_ALREADY_EXIST(409, "M002", "해당 닉네임을 지닌 회원이 이미 존재합니다."),
//  MYPAGE_INPUT_FAILURE(400, "M003", "마이페이지 정보 입력 형태가 잘못되었습니다. Json 형식을 확인해주세요."),

  /* Securty 예외 */
  AUTHORIZATION_NOT_FOUND(404, "SH001", "해당 Authorization Header 값은 지원하지 않는 형식입니다."),
  ALREADY_LOGOUT_TOKEN(401, "ST002", "해당 토큰은 이미 로그아웃 처리 된 토큰입니다."),
  MEMBER_NOT_FOUND_IN_TOKEN(404, "ST003", "해당 토큰 안에 등록된 멤버 정보가 없습니다."),
  NOT_FOUND_REFRESH_TOKEN(404, "ST004", "서버에 해당 리프레시 토큰이 존재하지 않습니다."),
  UN_AUTHENTICATION_MEMBER(401, "ST005", "해당 API는 토큰을 필요로합니다. 로그인 후 이용해주세요."),

  /* Oauth2 예외 */
  OAUTH2_REGISTER_NOT_FOUND(404, "OA001", "해당 registerId는 지원되지 않습니다."),
  OAUTH2_ALREADY_USED_CODE(400, "OA002", "해당 Oauth2인증 토큰은 이미 사용되었거나 잘못된 형식의 토큰입니다."),
  OAUTH2_DEFAULT_EXCEPTION(400, "OA003", "OAuth2 로그인에 실패하였습니다."),
  OAUTH2_CODE_NOT_FOUND(400, "OA004", "인증에 필수 값인 Oauth2 code가 존재하지 않습니다."),

  /* JWT Token 예외 */
  ALGORITHM_MISMATCH_EXCEPTION(403, "VT001", "토큰의 인코딩 알고리즘이 일치하지 않습니다." ),
  INVALID_CLAIM_EXCEPTION(400, "VT002", "토큰의 클레임 정보가 유효하지 않습니다."),
  JWT_DECODE_EXCEPTION(401, "VT003", "토큰을 해독할 수 없습니다."),
  SIGNATURE_VERIFICATION_EXCEPTION(401, "VT004", "토큰에 등로된 서명이 일치하지 않습니다."),
  ACCESS_TOKEN_EXPIRED_EXCEPTION(401, "VT005", "만료된 어세스 토큰입니다."),
  REFRESH_TOKEN_EXPIRED_EXCEPTION(401, "VT006", "만료된 리프레시 토큰입니다."),
  JWT_DECODING_DEFAULT_EXCEPTION(401, "VT007", "JWT 해독 과정 중 에러가 발생하였습니다."),
  REFRESH_TOKEN_TYPE_IS_NEEDED(401, "VT008", "해당 API 호출에 필요한 토큰 타입은 리프레시 토큰입니다."),
  ACCESS_TOKEN_TYPE_IS_NEEDED(401, "VT009", "해당 API 호출에 필요한 토큰 타입은 어세스 토큰입니다."),

  /* Recruitment 예외 */
  RECRUITMENT_IMG_COUNT(400, "R001", "모집글 이미지 등록은 최대 3개까지 가능합니다."),
  RECRUITMENT_NOT_FOUND(404, "R002", "존재하지 않는 모집글입니다."),
  INCORRECT_WRITER(403, "R003", "모집글 작성자와 요청 회원의 정보가 일치하지 않습니다."),
  RECRUITMENT_ALREADY_DELETE(404, "R004", "해당 모집글을 이미 삭제 처리된 모집글입니다."),
  RECRUITMENT_NOT_INCLUDE_APPLICATION(404, "R005", "해당 모집글에 신청된 요청이 아닙니다."),
  IMPOSSIBLE_TO_MATCH_STATUS(403, "ROO6", "해당 모집글의 상태가 매칭을 완료할 수 없는 상태입니다."),

  /* Application 예외 */
  APPLICATION_NOT_FOUND(404, "A001", "존재하지 않는 신청입니다."),
  APPLICATION_NOT_EQUAL_REQUEST_ID(403, "A002", "신청 유저와 신청 취소 요청 유저 정보가 일치하지 않습니다."),
  APPLICATION_STATUS_INCORRECT(402, "A003", "신청 상태가 매칭 성공인 상태여야 취소가 가능합니다."),
  APPLICANT_EQUAL_TO_WRITER(400, "A004", "모집글 작성자와 신청자가 일치합니다"),
  APPLY_TO_CLOSED_RECRUITMENT(400, "A005", "이미 모집 마감되어 신청이 불가합니다."),
  APPLY_TO_EXPIRATION_RECRUITMENT(400, "A006", "모집글 기간이 만료되어 신청이 불가합니다."),
  ALREADY_APPLIED_RECRUITMENT(400, "A007", "동일한 모집글에 중복 신청은 불가능합니다."),
  ALREADY_MATCHING_COMPLETE(400, "A008", "이미 매칭이 완료된 모집글의 신청 취소는 불가능합니다."),

  /* 파트너 예외 */
  TODAY_PARTNER_NOT_FOUND(404, "P001", "오늘 파트너와의 약속이 존재하지 않습니다."),
  PARTNER_HISTORY_NOT_FOUND(404, "P002", "매칭된 파트너 목록이 없습니다."),

  /* 알림 예외 */
  NOTIFICATION_NOT_FOUND(404, "N001", "존재하지 않는 알림입니다."),
  NOTIFICATION_UPDATE_FORBIDDEN(403, "N002", "알림을 받은 회원의 정보와 요청자의 정보가 일치하지 않아 읽기 처리가 불가합니다."),
  NOTIFICATION_ALREADY_READ(409, "N003", "이미 읽기 처리 된 알림입니다."),
  NOTIFICATION_DELETE_FORBIDDEN(403, "N004", "알림 삭제 리스트 중에 권한이 없는 알림이 존재하여 삭제 처리가 불가합니다."),

  /* 글로벌 예외 */
  HTTP_REQUEST_METHOD_NOT_SUPPORTED(405, "G001", "지원하지 않는 HTTP 메서드 형식입니다."),
  HTTP_MEDIA_TYPE_EXCEPTION(415, "G002", "지원하지 않는 미디어 타입입니다. Json 혹은 Form-Data 형식인지 확인해주세요."),
  MISSING_PATH_VARIABLE(400, "G003", "경로 변수가 잘못 입력되었습니다. 해당 api 명세서의 경로변수 타입을 확인해주세요."),
  MISSING_REQUEST_PARAMETER(400, "G004", "요청된 처리에 필요한 파라미터가 누락되어있습니다."),
  PARAMETER_TYPE_NOT_SUPPORTED(400, "G005", "파라미터 타입을 잘못 입력하셨습니다."),
  HTTP_MESSAGE_NOT_READABLE(400, "G006", "요청 본문의 형식 또는 필수 데이터가 누락되어 있습니다."),
  HTTP_MESSAGE_NOT_WRITEABLE(500, "G007", "서버에서 응답을 위한 데이터 변환에 실패하였습니다."),
  METHOD_ARGUMENT_NOT_VALID(400, "G008", "입력한 데이터의 유효성 검사에 실패하였습니다."),
  MISSING_REQUEST_PART(400, "G009", "이미지 또는 파일과 같은 멀티파트 파일 처리에 필요한 데이터가 누락되었거나 잘못입력되었습니다."),
  HANDLER_NOT_FOUND(404, "G010", "요청하신 경로는 제공되지 않는 API 입니다. 요청 경로를 확인해주세요."),


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