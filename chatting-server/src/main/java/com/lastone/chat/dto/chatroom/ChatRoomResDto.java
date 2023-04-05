package com.lastone.chat.dto.chatroom;

import com.lastone.core.domain.chat.ChatStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRoomResDto {
    private Long roomId;
    private Long userId;
    private String nickName;
    private String lastChat;
    private LocalDateTime lastestChatTime;
    private ChatStatus status;
}
