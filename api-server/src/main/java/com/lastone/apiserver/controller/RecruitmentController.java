package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.matching.MatchingService;
import com.lastone.apiserver.service.recruitment.RecruitmentService;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.SuccessCode;
import com.lastone.core.dto.recruitment.*;
import com.lastone.core.security.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
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

    private final MatchingService matchingService;

    @GetMapping
    public ResponseEntity<CommonResponse> getRecruitmentList(@Validated RecruitmentSearchCondition searchCondition) {
        Slice<RecruitmentListDto> recruitmentList = recruitmentService.getList(searchCondition);
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.RECRUITMENT_LIST, recruitmentList));
    }

    @GetMapping("/{recruitmentId}")
    public ResponseEntity<CommonResponse> getRecruitmentDetail(@PathVariable Long recruitmentId) {
        RecruitmentDetailDto recruitmentDetail = recruitmentService.getDetail(recruitmentId);
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.RECRUITMENT_DETAIL, recruitmentDetail));
    }

    @GetMapping("/main")
    public ResponseEntity<CommonResponse> getRecruitmentListInMain() {
        List<RecruitmentListDto> recruitmentList = recruitmentService.getMainList();
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.RECRUITMENT_LIST_FOR_MAIN, recruitmentList));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{recruitmentId}/application")
    public ResponseEntity<CommonResponse> checkApplyStatusForMember(@PathVariable Long recruitmentId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        RecruitmentApplyStatusForMember applyStatus = recruitmentService.isAlreadyApplyRecruitment(recruitmentId, userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.RECRUITMENT_APPLY_STATUS_FOR_MEMBER, applyStatus));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<CommonResponse> createRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @RequestPart @Validated RecruitmentRequestDto recruitment,
                                                            @RequestPart(required = false) List<MultipartFile> imgFiles) throws IOException {
        Long recruitmentId = recruitmentService.createRecruitment(userDetails.getId(), recruitment, imgFiles);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(SuccessCode.RECRUITMENT_CREATE, new RecruitmentIdDto(recruitmentId)));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{recruitmentId}")
    public ResponseEntity<CommonResponse> updateRecruitment(@PathVariable Long recruitmentId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @RequestPart(required = false) @Validated RecruitmentRequestDto recruitment,
                                                    @RequestPart(required = false) List<MultipartFile> imgFiles) throws IOException {
        recruitmentService.updateRecruitment(recruitmentId, userDetails.getId(), recruitment, imgFiles);
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.RECRUITMENT_UPDATE));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{recruitmentId}")
    public ResponseEntity<CommonResponse> deleteRecruitment(@PathVariable Long recruitmentId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        recruitmentService.deleteRecruitment(recruitmentId, userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.RECRUITMENT_DELETE));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{recruitmentId}/application/{applicationId}")
    public ResponseEntity<CommonResponse> changeApplicationStatus(@PathVariable Long recruitmentId, @PathVariable Long applicationId,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        matchingService.completeMatching(recruitmentId, applicationId, userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.APPLICATION_MATCHING_COMPLETE));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{recruitmentId}/application/{applicationId}")
    public ResponseEntity<CommonResponse> cancelApplication(@PathVariable Long recruitmentId, @PathVariable Long applicationId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        matchingService.cancelMatching(recruitmentId, applicationId, userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.APPLICATION_MATCHING_CANCEL));
    }
}
