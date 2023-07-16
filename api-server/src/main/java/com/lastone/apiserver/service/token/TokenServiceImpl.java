package com.lastone.apiserver.service.token;

import com.lastone.core.dto.token.TokenLogoutDto;
import com.lastone.core.security.exception.NotFoundRefreshTokenException;
import com.lastone.core.security.jwt.JwtProvider;
import com.lastone.core.security.jwt.TokenInfo;
import com.lastone.core.security.jwt.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void logout(TokenLogoutDto tokenLogoutDto) {
        logoutAccessToken(tokenLogoutDto.getAccessToken());
        logoutRefreshToken(tokenLogoutDto.getRefreshToken());
    }

    @Override
    public TokenResponse refresh(String refreshToken, String requestUri) {
        TokenInfo tokenInfo = jwtProvider.verifyRefreshToken(refreshToken);
        TokenResponse newToken = jwtProvider.createToken(tokenInfo.getSubject(), requestUri);
        logoutRefreshToken(tokenInfo.getSubject());
        saveNewRefreshToken(newToken.getRefreshToken(), tokenInfo.getSubject());
        return newToken;
    }

    private void saveNewRefreshToken(String refreshToken, String email) {
        redisTemplate.opsForValue()
                .set("refresh_token:" + email, refreshToken, jwtProvider.getRefreshTokenDuration(), TimeUnit.MILLISECONDS);
    }

    private void logoutRefreshToken(String email) {
        if (redisTemplate.opsForValue().get("refresh_token:" + email) == null) {
            throw new NotFoundRefreshTokenException();
        }
        redisTemplate.delete("refresh_token:" + email);

    }

    private void logoutAccessToken(String accessToken) {
        TokenInfo tokenInfo = jwtProvider.verifyAccessToken(accessToken);
        Long remainingTime = calRemainingTime(tokenInfo.getExpiredAt());
        redisTemplate.opsForValue().set(accessToken, "logout", remainingTime, TimeUnit.MILLISECONDS);
    }

    private Long calRemainingTime(Date expireDate) {
        return expireDate.getTime() - new Date().getTime();
    }
}
