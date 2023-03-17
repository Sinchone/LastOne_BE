package com.lastone.apiserver.mypage;

import com.lastone.core.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    public ResponseEntity<Object> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        MyPageDto myPageDto = myPageService.getMyPageDto(userDetails.getEmail());
        return ResponseEntity.ok().body(myPageDto);
    }
}
