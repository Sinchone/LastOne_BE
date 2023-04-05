package com.lastone.core.repository.chatroom;

import com.lastone.core.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
    Optional<ChatRoom> findByHostIdAndParticipationId(Long hostId, Long participationId);
}