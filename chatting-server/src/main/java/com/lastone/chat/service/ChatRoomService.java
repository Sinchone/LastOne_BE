package com.lastone.chat.service;

import com.lastone.chat.dto.ChatRoomDetailDto;
import com.lastone.chat.dto.ChatRoomResDto;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;

import java.util.List;

public interface ChatRoomService {
    String createRoom(Long userId, ChatRoomCreateReqDto createReqDto);
    void deleteRoom(String roomId, Long userId);
    List<ChatRoomResDto> getList(Long userId);
    ChatRoomDetailDto getOne(String roomId, Long userId);
}
