package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.token.TokenService;
import com.lastone.core.dto.response.SuccessResponse;
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
    public ResponseEntity<Object> logout(@RequestBody @Validated TokenLogoutDto tokenLogoutDto) {
        tokenService.logout(tokenLogoutDto);
        return ResponseEntity.ok()
                .body(SuccessResponse.builder()
                        .message("로그아웃을 완료했습니다.")
                        .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(HttpServletRequest request, @RequestBody @Validated TokenRefreshDto tokenRefreshDto) {
        TokenResponse tokens = tokenService.refresh(tokenRefreshDto.getRefreshToken(), request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.builder()
                        .message("토큰 재발급을 완료하였습니다.")
                        .data(tokens)
                        .build());
    }
}
