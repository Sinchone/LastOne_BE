package com.lastone.chat.service;

import com.lastone.chat.exception.CannotFountChatRoom;
import com.lastone.chat.exception.ChatException;
import com.lastone.chat.persistence.ChatMessage;
import com.lastone.chat.persistence.ChatRoom;
import com.lastone.chat.repository.ChatMessageRepository;
import com.lastone.chat.repository.ChatRoomRepository;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.domain.chat.ChatStatus;
import com.lastone.core.dto.message.ChatMessageReqDto;
import com.lastone.chat.dto.ChatMessageResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository messageRepository;
    private final ChatRoomRepository roomRepository;
    @Override
    public ChatMessageResDto createMessage(String roomId, ChatMessageReqDto messageReqDto) {
        ChatRoom chatRoom = roomRepository.findById(roomId).orElseThrow(CannotFountChatRoom::new);
        validationChatRoom(chatRoom.getStatus());
        ChatMessage chatMessage = new ChatMessage(roomId, messageReqDto);
        return new ChatMessageResDto(messageRepository.save(chatMessage));
    }
    private void validationChatRoom(ChatStatus status) {
        if(status.equals(ChatStatus.DELETED)) {
            throw new ChatException(ErrorCode.ALREADY_DELETED_CHAT_ROOM);
        }
        if(status.equals(ChatStatus.BLOCKED)){
            throw new ChatException(ErrorCode.BLOCKED_CHAT_ROOM);
        }
    }
}