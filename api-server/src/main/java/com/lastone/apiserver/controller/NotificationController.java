package com.lastone.apiserver.controller;

import com.lastone.apiserver.service.notification.NotificationService;
import com.lastone.core.common.response.CommonResponse;
import com.lastone.core.common.response.SuccessCode;
import com.lastone.core.dto.notification.NotificationCheckBoxCondition;
import com.lastone.core.dto.notification.NotificationResponseDto;
import com.lastone.core.security.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<CommonResponse> getNotificationList(NotificationCheckBoxCondition checkBoxCondition,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<NotificationResponseDto> notificationResponseDtoList = notificationService.getList(checkBoxCondition, userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.NOTIFICATION_LIST,notificationResponseDtoList));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{notificationId}")
    public ResponseEntity<CommonResponse> readNotification(@PathVariable Long notificationId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        notificationService.read(notificationId, userDetails.getId());
        return ResponseEntity.ok().body(CommonResponse.success(SuccessCode.NOTIFICATION_READ));
    }
}