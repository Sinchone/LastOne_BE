package com.lastone.apiserver.exception.token;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.security.exception.SecurityException;
import com.lastone.core.security.jwt.JwtExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class TokenControllerAdvice extends ResponseEntityExceptionHandler {
    private final JwtExceptionHandler exceptionHandler;
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Object> globalExceptionHandler(SecurityException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<Object> verificationExceptionHandler(JWTVerificationException e) {
        return handleExceptionInternal(exceptionHandler.findJwtVerificationErrorCode(e));
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(CommonResponse.fail(errorCode));
    }
}