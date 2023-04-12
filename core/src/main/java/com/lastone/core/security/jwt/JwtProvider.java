package com.lastone.core.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    @PostConstruct
    private void setAlgorithm() {
        this.algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenResponse createToken(String email, String requestUri) {


        String accessToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenDuration))
                .withIssuer(requestUri)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenDuration))
                .withIssuer(requestUri)
                .sign(algorithm);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenInfo verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return TokenInfo.builder()
                .subject(decodedJWT.getSubject())
                .expiredAt(decodedJWT.getExpiresAt())
                .build();
    }
}
