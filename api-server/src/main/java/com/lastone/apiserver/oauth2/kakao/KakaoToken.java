package com.lastone.apiserver.oauth2.kakao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoToken {

    private String id_token;
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String scope;
    private String refresh_token_expires_in;

}
