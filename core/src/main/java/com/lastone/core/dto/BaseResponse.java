package com.lastone.core.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseResponse {
    int statusCode;

    public BaseResponse(int statusCode) {
        this.statusCode = statusCode;
    }
}
