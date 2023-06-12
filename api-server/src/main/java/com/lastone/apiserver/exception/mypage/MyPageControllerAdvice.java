package com.lastone.apiserver.exception.mypage;

import com.lastone.apiserver.exception.global.ImgTypeNotSupportedException;
import com.lastone.core.common.exception.LastOneException;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MyPageControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MyPageException.class)
    public ResponseEntity<Object> globalExceptionHandler(MyPageException e) {
        return handleExceptionInternal(e);
    }

    @ExceptionHandler(ImgTypeNotSupportedException.class)
    public ResponseEntity<Object> globalExceptionHandler(ImgTypeNotSupportedException e) {
        return handleExceptionInternal(e);
    }

    private ResponseEntity<Object> handleExceptionInternal(LastOneException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(CommonResponse.fail(errorCode));
    }
}
