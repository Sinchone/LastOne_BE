package com.lastone.chat.controller;

import com.lastone.chat.dto.ChatRoomDetailDto;
import com.lastone.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test/chat-room")
@RequiredArgsConstructor
public class TestChattingController {
    private final ChatRoomService chatRoomService;
    @GetMapping("/{roomId}")
    @PreAuthorize("permitAll()")
    public String getRoom(@PathVariable String roomId, Model model, @RequestParam Long userId, @RequestParam Long otherId) {
        ChatRoomDetailDto chatRoomDetail = chatRoomService.getOne(roomId, userId);
        model.addAttribute("userId", userId);
        model.addAttribute("otherId", otherId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("chatRoomId", roomId);
        model.addAttribute("info", chatRoomDetail);
        return "chat/chat";
    }
}
