package com.lastone.chat.service;

import com.lastone.chat.dto.ChatRoomResDto;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRoomService {
    String createRoom(Long userId, ChatRoomCreateReqDto createReqDto);
    void deleteRoom(String roomId, Long userId);
    Page<ChatRoomResDto> getList(Long userId, Pageable pageable);

}
