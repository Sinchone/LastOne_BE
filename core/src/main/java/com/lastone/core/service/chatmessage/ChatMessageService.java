package com.lastone.core.service.chatmessage;

import com.lastone.core.dto.message.ChatMessageReqDto;
import com.lastone.core.dto.message.ChatMessageResDto;

public interface ChatMessageService {
    ChatMessageResDto createMessage(Long roomId, ChatMessageReqDto messageReqDto);
}
