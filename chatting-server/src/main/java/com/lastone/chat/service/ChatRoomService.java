package com.lastone.chat.service;

import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;

public interface ChatRoomService {
    String createRoom(Long userId, ChatRoomCreateReqDto createReqDto);
    void deleteRoom(String roomId, Long userId);


}
