package com.lastone.chat.service;

import com.lastone.chat.persistence.ChatMessage;
import com.lastone.chat.repository.ChatMessageRepository;
import com.lastone.core.dto.message.ChatMessageReqDto;
import com.lastone.chat.dto.ChatMessageResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository messageRepository;
    @Override
    public ChatMessageResDto createMessage(String roomId, ChatMessageReqDto messageReqDto) {
        ChatMessage chatMessage = new ChatMessage(roomId, messageReqDto);
        return new ChatMessageResDto(messageRepository.save(chatMessage));
    }
}