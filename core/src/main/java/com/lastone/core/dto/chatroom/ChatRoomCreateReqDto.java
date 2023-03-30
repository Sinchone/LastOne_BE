package com.lastone.core.dto.chatroom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateReqDto {
    private Long hostId;
    private Long participationId;
}
