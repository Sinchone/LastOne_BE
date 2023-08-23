package com.lastone.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.config.StompBrokerRelayRegistration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${lastOne.front.localURL}")
    private String localEndURL;
    @Value("${lastOne.front.prodURL}")
    private String prodEndURL;
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${lastOne.rabbitmq.username}")
    private String clientUsername;
    @Value("${lastOne.rabbitmq.password}")
    private String clientPassword;
    @Value("${spring.rabbitmq.username}")
    private String systemUsername;
    @Value("${spring.rabbitmq.password}")
    private String systemPassword;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat/stomp")
                .setAllowedOrigins(localEndURL, prodEndURL, "http://localhost:8082")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setPathMatcher(new AntPathMatcher("."));
        registry.setApplicationDestinationPrefixes("/pub");
        StompBrokerRelayRegistration relayRegistration = registry.enableStompBrokerRelay("/topic")
                .setRelayHost(host)
                .setRelayPort(61613)
                .setClientLogin(clientUsername)
                .setClientPasscode(clientPassword)
                .setSystemLogin(systemUsername)
                .setSystemPasscode(systemPassword);
        relayRegistration.setSystemHeartbeatSendInterval(20000);
        relayRegistration.setSystemHeartbeatReceiveInterval(20000);
    }
}
