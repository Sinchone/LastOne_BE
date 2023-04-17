package com.lastone.core.security.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.security.exception.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionHandler {

    private static final String ALGORITHM_MISMATCH_EXCEPTION = "AlgorithmMismatchException";
    private static final String INVALID_CLAIM_EXCEPTION = "InvalidClaimException";
    private static final String JWT_DECODE_EXCEPTION = "JWTDecodeException";
    private static final String SIGNATURE_VERIFICATION_EXCEPTION = "SignatureVerificationException";
    private static final String TOKEN_EXPIRED_EXCEPTION = "TokenExpiredException";
    private final ObjectMapper objectMapper;

    public void createJwtVerificationResponse(HttpServletResponse response, JWTVerificationException exception) throws IOException {
        ErrorCode errorCode = findJwtVerificationErrorCode(exception);
        response.setStatus(errorCode.getStatus());
        response.getWriter().write(objectMapper.writeValueAsString(CommonResponse.fail(errorCode)));
    }

    public void createSecurityErrorResponse(HttpServletResponse response, SecurityException e) throws IOException {
        ErrorCode errorCode = e.getErrorCode();
        response.setStatus(errorCode.getStatus());
        response.getWriter()
                .write(objectMapper.writeValueAsString(CommonResponse.fail(e.getErrorCode())));
    }

    public ErrorCode findJwtVerificationErrorCode(JWTVerificationException e) {
        ErrorCode errorCode = ErrorCode.JWT_DECODING_DEFAULT_EXCEPTION;
        switch (e.getClass().getSimpleName()) {
            case ALGORITHM_MISMATCH_EXCEPTION:
                errorCode = ErrorCode.ALGORITHM_MISMATCH_EXCEPTION;
                break;
            case INVALID_CLAIM_EXCEPTION:
                errorCode = ErrorCode.INVALID_CLAIM_EXCEPTION;
                break;
            case JWT_DECODE_EXCEPTION:
                errorCode = ErrorCode.JWT_DECODE_EXCEPTION;
                break;
            case SIGNATURE_VERIFICATION_EXCEPTION:
                errorCode = ErrorCode.SIGNATURE_VERIFICATION_EXCEPTION;
                break;
            case TOKEN_EXPIRED_EXCEPTION:
                errorCode = ErrorCode.TOKEN_EXPIRED_EXCEPTION;
                break;
        }
        return errorCode;
    }
}