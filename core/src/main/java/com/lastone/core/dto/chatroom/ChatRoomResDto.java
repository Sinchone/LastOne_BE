package com.lastone.core.dto.chatroom;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ChatRoomResDto {
    private Long id; // 채팅방 아이디
    private String nickname; // 상대방 닉네임
    private String gender; // 성별
    private String profileUrl; // 프로필 이미지 링크
    private String lastMessage; // 마지막 메세지
    private LocalDateTime lastMessageTime; // 마지막 시각


}
