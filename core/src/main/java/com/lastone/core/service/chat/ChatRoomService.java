package com.lastone.core.service.chat;

import com.lastone.core.domain.chat.ChatRoom;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;

public interface ChatRoomService {
    ChatRoom create(ChatRoomCreateReqDto createReqDto);
}
