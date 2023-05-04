package com.lastone.core.domain.notification;

import com.lastone.core.domain.member.Member;
import com.lastone.core.repository.BaseTime;
import com.lastone.core.util.BooleanToYNConverter;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notification_id")
    private Long id;

    private Long recruitmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String senderNickname;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Convert(converter = BooleanToYNConverter.class)
    @ColumnDefault("false")
    private boolean isRead;
}
