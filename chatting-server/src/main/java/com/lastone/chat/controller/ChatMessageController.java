package com.lastone.chat.controller;

import com.lastone.core.dto.message.ChatMessageReqDto;
import com.lastone.core.dto.message.ChatMessageResDto;
import com.lastone.core.service.chatmessage.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessagingTemplate template;
    private final ChatMessageService messageService;

    @MessageMapping("chat/message/{chatRoomId}")
    public void sendMessage(@DestinationVariable Long chatRoomId, ChatMessageReqDto chatMessage) {
        ChatMessageResDto message = messageService.createMessage(chatRoomId, chatMessage);
        log.info("chatMessage  : " + chatMessage.getContent());
        template.convertAndSend("/sub/chat/room/" + chatRoomId, message);
    }
}
