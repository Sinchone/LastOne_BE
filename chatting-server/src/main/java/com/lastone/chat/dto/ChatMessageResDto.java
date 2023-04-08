package com.lastone.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    private Long senderId;
    private Long receiverId;
    private String content;
    private boolean isRead;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;

    public ChatMessageResDto(ChatMessage chatMessage) {
        this.senderId = chatMessage.getSenderId();
        this.receiverId = chatMessage.getReceiverId();
        this.content = chatMessage.getContent();
        this.isRead = chatMessage.isRead();
        this.sendTime = chatMessage.getCreatedAt();
    }
}
