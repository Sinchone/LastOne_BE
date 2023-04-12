package com.lastone.core.oauth2;

import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.oauth2.kakao.KakaoOauth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Oauth2ServiceProvider {

    private final KakaoOauth2Service kakaoOauth2Service;

    public Oauth2Service getOauth2Service(String registerId) {
        if (!registerId.equals("kakao")) {
            throw new Oauth2RegisterIdException(ErrorCode.OAUTH2_REGISTER_NOT_FOUND);
        }
        return kakaoOauth2Service;
    }
}
