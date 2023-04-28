package com.lastone.core.security.jwt;

import lombok.Getter;

@Getter
public enum TokenHeaderType {
    TEST_TOKEN("test"),
    BEARER_TOKEN("Bearer ");
    private final String tokenHeader;
    TokenHeaderType(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }
}