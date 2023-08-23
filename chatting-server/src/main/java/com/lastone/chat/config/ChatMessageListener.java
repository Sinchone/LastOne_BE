package com.lastone.chat.config;

import com.lastone.chat.dto.ChatMessageResDto;
import com.lastone.chat.dto.NewMessageResponseDto;
import com.lastone.chat.exception.ChatException;
import com.lastone.chat.service.ChatMessageService;
import com.lastone.chat.service.ChatRoomService;
import com.lastone.core.dto.message.ChatMessageReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatMessageListener {
    private final ChatMessageService messageService;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    @RabbitListener(queues = "chat.queue")
    public void handleMessage(ChatMessageReqDto messageReqDto) {
        try{
            String chatRoomId = messageReqDto.getRoomId();
            log.info("handleMessage      chatRoomId    :   {}", chatRoomId);
            if(!StringUtils.hasText(chatRoomId)) chatRoomId = "642da14657803f60253a39bf";
            ChatMessageResDto message = messageService.createMessage(chatRoomId, messageReqDto);

            NewMessageResponseDto newMessageResponseDto = chatRoomService.getChatRoomInfoByRoomId(chatRoomId, messageReqDto.getSenderId());
            newMessageResponseDto.setMessage(message, chatRoomId);
            log.info("newMessageResponseDto    :    {}", newMessageResponseDto);
            log.info("message.getReceiverId()     :   {}", message.getReceiverId());
            simpMessagingTemplate.convertAndSend("/topic/" + chatRoomId, message);
            simpMessagingTemplate.convertAndSend("/topic/" + message.getReceiverId(), newMessageResponseDto);
        }
        catch (ChatException e) {
            log.info("e.getStackTrace()   :   {}", e.getMessage());
        }
    }
}