package com.lastone.core.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommonResponse<T> {
    @JsonIgnore
    private int status;
    private Result result;
    private T data;
    private String message;
    private String errorCode;

    public static <T> CommonResponse<T> success(T data) {
        return success(data, null);
    }

    public static <T> CommonResponse<T> success(SuccessCode successCode) {
        return success(successCode.getMessage());
    }

    public static <T> CommonResponse<T> success(String message) {
        return (CommonResponse<T>) CommonResponse.builder()
                        .result(Result.SUCCESS)
                        .message(message)
                        .build();
    }

    public static <T> CommonResponse<T> success(T data, String message) {
        return (CommonResponse<T>) CommonResponse.builder()
                .result(Result.SUCCESS)
                .data(data)
                .message(message)
                .build();
    }

    public static <T> CommonResponse<T> success(SuccessCode successCode, T data) {
        return (CommonResponse<T>) CommonResponse.builder()
                .result(Result.SUCCESS)
                .data(data)
                .message(successCode.getMessage())
                .build();
    }



    public static <T> CommonResponse<T> success(int status, T data, String message) {
        return (CommonResponse<T>) CommonResponse.builder()
                .result(Result.SUCCESS)
                .data(data)
                .message(message)
                .status(status)
                .build();
    }

    public static CommonResponse fail(ErrorCode errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(errorCode.getMessage())
                .errorCode(errorCode.getCode())
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }
}
