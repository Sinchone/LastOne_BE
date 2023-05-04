package com.lastone.apiserver.service.notification;

import com.lastone.core.domain.notification.Notification;
import com.lastone.core.dto.notification.NotificationCheckBoxCondition;
import com.lastone.core.dto.notification.NotificationResponseDto;
import com.lastone.core.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getList(NotificationCheckBoxCondition checkBoxCondition, Long memberId) {
        List<Notification> notifications =  notificationRepository.getListByMemberId(checkBoxCondition, memberId);
        List<NotificationResponseDto> notificationResponseDtoList = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationResponseDto notificationResponseDto = NotificationResponseDto.builder()
                    .notificationId(notification.getId())
                    .notificationType(notification.getNotificationType())
                    .recruitmentId(notification.getRecruitment().getId())
                    .recruitmentTitle(notification.getRecruitment().getTitle())
                    .senderNickname(notification.getSenderNickname())
                    .isRead(notification.isRead())
                    .requestDate(notification.getCreatedAt())
                    .build();

            notificationResponseDtoList.add(notificationResponseDto);
        }
        return notificationResponseDtoList;
    }
}