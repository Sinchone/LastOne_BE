package com.lastone.apiserver.oauth2.kakao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@ToString
public class KakaoConfig {

    private String clientId;
    private String clientSecret;
    private String grantType;
    private String redirectUrl;
    private String authorizationUrl;
    private String tokenUrl;
    private String userInfoUri;

    public MultiValueMap<String, String> getTokenParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authCode);
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUrl);
        params.add("client_secret", clientSecret);
        return params;
    }
}
