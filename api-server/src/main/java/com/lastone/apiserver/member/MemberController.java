package com.lastone.apiserver.member;

import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.gym.GymUpdateDto;
import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.member.MemberUpdateDto;
import com.lastone.core.dto.sbd.SbdDto;
import com.lastone.core.security.UserDetailsImpl;
import com.lastone.core.service.gym.GymService;
import com.lastone.core.service.member.MemberService;
import com.lastone.core.service.sbd.SbdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, Object> result = new HashMap<>();
        result.put("message", "회원 조회에 성공하였습니다.");
        result.put("member", member);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Object> getMemberById(@PathVariable Long memberId) {
        MemberDto member = memberService.findById(memberId);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "회원 조회에 성공하였습니다.");
        result.put("member", member);

        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/{memberId}/gym")
    public ResponseEntity<Object> getGymByMemberId(@PathVariable Long memberId) {
        memberService.isExist(memberId);
        List<GymDto> gyms = gymService.findAllByMemberId(memberId);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "헬스장 조회에 성공하였습니다.");
        result.put("gyms", gyms);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{memberId}/sbd")
    public ResponseEntity<Object> getSbdByMemberId(@PathVariable Long memberId) {
        memberService.isExist(memberId);
        SbdDto sbd = sbdService.findByMemberId(memberId);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "삼대 운동 능력 조회에 성공하였습니다.");
        result.put("sbd", sbd);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Object> updateMember(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart(required = false) @Validated MemberUpdateDto memberUpdateDto, @RequestPart(required = false) MultipartFile profileImg) throws IOException {
        validateAuthorization(userDetails.getId(), memberId);
        memberService.update(memberId, memberUpdateDto, profileImg);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "회원 정보 수정이 완료되었습니다.");

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{memberId}/gym")
    public ResponseEntity<Object> updateGym(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody GymUpdateDto gymUpdateDto) {
        validateAuthorization(userDetails.getId(), memberId);
        gymService.updateByMemberId(gymUpdateDto.getGyms(), memberId);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "회원의 헬스장 정보 수정이 완료되었습니다.");

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{memberId}/sbd")
    public ResponseEntity<Object> updateSbd(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Validated SbdDto sbdDto) {
        validateAuthorization(userDetails.getId(), memberId);
        sbdService.updateByMemberId(sbdDto, memberId);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "회원의 3대 운동 능력 수정 작업이 완료되었습니다.");

        return ResponseEntity.ok().body(result);

    }

    private void validateAuthorization(Long id, Long memberId) {
           if (!id.equals(memberId)) {
            throw new IllegalArgumentException("수정할 권한이 없습니다.");
        }
    }
}
