package com.lastone.core.persistence.chatmessage;

import com.lastone.core.domain.chat.ChatRoom;

import java.util.Optional;

public interface CustomChatMessageRepository {
    Optional<ChatRoom> findByChatId(Long id);

}
