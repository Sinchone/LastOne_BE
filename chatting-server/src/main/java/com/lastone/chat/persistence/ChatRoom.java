package com.lastone.chat.persistence;

import com.lastone.core.domain.chat.ChatStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Document(value = "chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    private String id;
    private List<Long> participations;
    private ChatStatus status;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    public void preCreated() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdated() {
        this.updatedAt = LocalDateTime.now();
    }

    public static ChatRoom create(Long hostId, Long participationId) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.participations = new ArrayList<>(Arrays.asList(hostId, participationId));
        chatRoom.status = ChatStatus.NORMAL;
        return chatRoom;
    }
    public void delete() {
        this.status = ChatStatus.DELETED;
    }
}
