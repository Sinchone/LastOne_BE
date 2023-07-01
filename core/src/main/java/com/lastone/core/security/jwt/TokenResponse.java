package com.lastone.core.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

    private Boolean isFirstSignUp;

    public void setFirstSignUp() {
        this.isFirstSignUp = true;
    }
}
