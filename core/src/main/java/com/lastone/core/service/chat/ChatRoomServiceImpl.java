package com.lastone.core.service.chat;

import com.lastone.core.domain.chat.ChatRoom;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import com.lastone.core.persistence.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    @Override
    public ChatRoom create(ChatRoomCreateReqDto createReqDto) {
        ChatRoom savedChatRoom = chatRoomRepository.save(new ChatRoom(createReqDto));

        return savedChatRoom;
    }
}
