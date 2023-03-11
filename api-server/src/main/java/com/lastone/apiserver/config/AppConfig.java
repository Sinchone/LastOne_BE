package com.lastone.apiserver.config;

import com.lastone.core.config.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SecurityConfig.class)
public class AppConfig {
}
