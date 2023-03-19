package com.lastone.core.security.oauth2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@ToString
public class KakaoOauth2UserInfo {

    protected final Map<String, Object> attributes;

    private final Map<String, Object> kakaoAccount;

    public KakaoOauth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    }

    public String getEmail() {
        return (String) kakaoAccount.get("email");
    }

    public String getNickname() {
        Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");
        return profile.get("nickname");
    }

    public String getGender() {
        String gender = (String) kakaoAccount.get("gender");
        if (gender.equals("male")) {
            return "남";
        }
        return "여";
    }
}
