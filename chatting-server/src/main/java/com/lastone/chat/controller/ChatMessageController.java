package com.lastone.chat.controller;

import com.lastone.core.dto.message.ChatMessageReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final RabbitTemplate template;
    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";

    @MessageMapping("chat.message.test.{chatRoomId}")
    public void testSendMessage(@DestinationVariable String chatRoomId, ChatMessageReqDto chatMessage) {
        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatMessage.getRoomId(), chatMessage);
    }
}