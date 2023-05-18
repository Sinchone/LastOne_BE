package com.lastone.core.repository.notification;

import com.lastone.core.domain.notification.Notification;
import com.lastone.core.domain.notification.NotificationType;
import com.lastone.core.dto.notification.NotificationCheckBoxCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import java.util.List;
import static com.lastone.core.domain.notification.QNotification.notification;
import static com.lastone.core.domain.recruitment.QRecruitment.recruitment;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Notification> getListByMemberId(NotificationCheckBoxCondition checkBoxCondition, Long memberId) {
        return queryFactory
                .selectFrom(notification)
                .where(
                        notification.member.id.eq(memberId),
                        eqStatus(checkBoxCondition))
                .leftJoin(notification.recruitment, recruitment).fetchJoin()
                .orderBy(notification.createdAt.desc())
                .fetch();
    }

    private BooleanBuilder eqStatus(NotificationCheckBoxCondition checkBoxCondition) {
        if (ObjectUtils.isEmpty(checkBoxCondition)) {
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(checkBoxCondition.getMatchingCancelOnly()) && checkBoxCondition.getMatchingCancelOnly()) {
            builder.or(notification.notificationType.eq(NotificationType.MATCHING_CANCEL));
        }
        if (!ObjectUtils.isEmpty(checkBoxCondition.getMatchingSuccessOnly()) && checkBoxCondition.getMatchingSuccessOnly()) {
            builder.or(notification.notificationType.eq(NotificationType.MATCHING_SUCCESS));
        }
        if (!ObjectUtils.isEmpty(checkBoxCondition.getMatchingRequestOnly()) && checkBoxCondition.getMatchingRequestOnly()) {
            builder.or(notification.notificationType.eq(NotificationType.MATCHING_REQUEST));
        }
        return builder;
    }
}