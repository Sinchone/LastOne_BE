package com.lastone.core.security.exception;

import lombok.Getter;

@Getter
public enum JwtVerificationErrorCode {

    ALGORITHM_MISMATCH_EXCEPTION("AlgorithmMismatchException", 403, "VT001", "토큰의 인코딩 알고리즘이 일치하지 않습니다." ),

    INVALID_CLAIM_EXCEPTION("InvalidClaimException", 400, "VT002", "토큰의 클레임 정보가 유효하지 않습니다."),

    JWT_DECODE_EXCEPTION("JWTDecodeException", 401, "VT003", "토큰을 해독할 수 없습니다."),

    SIGNATURE_VERIFICATION_EXCEPTION("SignatureVerificationException", 401, "VT004", "토큰에 등로된 서명이 일치하지 않습니다."),

    TOKEN_EXPIRED_EXCEPTION("TokenExpiredException", 401, "VT005", "만료된 토큰입니다.");

    private final String name;

    private final int status;

    private final String code;

    private final String message;

    JwtVerificationErrorCode(String name, int status, String code, String message) {
        this.name = name;
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
