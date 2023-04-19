package com.lastone.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lastone.core.domain.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ChatRoomResDto {
    private String roomId;
    private Long otherUserId;
    private String profileUrl;
    private String nickname;
    private String lastChat;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastChatTime;
    private Long notReadCount;

    public ChatRoomResDto(ChatRoomFindDto roomFindDto, Member member) {
        this.roomId = roomFindDto.getId();
        this.lastChat = roomFindDto.getLastMessage().getContent();
        this.lastChatTime = roomFindDto.getLastMessage().getCreatedAt();
        this.notReadCount = roomFindDto.getNotReadCount();
        this.otherUserId = member.getId();
        this.profileUrl = member.getProfileUrl();
        this.nickname = member.getNickname();
    }

}
