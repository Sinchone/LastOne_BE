package com.lastone.core.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lastone.core.domain.member.MemberRepository;
import com.lastone.core.security.UserDetailsImpl;
import com.lastone.core.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;

    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("AuthorizatonFilter 접근, request uri = {}", request.getRequestURI());

        if (request.getServletPath().equals("/token/refresh") || request.getServletPath().equals("/token/logout")) {
            chain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            ;
            chain.doFilter(request, response);
            return;
        }

        // redis에 해당 access 토큰 정보가 있으면 권한 처리 x
        String token = authorizationHeader.substring("Bearer ".length());
        String isLogout = (String) redisTemplate.opsForValue().get(token);
        if (!ObjectUtils.isEmpty(isLogout)) {
            throw new IllegalArgumentException("만료된 토큰입니다.");
        }

        String email = JwtProvider.verifyToken(token);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        UserDetails userDetails = UserDetailsImpl.convert(memberRepository.findByEmail(email));

        UsernamePasswordAuthenticationToken AuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(AuthenticationToken);
        chain.doFilter(request, response);
    }
}
