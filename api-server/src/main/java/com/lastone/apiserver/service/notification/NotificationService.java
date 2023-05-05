package com.lastone.apiserver.service.notification;

import com.lastone.core.dto.notification.NotificationCheckBoxCondition;
import com.lastone.core.dto.notification.NotificationDeleteRequestDto;
import com.lastone.core.dto.notification.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    List<NotificationResponseDto> getList(NotificationCheckBoxCondition checkBoxCondition, Long memberId);

    void read(Long notificationId, Long requesterId);

    void delete(NotificationDeleteRequestDto notificationDeleteRequestDto, Long requesterId);
}
