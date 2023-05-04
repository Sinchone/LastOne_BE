package com.lastone.core.repository.notification;

import com.lastone.core.domain.notification.Notification;
import com.lastone.core.dto.notification.NotificationCheckBoxCondition;
import java.util.List;

public interface NotificationRepositoryCustom {

    List<Notification> getListByMemberId(NotificationCheckBoxCondition checkBoxCondition, Long memberId);
}