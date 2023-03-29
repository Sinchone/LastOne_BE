package com.lastone.core.dto.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageReqDto {
    private Long senderId;
    private Long receiverId;
    private String content;
}
