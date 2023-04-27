package com.lastone.apiserver.exception.partner;

import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PartnerControllerAdvice {
    @ExceptionHandler(PartnerException.class)
    public ResponseEntity<Object> globalExceptionHandler(PartnerException e) {
        return handleExceptionInternal(e);
    }

    private ResponseEntity<Object> handleExceptionInternal(PartnerException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(CommonResponse.fail(errorCode));
    }
}