package com.lastone.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SuccessResponse<T> {

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public SuccessResponse(String message) {
        this.message = message;
    }

    public SuccessResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
