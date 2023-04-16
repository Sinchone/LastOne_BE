package com.lastone.core.security.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.dto.response.FailureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionHandler {

    private final ObjectMapper objectMapper;

    public void createJwtVerificationResponse(HttpServletResponse response, JWTVerificationException e) throws IOException {
        for (JwtVerificationErrorCode errorCode : JwtVerificationErrorCode.values()) {
            if(e.getClass().getSimpleName().equals(errorCode.getName())) {
                response.setStatus(errorCode.getStatus());
                FailureResponse failureResponse = new FailureResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
                String result = objectMapper.writeValueAsString(failureResponse);
                response.getWriter().write(result);
            }
        }
    }

    public void createSecurityErrorResponse(HttpServletResponse response, SecurityException e) throws IOException {
        ErrorCode errorCode = e.getErrorCode();
        response.setStatus(errorCode.getStatus());
        FailureResponse failureResponse = new FailureResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
        String result = objectMapper.writeValueAsString(failureResponse);
        response.getWriter().write(result);
    }
}