package com.lastone.core.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lastone.core.security.jwt.TokenResponse;

public interface Oauth2Service {

    TokenResponse createToken(String code, String requestURI) throws JsonProcessingException;

}
