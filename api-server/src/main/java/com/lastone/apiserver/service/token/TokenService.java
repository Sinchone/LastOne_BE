package com.lastone.apiserver.service.token;

import com.lastone.core.dto.token.TokenLogoutDto;
import com.lastone.core.security.jwt.TokenResponse;

public interface TokenService {

    void logout(TokenLogoutDto tokenLogoutDto);

    TokenResponse refresh(String refreshToken, String requestUri);
}