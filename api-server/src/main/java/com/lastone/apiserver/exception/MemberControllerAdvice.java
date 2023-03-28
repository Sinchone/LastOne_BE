package com.lastone.apiserver.exception;

import com.lastone.apiserver.member.MemberController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(BindingResult bindingResult) {
        Map<String, Object> result = new HashMap<>();
        ErrorResponse errors = ErrorResponse.of(bindingResult);
        result.put("message", "입력값이 잘못되었습니다.");
        result.put("errors", errors.getErrors());
        return ResponseEntity.badRequest().body(result);
    }
}
