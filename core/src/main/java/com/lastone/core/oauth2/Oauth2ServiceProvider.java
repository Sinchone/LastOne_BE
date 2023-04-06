package com.lastone.core.oauth2;

import com.lastone.core.oauth2.kakao.KakaoOauth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Oauth2ServiceProvider {

    private final KakaoOauth2Service kakaoOauth2Service;

    public Oauth2Service getOauth2Service(String registerId) {
        if (registerId.equals("kakao")) {
            return kakaoOauth2Service;
        }
        return null;
    }
}
