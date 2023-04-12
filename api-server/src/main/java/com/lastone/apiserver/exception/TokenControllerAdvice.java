package com.lastone.apiserver.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lastone.core.dto.response.FailureResponse;
import com.lastone.core.exception.ErrorCode;
import com.lastone.core.security.exception.JwtVerificationErrorCode;
import com.lastone.core.security.exception.SecurityException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TokenControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Object> globalExceptionHandler(SecurityException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<Object> verificationExceptionHandler(JWTVerificationException e) {
        JwtVerificationErrorCode errorCode = JwtVerificationErrorCode.TOKEN_EXPIRED_EXCEPTION;
        for (JwtVerificationErrorCode code: JwtVerificationErrorCode.values()) {
            if (e.getClass().getSimpleName().equals(code.getName())) {
                errorCode = code;
            }
        }
        return handleExceptionInternal(errorCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ErrorCode.INVALID_INPUT_VALUE);
    }


    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal (JwtVerificationErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        List<FailureResponse.ValidationError> ve = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FailureResponse.ValidationError::of)
                .collect(Collectors.toList());
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode, errorCode.getMessage(), ve));
    }

    private FailureResponse buildResponse(ErrorCode errorCode) {
        return new FailureResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
    }

    private FailureResponse buildResponse(JwtVerificationErrorCode errorCode) {
        return new FailureResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
    }

    private FailureResponse buildResponse(ErrorCode errorCode, String message, List<FailureResponse.ValidationError> ve) {
        return new FailureResponse(ve, errorCode.getStatus(), errorCode.getCode(), message);
    }
}