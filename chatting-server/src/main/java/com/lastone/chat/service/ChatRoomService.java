package com.lastone.chat.service;

import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;

public interface ChatRoomService {
    Long createRoom(Long userId, ChatRoomCreateReqDto createReqDto);
    void deleteRoom(Long roomId, Long userId);
}
