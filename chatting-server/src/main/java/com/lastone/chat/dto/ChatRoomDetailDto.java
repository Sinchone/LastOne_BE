package com.lastone.chat.dto;

import com.lastone.chat.persistence.ChatMessage;
import com.lastone.core.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class ChatRoomDetailDto {
    private Long otherUserId;
    private String profileUrl;
    private String nickname;
    private String gender;
    private List<ChatMessageResDto> messages;

    public ChatRoomDetailDto(List<ChatMessage> messages , Member otherMember) {
        this.otherUserId = otherMember.getId();
        this.profileUrl = otherMember.getProfileUrl();
        this.nickname = otherMember.getNickname();
        this.gender = otherMember.getGender();

        this.messages = messages.stream()
                            .map(message -> new ChatMessageResDto(message))
                            .collect(Collectors.toList());
    }
}
