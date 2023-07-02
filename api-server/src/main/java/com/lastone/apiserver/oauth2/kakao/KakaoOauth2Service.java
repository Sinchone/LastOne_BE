package com.lastone.apiserver.oauth2.kakao;

import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastone.apiserver.oauth2.Oauth2Service;
import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.Oauth2LoginRequestDto;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.core.security.jwt.TokenResponse;
import com.lastone.core.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOauth2Service implements Oauth2Service {

    private final MemberRepository memberRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final KakaoConfig kakaoConfig;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;


    @Transactional(rollbackFor = Exception.class)
    public TokenResponse createToken(Oauth2LoginRequestDto loginRequestDto, String requestURI) throws JsonProcessingException {
        String token = getToken(loginRequestDto);
        KakaoOauth2UserInfo profile = getProfile(token);
        Optional<Member> member = memberRepository.findByEmail(profile.getEmail());
        boolean isFirstSignUp = false;
        if (member.isEmpty()) {
            Member newMember = Member.builder()
                    .email(profile.getEmail())
                    .build();
            if (!StringUtils.isNullOrEmpty(profile.getGender())) {
                newMember.initGender(profile.getGender());
            }
            Member saveMember = memberRepository.save(newMember);
            String nickname = "#" + saveMember.getId();
            saveMember.initNickname(nickname);
            isFirstSignUp = true;
        }
        TokenResponse tokens = jwtProvider.createToken(profile.getEmail(), requestURI);
        if (isFirstSignUp) {
            tokens.setFirstSignUp();
        }
        redisTemplate.opsForValue()
                .set("refresh_token:" + profile.getEmail(), tokens.getRefreshToken(), jwtProvider.getRefreshTokenDuration(), TimeUnit.MILLISECONDS);
        return tokens;
    }

    private String getToken(Oauth2LoginRequestDto loginRequestDto) throws JsonProcessingException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(kakaoConfig.getTokenParams(loginRequestDto), header);

        ResponseEntity<String> response = restTemplate.postForEntity(kakaoConfig.getTokenUrl(), request, String.class);
        KakaoToken kakaoToken = objectMapper.readValue(response.getBody(), KakaoToken.class);

        return kakaoToken.getAccess_token();
    }

    private KakaoOauth2UserInfo getProfile(String token) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(kakaoConfig.getUserInfoUri(), request, String.class);

        if(response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("유저 정보 업로드 실패");
        }
        Map<String,Object> kakaoOAuthResponseDto = objectMapper.readValue(response.getBody(), Map.class);
        return new KakaoOauth2UserInfo(kakaoOAuthResponseDto);
    }
}
