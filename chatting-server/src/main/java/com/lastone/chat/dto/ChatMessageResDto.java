package com.lastone.chat.dto;

import com.lastone.chat.persistence.ChatMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageResDto {
    private Long roomId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private boolean isRead;
    private LocalDateTime sendTime;

    public ChatMessageResDto(ChatMessage chatMessage) {
        this.roomId = chatMessage.getRoomId();
        this.senderId = chatMessage.getSenderId();
        this.receiverId = chatMessage.getReceiverId();
        this.content = chatMessage.getContent();
        this.isRead = chatMessage.isRead();
        this.sendTime = chatMessage.getCreatedAt();
    }
}
