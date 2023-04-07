package com.lastone.core.oauth2;

import com.lastone.core.dto.response.FailureResponse;
import com.lastone.core.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class Oauth2ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Oauth2Exception.class)
    public ResponseEntity<Object> globalExceptionHandler(Oauth2Exception e) {
        return handleExceptionInternal(e);
    }

    private ResponseEntity<Object> handleExceptionInternal(Oauth2Exception e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode));
    }

    private FailureResponse buildResponse(ErrorCode errorCode) {
        return new FailureResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
    }
}
