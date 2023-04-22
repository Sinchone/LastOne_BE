package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.applyment.ApplymentService;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.SuccessCode;
import com.lastone.core.security.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/applyment")
@RequiredArgsConstructor
public class ApplymentController {
    private final ApplymentService applymentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> createApplyment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          @RequestBody HashMap<String, Long> recruitmentInfo) {
        Long recruitmentId = recruitmentInfo.get("recruitmentId");
        applymentService.createApplyment(userDetails.getId(), recruitmentId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(SuccessCode.APPLYMENT_CREATE));
    }
}
