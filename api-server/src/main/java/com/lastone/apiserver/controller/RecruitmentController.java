package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.recruitment.RecruitmentService;
import com.lastone.core.dto.recruitment.RecruitmentCreateDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import com.lastone.core.security.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<Object> getRecruitmentList(RecruitmentSearchCondition searchCondition) {
        Page<RecruitmentListDto> data = recruitmentService.getList(searchCondition);
        return ResponseEntity.ok().body(data);
    }

    @Secured("ROLE_MEMBER")
    @PostMapping
    public ResponseEntity<Object> createRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @RequestPart RecruitmentCreateDto recruitment,
                                                    @RequestPart List<MultipartFile> imgFiles) throws IOException {
        recruitmentService.createRecruitment(userDetails.getId(), recruitment, imgFiles);

        return ResponseEntity.ok().build();
    }
}
