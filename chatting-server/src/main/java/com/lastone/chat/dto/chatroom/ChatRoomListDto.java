package com.lastone.chat.dto.chatroom;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomListDto {
    private Long roomId;
    private List<String> content;
    private LocalDateTime createdAt;
}
