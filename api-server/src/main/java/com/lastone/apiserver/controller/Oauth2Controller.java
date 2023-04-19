package com.lastone.apiserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lastone.apiserver.exception.oauth2.Oauth2CodeNotFounException;
import com.lastone.apiserver.oauth2.Oauth2Service;
import com.lastone.apiserver.oauth2.Oauth2ServiceProvider;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.SuccessCode;
import com.lastone.core.security.jwt.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/oauth2/login")
@RequiredArgsConstructor
public class Oauth2Controller {

    private static final String CODE = "code";

    private final Oauth2ServiceProvider oauth2ServiceProvider;

    @PostMapping("/{registerId}")
    public ResponseEntity<Object> getToken(HttpServletRequest request, @PathVariable("registerId") String registerId, @RequestBody Map<String,String> authCode) throws JsonProcessingException {
        String code = authCode.get(CODE);
        if (ObjectUtils.isEmpty(code)) {
            throw new Oauth2CodeNotFounException();
        }
        Oauth2Service oauth2Service = oauth2ServiceProvider.getOauth2Service(registerId);
        TokenResponse tokens = oauth2Service.createToken(authCode.get(CODE), request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(tokens, SuccessCode.OAUTH2_LOGIN.getMessage()));
    }
}
