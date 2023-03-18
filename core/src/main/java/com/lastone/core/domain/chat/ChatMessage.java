package com.lastone.core.domain.chat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private boolean isRead;
    private LocalDateTime createdAt;
}
