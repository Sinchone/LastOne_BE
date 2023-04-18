package com.lastone.chat.controller;

import com.lastone.chat.dto.ChatMessageResDto;
import com.lastone.chat.service.ChatMessageService;
import com.lastone.core.dto.message.ChatMessageReqDto;
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
    /**
     * Todo - API 연동이 끝나면 통합테스트를 위해 로그인 로직 붙이기
     * @AuthenticationPrincipal UserDetailsImpl userDetails,
     * if(userDetails.getId() != chatMessage.getSenderId()
     *         || userDetails.getId() != chatMessage.getReceiverId()) {
     *             throw new ChatException(ErrorCode.UNAUTHORIZED);
     *         }
     */
    @MessageMapping("chat/message/{chatRoomId}")
    public void sendMessage(@DestinationVariable String chatRoomId, ChatMessageReqDto chatMessage) {
        ChatMessageResDto message = messageService.createMessage(chatRoomId, chatMessage);
        log.info("chatMessage  : " + chatMessage.getContent());
        template.convertAndSend("/sub/chat/room/" + chatRoomId, message);
    }
}