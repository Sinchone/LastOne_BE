package com.lastone.core.dto.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageReqDto {
    private String roomId;
    private Long senderId;
    private Long receiverId;
    private String content;

    private ChatMessageReqDto() {}

    @Builder
    public ChatMessageReqDto(Long senderId, Long receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }
}
