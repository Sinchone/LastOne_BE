package com.lastone.core.persistence.chatmessage;

import com.lastone.core.domain.chat.ChatRoom;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomChatMessageRepositoryImpl implements CustomChatMessageRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<ChatRoom> findByChatId(Long id) {
        return Optional.empty();
    }
    public ChatRoom createChatRoom(ChatRoomCreateReqDto createReqDto) {
        ChatRoom chatRoom = new ChatRoom(createReqDto);
        return null;
    }
}
