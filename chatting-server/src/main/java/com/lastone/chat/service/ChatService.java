package com.lastone.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastone.chat.dto.ChatRoomListResDto;
import com.lastone.core.domain.chat.ChatRoom;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import com.lastone.core.persistence.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<Long, ChatRoom> chatRooms;
    private ChatRoomRepository chatRoomRepository;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom getChatRoomById(Long id) {
        return chatRooms.get(id);
    }

    public ChatRoom createRoom(ChatRoomCreateReqDto reqDto) {
        ChatRoom chatRoom = ChatRoom.builder()
                                .hostId(reqDto.getHostId())
                                .participationId(reqDto.getParticipationId())
                                .build();
//        ChatRoom savedChatRoom = chatRepository.save(chatRoom);
//        chatRooms.put(savedChatRoom.getId(), savedChatRoom);
//        return savedChatRoom;
        return null;
    }
    public List<ChatRoomListResDto> getAllChatRoom() {

        return new ArrayList<ChatRoomListResDto>();
    }
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
