package com.lastone.core.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private static final String ENCODING_TYPE = "UTF-8";
    private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;
    private static final int HTTP_STATUS = HttpStatus.UNAUTHORIZED.value();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        setResponse(response);
        response.getWriter()
                .write(objectMapper.writeValueAsString(CommonResponse.fail(ErrorCode.UN_AUTHENTICATION_MEMBER)));
    }

    private void setResponse(HttpServletResponse response) {
        response.setCharacterEncoding(ENCODING_TYPE);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HTTP_STATUS);
    }
}