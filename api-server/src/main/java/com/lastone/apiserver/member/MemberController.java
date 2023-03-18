package com.lastone.apiserver.member;

import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.mypage.MyPageDto;
import com.lastone.core.security.UserDetailsImpl;
import com.lastone.core.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Object> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getId();
        MyPageDto myPageDto = memberService.findMyPageInfoByMemberId(memberId);
        return ResponseEntity.ok().body(myPageDto);
    }

//    @PutMapping
//    public ResponseEntity<Object> updateMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody MemberDto memberDto) throws IOException {
//        memberService.checkIdentity(userDetails.getId(), memberDto.getId());
//        memberService.updateMyPage(memberDto);
//
//        return ResponseEntity.ok().body(memberDto);
//    }
}
