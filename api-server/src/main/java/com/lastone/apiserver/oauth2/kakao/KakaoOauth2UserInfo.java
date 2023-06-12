package com.lastone.apiserver.oauth2.kakao;

import com.amazonaws.util.StringUtils;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@ToString
@Slf4j
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

    public String getGender() {
        String gender = (String) kakaoAccount.get("gender");
        if (StringUtils.isNullOrEmpty(gender)) {
            return null;
        }
        if (gender.equals("male")) {
            return "남성";
        }
        return "여성";
    }
}
