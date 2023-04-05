package com.lastone.core.dto.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomCreateReqDto {
    private Long hostId;
    private Long participationId;
    @Builder
    public ChatRoomCreateReqDto(Long hostId, Long participationId) {
        this.hostId = hostId;
        this.participationId = participationId;
    }
}
