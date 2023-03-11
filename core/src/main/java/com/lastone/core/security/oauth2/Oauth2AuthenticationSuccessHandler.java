package com.lastone.core.security.oauth2;

import com.lastone.core.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final RedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        log.info("oauth2SuccessHadnler 접근");

        CustomOauth2User user = (CustomOauth2User) authentication.getPrincipal();

        Map<String, String> tokens = JwtProvider.createToken(user.getName(), request.getRequestURI());

        response.addCookie(makeCookieForAccessToken(tokens.get("accessToken")));
        response.addCookie(makeCookieForRefreshToken(tokens.get("refreshToken")));

        redisTemplate.opsForValue()
                .set("refresh_token:" + user.getName(), tokens.get("refreshToken"), 60 * 30, TimeUnit.SECONDS);


        String targetUrl = "http://localhost:3000/oauth2/login";
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    private Cookie makeCookieForAccessToken(String token) {
        Cookie cookie = new Cookie("access_token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(10 * 60);
        return cookie;
    }

    private Cookie makeCookieForRefreshToken(String token) {
        Cookie cookie = new Cookie("refresh_token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 60);
        return cookie;
    }
}
