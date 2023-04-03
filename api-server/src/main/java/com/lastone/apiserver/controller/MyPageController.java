package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.mypage.MyPageService;
import com.lastone.apiserver.service.mypage.MyPageServiceImpl;
import com.lastone.core.dto.mypage.MyPageDto;
import com.lastone.core.dto.mypage.MyPageUpdateDto;
import com.lastone.core.dto.response.BaseResponse;
import com.lastone.core.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")
    public ResponseEntity<Object> getMyPageByToken(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getId();
        MyPageDto myPage = myPageService.getMyPage(memberId);

        BaseResponse baseResponse = BaseResponse.builder()
                .message("마이페이지 조회에 성공하였습니다.")
                .data(myPage)
                .build();

        return ResponseEntity.ok().body(baseResponse);
    }

    @GetMapping("/mypage/{memberId}")
    public ResponseEntity<Object> getMyPageByMemberId(@PathVariable Long memberId) {
        MyPageDto myPage = myPageService.getMyPage(memberId);

        BaseResponse baseResponse = BaseResponse.builder()
                .message("마이페이지 조회에 성공하였습니다.")
                .data(myPage)
                .build();

        return ResponseEntity.ok().body(baseResponse);
    }

    @PutMapping("/mypage")
    public ResponseEntity<Object> updateMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestPart(required = false) @Validated MyPageUpdateDto myPage,
                                               @RequestPart(required = false) MultipartFile profileImg) throws IOException {
        Long memberId = userDetails.getId();
        myPageService.updateMyPage(memberId, myPage, profileImg);

        BaseResponse baseResponse = BaseResponse.builder()
                .message("마이페이지 수정에 성공하였습니다.")
                .build();

        return ResponseEntity.ok().body(baseResponse);
    }

















}
