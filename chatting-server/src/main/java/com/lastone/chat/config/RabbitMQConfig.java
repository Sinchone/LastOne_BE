package com.lastone.chat.config;

import com.lastone.core.domain.chat.ChatMessage;
import com.lastone.core.dto.message.ChatMessageReqDto;
import com.sun.xml.bind.v2.runtime.unmarshaller.Receiver;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    private final String host = "localhost";
    private String username = "guest";
    private String password = "guest";
    private static final String CHAT_QUEUE_NAME = "chat.queue";
    private static final String CHAT_EXCHAGNE_NAME = "chat.exchange";
    private static final String ROUTING_KEY = "room.*";

    @Bean
    Queue queue() {
        return new Queue(CHAT_QUEUE_NAME, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(CHAT_EXCHAGNE_NAME);
    }

    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ROUTING_KEY);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                  MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(CHAT_QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return  container;
    }
    @Bean
    public MessageListenerAdapter listenerAdapter(RabbitMQReceiver receiver, MessageConverter messageConverter) {
        return new MessageListenerAdapter(receiver, messageConverter);
    }
    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Component
    @RequiredArgsConstructor
    public class RabbitMQReceiver {
        private final SimpMessagingTemplate messagingTemplate;

        @RabbitListener(queues = CHAT_QUEUE_NAME)
        public void receiveMessage(Message messageReq) {
            ChatMessage message = (ChatMessage) messagingTemplate
                                    .getMessageConverter()
                                    .fromMessage(messageReq, ChatMessage.class);
            messagingTemplate.convertAndSend("/exchange/messages", message);
        }
    }

}
