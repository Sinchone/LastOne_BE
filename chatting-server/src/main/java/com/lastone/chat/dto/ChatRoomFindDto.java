package com.lastone.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ChatRoomFindDto {
    private String id;
    private List<List<Long>> other;
    private String content;
    private LocalDateTime createdAt;
    private Long notReadCount;
}
