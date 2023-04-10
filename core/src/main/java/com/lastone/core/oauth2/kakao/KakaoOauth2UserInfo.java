package com.lastone.core.oauth2.kakao;

import lombok.Getter;
import lombok.ToString;

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
            return "남성";
        }
        return "여성";
    }
}
