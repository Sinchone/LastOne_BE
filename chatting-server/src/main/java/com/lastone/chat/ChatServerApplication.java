package com.lastone.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EntityScan("com.lastone.core")
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.lastone.core", "com.lastone.chat"})
@EnableMongoAuditing
@EnableMongoRepositories
@SpringBootApplication(scanBasePackages = {"com.lastone.core","com.lastone.chat"} )
public class ChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }
}