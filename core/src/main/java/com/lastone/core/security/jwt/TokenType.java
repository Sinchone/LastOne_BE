package com.lastone.core.security.jwt;

import lombok.Getter;

@Getter
public enum TokenType {
    TEST_TOKEN("test"),
    BEARER_TOKEN("Bearer ");
    private final String tokenHeader;
    TokenType(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }
}
