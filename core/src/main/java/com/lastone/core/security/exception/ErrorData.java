package com.lastone.core.security.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@AllArgsConstructor
@Builder
public class ErrorData  {
    private String errorCode;
    private String message;

    public static ErrorData create(String errorCode, String message) {
        return ErrorData.builder()
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}
