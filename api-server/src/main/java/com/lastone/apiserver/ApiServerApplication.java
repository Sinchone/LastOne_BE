package com.lastone.apiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.lastone.core")
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.lastone.core"})
@SpringBootApplication(scanBasePackages = {"com.lastone.core", "com.lastone.apiserver"} )
public class ApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServerApplication.class, args);
    }
}
