package com.lastone.core.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class JwtProvider {

    private static final int ACCESS_TOKEN_TIME = 10 * 60 * 1000;
    private static final int REFRESH_TOKEN_TIME = 30 * 60 * 1000;
    private static final String secretKey = "test";
    private static final Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());

    public static Map<String, String> createToken(String email, String requestUri) {

        String accessToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_TIME))
                .withIssuer(requestUri)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_TIME))
                .withIssuer(requestUri)
                .sign(algorithm);

        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    public static String verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }
}
