package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.token.TokenService;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.SuccessCode;
import com.lastone.core.dto.token.TokenLogoutDto;
import com.lastone.core.dto.token.TokenRefreshDto;
import com.lastone.core.security.jwt.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(@RequestBody @Validated TokenLogoutDto tokenLogoutDto) {
        tokenService.logout(tokenLogoutDto);
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.TOKEN_LOGOUT));
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponse> refreshToken(HttpServletRequest request, @RequestBody @Validated TokenRefreshDto tokenRefreshDto) {
        TokenResponse tokens = tokenService.refresh(tokenRefreshDto.getRefreshToken(), request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(SuccessCode.TOKEN_REFRESH, tokens));
    }
}
