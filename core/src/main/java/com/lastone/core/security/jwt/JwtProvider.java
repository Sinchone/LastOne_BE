package com.lastone.core.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.security.exception.AccessTokenExpiredException;
import com.lastone.core.security.exception.RefreshTokenExpiredException;
import com.lastone.core.security.exception.TokenTypeNotEqualException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import java.util.*;

@Component
@Getter
public class JwtProvider {

    @Value("${token.time.access}")
    private long accessTokenDuration;
    @Value("${token.time.refresh}")
    private long refreshTokenDuration;
    @Value("${token.secretkey}")
    private String secretKey;
    private Algorithm algorithm;

    private JWTVerifier verifier;

    private DecodedJWT decodedJWT;

    @PostConstruct
    private void setAlgorithm() {
        this.algorithm = Algorithm.HMAC256(secretKey.getBytes());
        this.verifier = JWT.require(algorithm).build();
    }

    public TokenResponse createToken(String email, String requestUri) {


        String accessToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenDuration))
                .withIssuer(requestUri)
                .withClaim("type", "accessToken")
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenDuration))
                .withIssuer(requestUri)
                .withClaim("type", "refreshToken")
                .sign(algorithm);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isFirstSignUp(false)
                .build();
    }

    public TokenInfo verifyAccessToken(String accessToken) {
        try {
            decodedJWT = verifier.verify(accessToken);
        } catch (TokenExpiredException e) {
            throw new AccessTokenExpiredException();
        }
        String tokenType = decodedJWT.getClaim("type").asString();
        if (!tokenType.equals(TokenType.ACCESS_TOKEN.getTokenType())) {
            throw new TokenTypeNotEqualException(ErrorCode.ACCESS_TOKEN_TYPE_IS_NEEDED);
        }
        return TokenInfo.builder()
                .subject(decodedJWT.getSubject())
                .expiredAt(decodedJWT.getExpiresAt())
                .build();
    }

    public TokenInfo verifyRefreshToken(String refreshToken) {
        try {
            decodedJWT = verifier.verify(refreshToken);
        } catch (TokenExpiredException e) {
            throw new RefreshTokenExpiredException();
        }
        String tokenType = decodedJWT.getClaim("type").asString();
        if (!tokenType.equals(TokenType.REFRESH_TOKEN.getTokenType())) {
            throw new TokenTypeNotEqualException(ErrorCode.REFRESH_TOKEN_TYPE_IS_NEEDED);
        }
        return TokenInfo.builder()
                .subject(decodedJWT.getSubject())
                .expiredAt(decodedJWT.getExpiresAt())
                .build();
    }
}
