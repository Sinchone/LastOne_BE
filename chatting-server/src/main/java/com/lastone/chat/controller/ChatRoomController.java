package com.lastone.chat.controller;

import com.lastone.chat.service.ChatRoomService;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat-room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @GetMapping("/{roomId}")
    public String getRoom(@PathVariable Long roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "chat/chat";
    }
    @PostMapping
    public ResponseEntity<Long> createChatRoom(@RequestBody ChatRoomCreateReqDto chatRoomCreateReqDto) {
        Long userId = 5L;
        return ResponseEntity.ok(chatRoomService.createRoom(userId, chatRoomCreateReqDto));
    }
    @DeleteMapping("/{roomId}")
    public void deleteChatRoom(@PathVariable Long roomId) {
        Long userId = 5L;
        chatRoomService.deleteRoom(roomId, userId);
    }
    @GetMapping
    public ResponseEntity<Object> getList(Pageable pageable) {
        Long userId = 5L;
        chatRoomService.getList(userId, pageable);
        return ResponseEntity.ok("");
    }
}