package com.lastone.apiserver.exception;

import com.lastone.core.dto.FailureResponse;
import com.lastone.core.dto.FailureResponse.ValidationError;
import com.lastone.core.exception.ErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MyPageControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MyPageException.class)
    public ResponseEntity<Object> globalExceptionHandler(MyPageException e) {
        return handleExceptionInternal(e);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ErrorCode.MYPAGE_INPUT_FAILURE);
    }
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleBindException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ErrorCode.INVALID_INPUT_VALUE);
    }

    private ResponseEntity<Object> handleExceptionInternal(MyPageException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        List<ValidationError> ve = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::of)
                .collect(Collectors.toList());
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode, errorCode.getMessage(), ve));
    }

    private FailureResponse buildResponse(ErrorCode errorCode) {
        return new FailureResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
    }

    private FailureResponse buildResponse(ErrorCode errorCode, String message) {
        return new FailureResponse(null, errorCode.getStatus(), errorCode.getCode(), message);
    }

    private FailureResponse buildResponse(ErrorCode errorCode, String message, List<ValidationError> ve) {
        return new FailureResponse(ve, errorCode.getStatus(), errorCode.getCode(), message);
    }

}
