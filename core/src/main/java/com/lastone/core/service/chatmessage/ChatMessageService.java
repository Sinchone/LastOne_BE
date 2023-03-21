package com.lastone.core.service.chatmessage;

import com.lastone.core.domain.chat.ChatRoom;
import com.lastone.core.dto.message.ChatMessageReqDto;
import com.lastone.core.dto.message.ChatMessageResDto;

public interface ChatMessageService {
    ChatRoom getChatRoomMessage(Long id);
    ChatMessageResDto createMessage(Long chatRoomId, ChatMessageReqDto messageReqDto);
}
