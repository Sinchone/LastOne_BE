package com.lastone.chat.controller;

import com.lastone.chat.dto.ChatRoomListResDto;
import com.lastone.chat.service.ChatService;
import com.lastone.core.domain.chat.ChatRoom;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import com.lastone.core.service.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/{id}")
    public void enterRoom(@PathVariable Long id , Model model) {
        model.addAttribute("hello", "안녕 너는 "+ id + "번 방에 왔어");
        System.out.println(id + "로 왔음");
    }

    @PostMapping
    public String createRoom(@ModelAttribute ChatRoomCreateReqDto reqDto) {
        ChatRoom chatRoom = chatRoomService.create(reqDto);
        System.out.println("chatRoom.getId()  : " + chatRoom.getId());

        return "redirect:/" + chatRoom.getId();
    }

    /**
     * @author 규현
     * @return
     */
    @GetMapping
    public List<ChatRoomListResDto> findAllRooms() {
        return null;
    }
}
