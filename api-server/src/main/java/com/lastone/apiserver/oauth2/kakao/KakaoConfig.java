package com.lastone.apiserver.oauth2.kakao;

import com.lastone.core.dto.Oauth2LoginRequestDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
@ToString
public class KakaoConfig {

    private String clientId;

    private String clientSecret;

    private String grantType;

    private String localRedirectUrl;

    private String prodRedirectUrl;

    private String authorizationUrl;

    private String tokenUrl;

    private String userInfoUri;

    public MultiValueMap<String, String> getTokenParams(Oauth2LoginRequestDto loginRequestDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", loginRequestDto.getCode());
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("redirect_uri", pickRedirectUrlByEnv(loginRequestDto.getEnv()));
        params.add("client_secret", clientSecret);
        return params;
    }

    private String pickRedirectUrlByEnv(String env) {
        if (ObjectUtils.isEmpty(env)) {
            return prodRedirectUrl;
        }
        return localRedirectUrl;
    }
}
