package com.lastone.core.dto.chatroom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomCreateReqDto {
    private Long participationId;
    public ChatRoomCreateReqDto(Long participationId) {
        this.participationId = participationId;
    }
}
