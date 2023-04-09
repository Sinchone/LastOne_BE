package com.lastone.apiserver.service.token;

import com.lastone.core.dto.token.TokenLogoutDto;
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
        TokenInfo tokenInfo = jwtProvider.verifyToken(refreshToken);
        logoutRefreshToken(refreshToken);
        return jwtProvider.createToken(tokenInfo.getSubject(), requestUri);
    }

    private void logoutRefreshToken(String refreshToken) {
        String email = jwtProvider.verifyToken(refreshToken).getSubject();
        if (redisTemplate.opsForValue().get("refresh_token:" + email) != null) {
            redisTemplate.delete("refresh_token:" + email);
        }
    }

    private void logoutAccessToken(String accessToken) {
        TokenInfo tokenInfo = jwtProvider.verifyToken(accessToken);
        Long remainingTime = calRemainingTime(tokenInfo.getExpiredAt());
        redisTemplate.opsForValue().set(accessToken, "logout", remainingTime, TimeUnit.MILLISECONDS);
    }

    private Long calRemainingTime(Date expireDate) {
        return expireDate.getTime() - new Date().getTime();
    }
}
