package com.lastone.chat.service;

import com.lastone.chat.dto.ChatRoomDetailDto;
import com.lastone.chat.dto.ChatRoomResDto;
import com.lastone.chat.dto.NewMessageResponseDto;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatRoomService {
    String createRoom(Long userId, ChatRoomCreateReqDto createReqDto);
    void deleteRoom(String roomId, Long userId);
    List<ChatRoomResDto> getList(Long userId, Pageable pageable);
    ChatRoomDetailDto getOne(String roomId, Long userId);
    NewMessageResponseDto getChatRoomInfoByRoomId(String chatRoomId, Long userId);
}
