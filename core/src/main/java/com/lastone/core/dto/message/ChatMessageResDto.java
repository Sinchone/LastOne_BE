package com.lastone.core.dto.message;

import com.lastone.core.domain.chat.ChatMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ChatMessageResDto {
    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private boolean isRead;
    private LocalDateTime sendTime;

    public ChatMessageResDto(ChatMessage chatMessage) {
        this.chatRoomId = chatMessage.getChatRoomId();
        this.senderId = chatMessage.getSenderId();
        this.receiverId = chatMessage.getReceiverId();
        this.content = chatMessage.getContent();
        this.isRead = chatMessage.isRead();
        this.sendTime = chatMessage.getCreatedAt();
    }
}
