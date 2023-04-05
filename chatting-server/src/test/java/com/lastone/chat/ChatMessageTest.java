package com.lastone.chat;

import com.lastone.chat.dto.ChatMessageResDto;
import com.lastone.chat.persistence.ChatMessage;
import com.lastone.chat.repository.ChatMessageRepository;
import com.lastone.chat.service.ChatMessageService;
import com.lastone.chat.service.ChatMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.lastone.core.dto.message.ChatMessageReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatMessageTest {
    @Autowired
    private WebTestClient webTestClient;
    @InjectMocks
    ChatMessageServiceImpl messageService;
    @Mock
    ChatMessageRepository messageRepository;

    ChatMessage chatMessage;
    @Test
    public void createTest() {

        Long roomId = 1L;
        ChatMessageReqDto messageReqDto =
                ChatMessageReqDto.builder()
                        .content("ㅎㅇㅎㅇ ㅋㅋㅋ")
                        .senderId(5L)
                        .receiverId(7L)
                    .build();
        ChatMessage chatMessage = new ChatMessage(roomId, messageReqDto);
        ChatMessageResDto messageResDtoMono = messageService.createMessage(roomId, messageReqDto);

        Mockito.when(messageService.createMessage(roomId, messageReqDto))
                .thenReturn(messageResDtoMono);
        webTestClient.post()
                        .uri("/test/message/send/" + roomId)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody(ChatMessageResDto.class)
                                .value(response -> {
                                   assertThat(response).isNotNull();
                                   assertThat(response.getContent().equals("ㅎㅇㅎㅇ ㅋㅋㅋ"));
                                });
    }
}
