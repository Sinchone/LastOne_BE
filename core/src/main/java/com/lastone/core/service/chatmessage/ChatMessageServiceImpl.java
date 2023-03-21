package com.lastone.core.service.chatmessage;

import com.lastone.core.domain.chat.ChatMessage;
import com.lastone.core.domain.chat.ChatRoom;
import com.lastone.core.dto.chatroom.ChatRoomResDto;
import com.lastone.core.dto.message.ChatMessageReqDto;
import com.lastone.core.dto.message.ChatMessageResDto;
import com.lastone.core.persistence.chat.ChatRoomRepository;
import com.lastone.core.persistence.chatmessage.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    @Override
    public ChatRoom getChatRoomMessage(Long id) {
        if(id == null || id == 0) {

        }
        return null;
    }

    @Override
    public ChatMessageResDto createMessage(Long chatRoomId, ChatMessageReqDto messageReqDto) {
        ChatMessage chatMessage = chatMessageRepository.save(new ChatMessage(chatRoomId, messageReqDto));
        return new ChatMessageResDto(chatMessage);
    }

}
