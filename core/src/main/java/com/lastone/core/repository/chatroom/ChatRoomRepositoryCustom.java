package com.lastone.core.repository.chatroom;

import com.lastone.core.domain.chat.ChatRoom;
import org.springframework.data.domain.Page;

public interface ChatRoomRepositoryCustom {
    Page<ChatRoom> getList();
}
