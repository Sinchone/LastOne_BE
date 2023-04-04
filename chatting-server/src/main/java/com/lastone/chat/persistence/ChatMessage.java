package com.lastone.chat.persistence;

import com.lastone.core.dto.message.ChatMessageReqDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(value = "messages")
public class ChatMessage {
    @Id
    private String id;
    private Long senderId;
    private Long roomId;
    private Long receiverId;
    private String content;
    private boolean isRead;

    @CreatedDate
    private LocalDateTime createdAt;

    public ChatMessage(Long roomId, ChatMessageReqDto messageReqDto) {
        this.senderId = messageReqDto.getSenderId();
        this.roomId = roomId;
        this.receiverId = messageReqDto.getReceiverId();
        this.content = messageReqDto.getContent();
        this.isRead = false;
        this.createdAt = LocalDateTime.now();
    }
}