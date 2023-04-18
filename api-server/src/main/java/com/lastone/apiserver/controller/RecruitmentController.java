package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.recruitment.RecruitmentService;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.SuccessCode;
import com.lastone.core.dto.recruitment.RecruitmentRequestDto;
import com.lastone.core.dto.recruitment.RecruitmentDetailDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import com.lastone.core.security.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping
    public ResponseEntity<Object> getRecruitmentList(RecruitmentSearchCondition searchCondition) {
        Page<RecruitmentListDto> recruitmentList = recruitmentService.getList(searchCondition);
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.RECRUITMENT_LIST, recruitmentList));
    }

    @GetMapping("/{recruitmentId}")
    public ResponseEntity<Object> getRecruitmentDetail(@PathVariable Long recruitmentId) {
        RecruitmentDetailDto recruitmentDetail = recruitmentService.getDetail(recruitmentId);
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.RECRUITMENT_DETAIL, recruitmentDetail));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Object> createRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @RequestPart @Validated RecruitmentRequestDto recruitment,
                                                    @RequestPart(required = false) List<MultipartFile> imgFiles) throws IOException {
        recruitmentService.createRecruitment(userDetails.getId(), recruitment, imgFiles);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(SuccessCode.RECRUITMENT_CREATE));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{recruitmentId}")
    public ResponseEntity<Object> updateRecruitment(@PathVariable Long recruitmentId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @RequestPart(required = false) @Validated RecruitmentRequestDto recruitment,
                                                    @RequestPart(required = false) List<MultipartFile> imgFiles) throws IOException {
        recruitmentService.updateRecruitment(recruitmentId, userDetails.getId(), recruitment, imgFiles);
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.RECRUITMENT_UPDATE));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{recruitmentId}")
    public ResponseEntity<Object> deleteRecruitment(@PathVariable Long recruitmentId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        recruitmentService.deleteRecruitment(recruitmentId, userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.RECRUITMENT_DELETE));
    }
}
