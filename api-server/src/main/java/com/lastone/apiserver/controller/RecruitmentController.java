package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.recruitment.RecruitmentService;
import com.lastone.core.dto.recruitment.RecruitmentCreateDto;
import com.lastone.core.security.principal.UserDetailsImpl;
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
@RequestMapping("/api/recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PostMapping
    public ResponseEntity<Object> createRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @RequestPart RecruitmentCreateDto recruitment,
                                                    @RequestPart List<MultipartFile> imgFiles) throws IOException {
        recruitmentService.createRecruitment(userDetails.getId(), recruitment, imgFiles);

        return ResponseEntity.ok().build();
    }
}
