package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.partner.PartnerService;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.SuccessCode;
import com.lastone.core.dto.partner.PartnerHistoryDto;
import com.lastone.core.dto.partner.TodayPartnerDto;
import com.lastone.core.security.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partner")
public class PartnerController {

    private final PartnerService partnerService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/today")
    public ResponseEntity<CommonResponse<TodayPartnerDto>> getTodayPartner(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodayPartnerDto todayPartnerDto = partnerService.findTodayPartner(userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.TODAY_APPOINTMENT_INFO, todayPartnerDto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/history")
    public ResponseEntity<CommonResponse> getPartnerHistory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PartnerHistoryDto> partnerHistoryList = partnerService.findPartnerHistoryList(userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.PARTNER_HISTORY_LIST, partnerHistoryList));
    }
}
