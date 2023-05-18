package com.lastone.core.security.filter;

import com.lastone.core.domain.member.Member;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.core.security.principal.UserDetailsImpl;
import com.lastone.core.security.exception.AlreadyLogoutException;
import com.lastone.core.security.exception.AuthorizationHeaderException;
import com.lastone.core.security.exception.NotFoundMemberException;
import com.lastone.core.security.jwt.JwtProvider;
import com.lastone.core.security.jwt.TokenHeaderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final MemberRepository memberRepository;
    private final RedisTemplate redisTemplate;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (isPossibleToPassFilter(request.getHeader(AUTHORIZATION))) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken AuthorizeToken = getAuthorizeToken(request.getHeader(AUTHORIZATION));
        SecurityContextHolder.getContext().setAuthentication(AuthorizeToken);
        chain.doFilter(request, response);
    }

    private boolean isPossibleToPassFilter(String header) {
        return ObjectUtils.isEmpty(header);
    }

    private UsernamePasswordAuthenticationToken getAuthorizeToken(String header) {
        if (header.contains(TokenHeaderType.TEST_TOKEN.getTokenHeader())) {
            return createTestToken(header);
        }
        if (header.startsWith(TokenHeaderType.BEARER_TOKEN.getTokenHeader())) {
            return createBearerToken(header);
        }
        else {
            throw new AuthorizationHeaderException();
        }
    }

    private UsernamePasswordAuthenticationToken createBearerToken(String header) {
        String token = header.substring(TokenHeaderType.BEARER_TOKEN.getTokenHeader().length());
        String isLogout = (String) redisTemplate.opsForValue().get(token);
        if (StringUtils.hasText(isLogout)) {
            throw new AlreadyLogoutException();
        }
        String email = jwtProvider.verifyAccessToken(token).getSubject();
        UserDetails userdetails = UserDetailsImpl.convert(memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new));
        Collection<SimpleGrantedAuthority> authorities = createMemberAuthorities();

        return new UsernamePasswordAuthenticationToken(userdetails, null, authorities);
    }

    private UsernamePasswordAuthenticationToken createTestToken(String header) {
        Optional<Member> findMember = memberRepository.findByEmail(header);
        if (findMember.isEmpty()) {
            findMember = createTestMember(header);
        }
        UserDetails userDetails = UserDetailsImpl.convert(findMember.get());
        Collection<SimpleGrantedAuthority> authorities = createMemberAuthorities();

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    private Collection<SimpleGrantedAuthority> createMemberAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        return authorities;
    }

    private Optional<Member> createTestMember(String header) {
        String uuid = UUID.randomUUID().toString();
        Member member = memberRepository.save(Member.builder()
                .gender("남성")
                .nickname("test" + uuid)
                .email(header)
                .workoutDay("월,화,수")
                .workoutPurpose("다이어트")
                .workoutTime("15:30")
                .build());
        return Optional.of(member);
    }
}

