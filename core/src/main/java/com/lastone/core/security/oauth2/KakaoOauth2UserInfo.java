package com.lastone.core.security.oauth2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class KakaoOauth2UserInfo {

    protected Map<String, Object> attributes;

    public KakaoOauth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    public String getNickname() {
        return "닉네임";

    }
}
