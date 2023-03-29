package com.lastone.core.service.chatmessage;

import com.lastone.core.domain.chat.ChatMessage;
import com.lastone.core.dto.message.ChatMessageReqDto;
import com.lastone.core.dto.message.ChatMessageResDto;
import com.lastone.core.persistence.chatmessage.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessageResDto createMessage(Long roomId, ChatMessageReqDto messageReqDto) {
        ChatMessage chatMessage = chatMessageRepository.save(new ChatMessage(roomId, messageReqDto));
        return new ChatMessageResDto(chatMessage);
    }
}
