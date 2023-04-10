package com.lastone.core.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lastone.core.security.exception.JwtExceptionHandler;
import com.lastone.core.security.exception.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private static final String JSON = "application/json";
    private static final String ENCODING = "UTF-8";
    private final JwtExceptionHandler exceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityException e) {
            setResponseHeader(response);
            exceptionHandler.createSecurityErrorResponse(response, e);
        } catch (JWTVerificationException e) {
            setResponseHeader(response);
            exceptionHandler.createJwtVerificationResponse(response, e);
        }
    }

    private void setResponseHeader(HttpServletResponse response) {
        response.setContentType(JSON);
        response.setCharacterEncoding(ENCODING);
    }
}