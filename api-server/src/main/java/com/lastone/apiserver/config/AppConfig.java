package com.lastone.apiserver.config;

import com.lastone.apiserver.oauth2.kakao.KakaoConfig;
import com.lastone.core.config.SecurityConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import(SecurityConfig.class)
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConfigurationProperties(prefix = "oauth2.kakao")
    public KakaoConfig kakaoConfig() {
        return new KakaoConfig();
    }
}
