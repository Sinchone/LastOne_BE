package com.lastone.core.repository;

import com.lastone.core.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByHostIdAndParticipationId(Long hostId, Long participationId);
}