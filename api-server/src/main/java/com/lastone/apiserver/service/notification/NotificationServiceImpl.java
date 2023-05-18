package com.lastone.apiserver.service.notification;

import com.lastone.apiserver.exception.notification.NotificationAlreadyReadException;
import com.lastone.apiserver.exception.notification.NotificationNotFoundException;
import com.lastone.apiserver.exception.notification.NotificationUnauthorizedException;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.domain.notification.Notification;
import com.lastone.core.dto.notification.NotificationCheckBoxCondition;
import com.lastone.core.dto.notification.NotificationDeleteRequestDto;
import com.lastone.core.dto.notification.NotificationResponseDto;
import com.lastone.core.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public void read(Long notificationId, Long requesterId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(NotificationNotFoundException::new);
        validateNotificationStatus(notification.isRead());
        validateUpdateAuthorization(notification.getMember().getId(), requesterId);
        notification.read();
    }

    @Override
    public void delete(NotificationDeleteRequestDto notificationDeleteRequestDto, Long requesterId) {
        if (ObjectUtils.isEmpty(notificationDeleteRequestDto)) {
            notificationRepository.deleteAllByMemberId(requesterId);
            return;
        }
        Set<Long> notificationIds = notificationRepository.findAllByMemberId(requesterId).stream()
                .map(Notification::getId)
                .collect(Collectors.toSet());

        validateDeleteAuthorization(notificationIds, notificationDeleteRequestDto.getDeleteIdList());
        notificationRepository.deleteByIdList(notificationDeleteRequestDto.getDeleteIdList());
    }

    private void validateNotificationStatus(boolean isRead) {
        if (isRead) {
            throw new NotificationAlreadyReadException();
        }
    }

    private void validateDeleteAuthorization(Set<Long> notificationIds, List<Long> deleteIdList) {
        if (!notificationIds.containsAll(deleteIdList)) {
            throw new NotificationUnauthorizedException(ErrorCode.NOTIFICATION_DELETE_FORBIDDEN);
        }
    }

    private void validateUpdateAuthorization(Long id, Long requesterId) {
        if (!id.equals(requesterId)) {
            throw new NotificationUnauthorizedException(ErrorCode.NOTIFICATION_UPDATE_FORBIDDEN);
        }
    }
}