package com.lastone.chat.dto;

import com.lastone.chat.persistence.ChatMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChatRoomFindDto {
    private String id;
    private List<Long> participations;
    private ChatMessage lastMessage;
    private Long notReadCount;
}
