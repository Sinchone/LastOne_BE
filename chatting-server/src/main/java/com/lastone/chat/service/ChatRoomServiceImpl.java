package com.lastone.chat.service;

import com.lastone.chat.persistence.MessageColumn;
import com.lastone.chat.persistence.RoomColumn;
import com.lastone.chat.exception.ChatException;
import com.lastone.chat.exception.NotParticipantChatRoom;
import com.lastone.chat.repository.ChatMessageRepository;
import com.lastone.chat.persistence.ChatRoom;
import com.lastone.core.domain.chat.ChatStatus;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import com.lastone.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatMessageRepository messageRepository;
    private final MongoTemplate mongoTemplate;
    /**
     * 채팅방을 생성하기 전, 이미 해당 참여자가 있는지 확인
     * 없다면 채팅방 생성
     * 있다면 해당 채팅방의 번호 반환
     */
    private final String senderId = MessageColumn.SENDERID.getWord();
    private final String receiverId = MessageColumn.RECEIVERID.getWord();
    private final String roomId = MessageColumn.ROOMID.getWord();
    private final String content = MessageColumn.CONTENT.getWord();
    private final String createdAt = MessageColumn.CREATEDAT.getWord();

    @Override
    public String createRoom(Long userId, ChatRoomCreateReqDto createReqDto) {
        Map<String, Long> userIdMap = userIdSort(userId, createReqDto.getParticipationId());
        Long hostId = userIdMap.get("hostId");
        Long participationId = userIdMap.get("participationId");

        Query query = Query.query(
                        Criteria.where(RoomColumn.PARTICIPATIONS.getWord())
                        .all(userIdMap));
        Optional<ChatRoom> chatRoomOptional = Optional.ofNullable(mongoTemplate.findOne(query, ChatRoom.class));
        if(chatRoomOptional.isEmpty()) {
            ChatRoom createChatRoom = ChatRoom.create(hostId, participationId);
            ChatRoom save = mongoTemplate.save(createChatRoom);
            return save.getId();
        }else {
            isRoomValidation(chatRoomOptional.get().getStatus());
        }
        return chatRoomOptional.get().getId();
    }

    @Override
    @Transactional
    public void deleteRoom(String roomId, Long userId) {
        Optional<ChatRoom> chatRoomOptional = Optional.ofNullable(mongoTemplate.findById(roomId, ChatRoom.class));

        if(chatRoomOptional.isEmpty()) {
            throw new ChatException(ErrorCode.NOT_FOUNT_ROOM);
        }
        ChatRoom chatRoom = chatRoomOptional.get();

        if(chatRoom.getStatus().equals(ChatStatus.DELETED)) {
            return;
        }
        isRoomValidation(chatRoom.getStatus());

        chatRoom.getParticipations().stream()
                .filter(paticipant -> paticipant == userId )
                .findFirst().orElseThrow(NotParticipantChatRoom::new);
        chatRoom.delete();
        mongoTemplate.save(chatRoomOptional);
    }



    private void isRoomValidation(ChatStatus roomStatus) {
        if(roomStatus.equals(ChatStatus.BLOCKED)) {
            throw new ChatException(ErrorCode.BLOCKED_CHAT_ROOM);
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
