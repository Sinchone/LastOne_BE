package com.lastone.chat.dto;

import com.lastone.core.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class NewMessageResponseDto {
    private Long otherUserId;
    private String profileUrl;
    private String nickname;
    private String gender;
    private String content;
    private LocalDateTime sendTime;

    public NewMessageResponseDto(Member otherMember) {
        this.otherUserId = otherMember.getId();
        this.profileUrl = otherMember.getProfileUrl();
        this.nickname = otherMember.getNickname();
        this.gender = otherMember.getGender();
    }

    public void setMessage(ChatMessageResDto message) {
        this.content = message.getContent();
        this.sendTime = message.getSendTime();
    }
}