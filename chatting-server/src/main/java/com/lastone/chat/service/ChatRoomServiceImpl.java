package com.lastone.chat.service;

import com.lastone.chat.exception.ChatException;
import com.lastone.core.repository.ChatRoomRepository;
import com.lastone.core.domain.chat.ChatRoom;
import com.lastone.core.domain.chat.ChatStatus;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import com.lastone.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방을 생성하기 전, 이미 해당 참여자가 있는지 확인
     * 없다면 채팅방 생성
     * 있다면 해당 채팅방의 번호 반환
     */
    @Override
    public Long createRoom(Long userId, ChatRoomCreateReqDto createReqDto) {
        Map<String, Long> userIdMap = userIdSort(userId, createReqDto.getParticipationId());
        Long hostId = userIdMap.get("hostId");
        Long participationId = userIdMap.get("participationId");

        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByHostIdAndParticipationId(hostId, participationId);

        if(!chatRoomOptional.isPresent()) {
            ChatRoom createChatRoom = ChatRoom.create(hostId, participationId);
            ChatRoom save = chatRoomRepository.save(createChatRoom);
            return save.getId();
        }else {
            isRoomValidation(chatRoomOptional.get().getStatus());
        }
        return chatRoomOptional.get().getId();
    }
    private void isRoomValidation(ChatStatus roomStatus) {
        if(roomStatus.equals(ChatStatus.BLOCKED)) {
            throw new ChatException(ErrorCode.BLOCKED_CHAT_ROOM);
        }
        if(roomStatus.equals(ChatStatus.DELETED)) {
            throw new ChatException(ErrorCode.ALREADY_DELETED_CHAT_ROOM);
        }
    }
    private Map<String, Long> userIdSort(Long loginedMemberId, Long otherMemberId) {
        Map<String, Long> userMap = new HashMap<>();
        Long hostId;
        Long participationId;

        if(isBiggerThanOtherUserId(loginedMemberId, otherMemberId)) {
            hostId = loginedMemberId;
            participationId = otherMemberId;
        }else {
            hostId = otherMemberId;
            participationId = loginedMemberId;
        }
        userMap.put("hostId", hostId);
        userMap.put("participationId", participationId);

        return userMap;
    }
    private boolean isBiggerThanOtherUserId(Long loginedMemberId, Long otherMemberId) {
        return loginedMemberId > otherMemberId;
    }
}
