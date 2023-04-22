package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.application.ApplicationService;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.SuccessCode;
import com.lastone.core.dto.applicaation.ApplicationReceivedDto;
import com.lastone.core.dto.applicaation.ApplicationRequestedDto;
import com.lastone.core.security.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping("/received")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> getReceivedApplication(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ApplicationReceivedDto> content = applicationService.getReceivedListByMemberId(userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.APPLICATION_RECEIVED_LIST, content));
    }

    @GetMapping("/requested")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> getRequestedApplication(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ApplicationRequestedDto> content = applicationService.getRequestedListByMemberId(userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.APPLICATION_REQUESTED_LIST, content));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> createApplication(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @RequestBody HashMap<String, Long> recruitmentInfo) {
        Long recruitmentId = recruitmentInfo.get("recruitmentId");
        applicationService.createApplication(userDetails.getId(), recruitmentId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(SuccessCode.APPLICATION_CREATE));
    }

    @DeleteMapping("/{applicationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> cancelApplication(@PathVariable Long applicationId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        applicationService.cancel(applicationId, userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.APPLICATION_REQUEST_CANCEL));
    }
}
