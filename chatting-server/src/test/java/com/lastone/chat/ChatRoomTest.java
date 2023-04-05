package com.lastone.chat;

import com.lastone.chat.service.ChatRoomServiceImpl;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@Profile("local")
@ExtendWith(MockitoExtension.class)
class ChatRoomTest {
    @Autowired
    private WebTestClient webTestClient;
    @InjectMocks
    ChatRoomServiceImpl chatRoomService;

    @Test
    @DisplayName("채팅방 생성 테스트")
    public void createRoomTest() {
        Long userId = 5L;
        ChatRoomCreateReqDto createReqDto =
                ChatRoomCreateReqDto.builder()
                    .hostId(1L)
                    .participationId(7L)
                    .build();
        Long roomId = chatRoomService.createRoom(userId, createReqDto);
        assertNotNull(roomId);
        when(chatRoomService.createRoom(userId, createReqDto))
                .thenReturn(roomId);
    }
}
