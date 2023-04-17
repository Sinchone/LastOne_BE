package com.lastone.chat.controller;

import com.lastone.chat.dto.ChatRoomDetailDto;
import com.lastone.chat.service.ChatRoomService;
import com.lastone.core.security.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test/chat-room")
@RequiredArgsConstructor
public class TestChattingController {
    private final ChatRoomService chatRoomService;
    @GetMapping("/{roomId}")
    public String getRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String roomId, Model model) {
        Long userId = 5L;
        ChatRoomDetailDto chatRoomDetail = chatRoomService.getOne(roomId, userId);
        model.addAttribute("userId", userId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("info", chatRoomDetail);
        return "chat/room";
    }
}
