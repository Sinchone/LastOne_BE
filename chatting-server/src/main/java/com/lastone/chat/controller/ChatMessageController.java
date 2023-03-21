package com.lastone.chat.controller;

import com.lastone.core.domain.chat.ChatMessage;
import com.lastone.core.dto.message.ChatMessageReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

// 채팅을 보내는 행위를 함
@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    // 메세지를 보낼 때
    private final RabbitTemplate rabbitTemplate;
    @MessageMapping("/chat/{chatRoomId}")
    public void sendMessage(@DestinationVariable Long chatRoomId, ChatMessageReqDto chatMessage) {
        ChatMessage message = new ChatMessage(chatRoomId, chatMessage);
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, message);
    }
}
