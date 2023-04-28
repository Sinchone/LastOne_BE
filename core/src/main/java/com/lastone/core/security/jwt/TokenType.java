package com.lastone.core.security.jwt;

public enum TokenType {

    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken");

    private final String tokenType;

    TokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }
}
