package com.lastone.core.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

public class JwtProvider {

    @Value("${token.time.access}")
    private static long accessTokenDuration;

    @Value("${token.time.refresh}")
    private static long refreshTokenDuration;

    private static final String secretKey = "test";
    private static final Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());

    public static TokenResponse createToken(String email, String requestUri) {

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


        return new TokenResponse(accessToken, refreshToken);
    }

    public static Map<String, Object> verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("expireDate", decodedJWT.getExpiresAt());
        tokenInfo.put("subject", decodedJWT.getSubject());
        return tokenInfo;
    }


}
