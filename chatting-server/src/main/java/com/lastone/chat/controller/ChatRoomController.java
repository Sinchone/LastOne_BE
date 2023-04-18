package com.lastone.chat.controller;

import com.lastone.chat.dto.ChatRoomDetailDto;
import com.lastone.chat.service.ChatRoomService;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.SuccessCode;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import com.lastone.core.security.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @GetMapping("/{roomId}")
    public ResponseEntity<CommonResponse> getRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String roomId) {
        ChatRoomDetailDto chatRoomDetail = chatRoomService.getOne(roomId, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(chatRoomDetail));
    }
    @PostMapping
    public ResponseEntity<CommonResponse> createChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ChatRoomCreateReqDto chatRoomCreateReqDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(
                        chatRoomService.createRoom(userDetails.getId(), chatRoomCreateReqDto),
                        SuccessCode.CREATED_CHAT_ROOM.getMessage()
                )
        );
    }
    @DeleteMapping("/{roomId}")
    public ResponseEntity<CommonResponse> deleteChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String roomId) {
        chatRoomService.deleteRoom(roomId, userDetails.getId());
        return ResponseEntity.ok(CommonResponse.success(SuccessCode.DELETED_CHAT_ROOM.getMessage()));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getList(@AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable) {
        return ResponseEntity.ok(
                CommonResponse.success(
                        chatRoomService.getList(userDetails.getId(), pageable),
                        SuccessCode.GET_CHAT_ROOM_LIST.getMessage()
                )
        );
    }
}