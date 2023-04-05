package com.lastone.chat.service;

import com.lastone.chat.dto.ChatMessageResDto;
import com.lastone.core.dto.message.ChatMessageReqDto;

public interface ChatMessageService {
    ChatMessageResDto createMessage(String roomId, ChatMessageReqDto messageReqDto);
}