package com.lastone.apiserver.member;

import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.mypage.MyPageDto;
import com.lastone.core.dto.mypage.MyPageUpdateDto;
import com.lastone.core.dto.sbd.SbdDto;
import com.lastone.core.security.UserDetailsImpl;
import com.lastone.core.service.gym.GymService;
import com.lastone.core.service.member.MemberService;
import com.lastone.core.service.sbd.SbdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    private final GymService gymService;

    private final SbdService sbdService;

    @GetMapping
    public ResponseEntity<Object> getMember(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getId();
        MemberDto member = memberService.findById(memberId);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Object> getMemberById(@PathVariable Long memberId) {
        MemberDto member = memberService.findById(memberId);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/{memberId}/gym")
    public ResponseEntity<Object> getGymByMemberId(@PathVariable Long memberId) {
        List<GymDto> gyms = gymService.findAllByMemberId(memberId);
        return ResponseEntity.ok().body(gyms);
    }

    @GetMapping("/{memberId}/sbd")
    public ResponseEntity<Object> getSbdByMemberId(@PathVariable Long memberId) {
        SbdDto sbd = sbdService.findByMemberId(memberId);
        return ResponseEntity.ok().body(sbd);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Object> updateMember(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        validateAuthorization(userDetails.getId(), memberId);
        memberService.

    }


    @PutMapping
    public ResponseEntity<Object> updateMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart MyPageUpdateDto myPageUpdateDto, @RequestPart MultipartFile profileImg) throws IOException {
        memberService.checkIdentity(userDetails.getId(), myPageUpdateDto.getId());
        memberService.updateMyPage(myPageUpdateDto);

        return ResponseEntity.ok().build();
    }

    private void validateAuthorization(Long id, Long memberId) {
        if (!id.equals(memberId)) {
            throw new IllegalArgumentException("수정할 권한이 없습니다.");
        }
    }
}
