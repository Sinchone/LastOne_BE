package com.lastone.core.dto.chatroom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomCreateReqDto {
    private Long hostId;
    private Long participationId;
}
