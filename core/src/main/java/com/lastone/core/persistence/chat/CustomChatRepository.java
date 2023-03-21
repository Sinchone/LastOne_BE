package com.lastone.core.persistence.chat;

import com.lastone.core.domain.chat.ChatRoom;

import java.util.Optional;

public interface CustomChatRepository {
    Optional<ChatRoom> findByChatId(Long id);

}
