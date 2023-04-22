package com.lastone.apiserver.exception.application;

import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> globalExceptionHandler(ApplicationException e) {
        return handleExceptionInternal(e);
    }

    private ResponseEntity<Object> handleExceptionInternal(ApplicationException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(CommonResponse.fail(errorCode));
    }
}
