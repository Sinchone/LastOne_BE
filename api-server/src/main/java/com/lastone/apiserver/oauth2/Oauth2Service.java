package com.lastone.apiserver.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lastone.core.dto.Oauth2LoginRequestDto;
import com.lastone.core.security.jwt.TokenResponse;

public interface Oauth2Service {

    TokenResponse createToken(Oauth2LoginRequestDto loginRequestDto, String requestURI) throws JsonProcessingException;
}
