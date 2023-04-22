package com.lastone.core.common.response;

import lombok.Getter;

@Getter
public enum SuccessCode {
    CREATED_CHAT_ROOM("채팅방이 생성되었습니다."),
    DELETED_CHAT_ROOM("채팅방이 삭제되었습니다."),
    GET_CHAT_ROOM_LIST("채팅방 목록 조회에 성공하였습니다."),

    /* My Page */
    INQUIRE_MYPAGE("마이페이지 정보 조회에 성공하였습니다."),
    UPDATE_MYPAGE("마이페이지 수정 작업에 성공하였습니다."),

    /* TOKEN */
    OAUTH2_LOGIN("Oauth2 로그인이 완료되었습니다."),
    TOKEN_LOGOUT("로그아웃을 완료하였습니다."),
    TOKEN_REFRESH("토큰 재발급을 완료하였습니다."),

    /* 모집글 */
    RECRUITMENT_LIST("모집글 리스트 조회에 성공하였습니다."),
    RECRUITMENT_LIST_FOR_MAIN("메인 페이지 모집글 리스트 조회에 성공하였습니다."),
    RECRUITMENT_DETAIL("모집글 상세 정보 조회에 성공하였습니다."),
    RECRUITMENT_CREATE("모집글 작성이 완료되었습니다."),
    RECRUITMENT_UPDATE("모집글 수정이 완료되었습니다."),
    RECRUITMENT_DELETE("모집글 삭제가 완료되었습니다."),

    /* 신청 */
    APPLYMENT_CREATE("신청 처리가 완료되었습니다.")
    ;

    private String message;

    SuccessCode(String message) {
        this.message = message;
    }
}
