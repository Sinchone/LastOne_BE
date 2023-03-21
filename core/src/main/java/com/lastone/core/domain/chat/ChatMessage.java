package com.lastone.core.domain.chat;

import com.lastone.core.dto.message.ChatMessageReqDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "chat_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private boolean isRead;

    @CreatedDate
    private LocalDateTime createdAt;

    public ChatMessage(ChatMessageReqDto messageReqDto) {
        this.senderId = messageReqDto.getSenderId();
        this.receiverId = messageReqDto.getReceiverId();
        this.content = messageReqDto.getContent();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }
}
