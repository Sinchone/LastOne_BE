package com.lastone.chat.controller;

import com.lastone.chat.dto.ChatRoomDetailDto;
import com.lastone.chat.service.ChatRoomService;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.SuccessCode;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chat-room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @GetMapping("/{roomId}")
    public String getRoom(@PathVariable String roomId, Model model) {
        Long userId = 5L;
        ChatRoomDetailDto chatRoomDetail = chatRoomService.getOne(roomId, userId);
        model.addAttribute("userId", userId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("info", chatRoomDetail);
        return "chat/chat";
    }
    @PostMapping
    public ResponseEntity<CommonResponse> createChatRoom(@RequestBody ChatRoomCreateReqDto chatRoomCreateReqDto) {
        Long userId = 5L;
        SuccessCode successCode = SuccessCode.CREATED_CHAT_ROOM;

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(
                        chatRoomService.createRoom(userId, chatRoomCreateReqDto),
                        successCode.getMessage())
        );
    }
    @DeleteMapping("/{roomId}")
    @ResponseBody
    public ResponseEntity<CommonResponse> deleteChatRoom(@PathVariable String roomId) {
        Long userId = 5L;
        chatRoomService.deleteRoom(roomId, userId);

        return ResponseEntity.ok(CommonResponse.success(SuccessCode.DELETED_CHAT_ROOM.getMessage()));
    }

    /**
     * Todo - 회원번호 받는 부분 추가하기
     * @param pageable
     * @return
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<CommonResponse> getList(Pageable pageable) {
        Long userId = 5L;
        SuccessCode successCode = SuccessCode.GET_CHAT_ROOM_LIST;

        return ResponseEntity.ok(
                CommonResponse.success(
                        chatRoomService.getList(userId, pageable),
                        successCode.getMessage()
                )
        );
    }
}