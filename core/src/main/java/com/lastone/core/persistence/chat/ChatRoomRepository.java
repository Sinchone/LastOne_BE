package com.lastone.core.persistence.chat;

import com.lastone.core.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, CustomChatRepository {
}
