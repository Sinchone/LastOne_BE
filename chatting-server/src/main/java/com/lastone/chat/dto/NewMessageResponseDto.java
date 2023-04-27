package com.lastone.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lastone.core.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class NewMessageResponseDto {
    private String roomId;
    private Long otherUserId;
    private String profileUrl;
    private String nickname;
    private String gender;
    private String content;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;

    public NewMessageResponseDto(Member otherMember) {
        this.otherUserId = otherMember.getId();
        this.profileUrl = otherMember.getProfileUrl();
        this.nickname = otherMember.getNickname();
        this.gender = otherMember.getGender();
    }

    public void setMessage(ChatMessageResDto message, String roomId) {
        this.roomId = roomId;
        this.content = message.getContent();
        this.sendTime = message.getSendTime();
    }
}