package com.lastone.chat.controller;

import com.lastone.chat.dto.ChatMessageResDto;
import com.lastone.chat.persistence.ChatMessage;
import com.lastone.chat.service.ChatMessageService;
import com.lastone.core.dto.message.ChatMessageReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestChatMessageController {
    private final ChatMessageService messageService;

    @PostMapping("/message/send/{roomId}")
    public ChatMessageResDto createMessage(@PathVariable Long roomId, @RequestBody ChatMessageReqDto messageReqDto) {
        return messageService.createMessage(roomId, messageReqDto);
    }
}
