package com.lastone.core.domain.chat;

import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long hostId;
    private Long participationId;

    @Enumerated(EnumType.STRING)
    private ChatStatus status;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public ChatRoom(Long hostId, Long participationId) {
        this.hostId = hostId;
        this.participationId = participationId;
    }
    public ChatRoom(ChatRoomCreateReqDto createReqDto) {
        this.hostId = createReqDto.getHostId();
        this.participationId = createReqDto.getParticipationId();
        this.status = ChatStatus.NORMAL;
    }
    @PrePersist
    public void preCreated() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdated() {
        this.updatedAt = LocalDateTime.now();
    }
}
