package com.lastone.apiserver;

import com.lastone.core.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestTokenController {

    @GetMapping
    public ResponseEntity<Object> getMemberFromTestToken(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        TestMemberDto dto = TestMemberDto.builder()
                .email(userDetails.getEmail())
                .nickname(userDetails.getNickname())
                .gender(userDetails.getGender())
                .build();

        return ResponseEntity.ok().body(dto);
    }
}


@Builder
@Getter
@AllArgsConstructor
class TestMemberDto {
    private String email;

    private String nickname;

    private String gender;
}
