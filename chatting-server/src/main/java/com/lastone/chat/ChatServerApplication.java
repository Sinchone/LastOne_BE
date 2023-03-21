package com.lastone.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.lastone.core")
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.lastone.core"})
@SpringBootApplication(scanBasePackages = {"com.lastone.core", "com.lastone.chat"}, exclude = SecurityAutoConfiguration.class)
public class ChatServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }
}