package com.lastone.core.dto.token;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenLogoutDto {

    @NotBlank(message = "억세스 토큰 값이 비어있습니다.")
    private String accessToken;

    @NotBlank(message = "리프레시 토큰 값이 비어있습니다.")
    private String refreshToken;
}
