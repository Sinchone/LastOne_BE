package com.lastone.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SuccessResponse{

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public SuccessResponse(String message) {
        this.message = message;
    }

    public SuccessResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
