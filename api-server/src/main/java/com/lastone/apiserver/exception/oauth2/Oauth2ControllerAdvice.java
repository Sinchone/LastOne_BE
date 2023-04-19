package com.lastone.apiserver.exception.oauth2;

import com.lastone.apiserver.controller.Oauth2Controller;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;


@Slf4j
@RestControllerAdvice(assignableTypes = Oauth2Controller.class)
@RequiredArgsConstructor
public class Oauth2ControllerAdvice extends ResponseEntityExceptionHandler {
    private static final String ALREADY_USED_CODE = "KOE320";

    @ExceptionHandler(Oauth2Exception.class)
    public ResponseEntity<CommonResponse> globalExceptionHandler(Oauth2Exception e) {
        return handleExceptionInternal(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> globalExceptionHandler(Exception e) {
        log.info(e.getMessage());
        if(Objects.requireNonNull(e.getMessage()).contains(ALREADY_USED_CODE)) {
            return handleExceptionInternal(new AlreadyUsedCodeException());
        }
        return handleExceptionInternal(new Oauth2DefaultException());
    }

    private ResponseEntity<CommonResponse> handleExceptionInternal(Oauth2Exception e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(CommonResponse.fail(errorCode));
    }
}
