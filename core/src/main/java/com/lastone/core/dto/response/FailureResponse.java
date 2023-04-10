package com.lastone.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class FailureResponse extends BaseResponse {

    private String errorCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidationError> errors;

    public FailureResponse(int statusCode, String errorCode, String message) {
        super(statusCode);
        this.errorCode = errorCode;
        this.message = message;
    }
    public FailureResponse(List<ValidationError> errors, int statusCode, String errorCode, String message) {
        super(statusCode);
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
