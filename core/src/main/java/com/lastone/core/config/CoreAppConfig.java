package com.lastone.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastone.core.oauth2.kakao.KakaoConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreAppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConfigurationProperties(prefix = "oauth2.kakao")
    public KakaoConfig kakaoConfig() {
        return new KakaoConfig();
    }
}
