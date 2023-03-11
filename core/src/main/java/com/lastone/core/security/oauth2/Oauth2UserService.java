package com.lastone.core.security.oauth2;

import antlr.StringUtils;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        log.info("Oauth2UserService 접근");

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        ClientRegistration clientRegistration = oAuth2UserRequest.getClientRegistration();
        KakaoOauth2UserInfo kakaoOauth2UserInfo = new KakaoOauth2UserInfo(oAuth2User.getAttributes());

        String email = kakaoOauth2UserInfo.getEmail();
        String nickname = kakaoOauth2UserInfo.getNickname();

        if (email == null) {
            throw new IllegalArgumentException("카카오 email은 필수 입력 값입니다.");
        }

        Member findMember = memberRepository.findByEmail(email);
        if (findMember == null) {
            Member member = Member.builder()
                    .email(email)
                    .nickname(nickname)
                    .build();
            memberRepository.save(member);
        }

        return new CustomOauth2User(email, oAuth2User.getAttributes());
    }
}
