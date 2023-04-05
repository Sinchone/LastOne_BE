package com.lastone.core.repository.chatroom;

import com.lastone.core.domain.chat.ChatRoom;
import static com.lastone.core.domain.chat.QChatRoom.chatRoom;

import com.lastone.core.domain.chat.ChatStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom{
    private final JPAQueryFactory factory;

    @Override
    public Page<ChatRoom> getList() {
        factory.select(chatRoom)
                .from(chatRoom)
                .where(eqStatusNormal())
                .fetch();
        return null;
    }

    private BooleanExpression eqStatusNormal() {
        return chatRoom.status.eq(ChatStatus.NORMAL);
    }
    private BooleanExpression offsetSearch(Long roomId) {
        return null;
    }
}
