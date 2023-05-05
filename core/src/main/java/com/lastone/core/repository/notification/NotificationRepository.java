package com.lastone.core.repository.notification;

import com.lastone.core.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {

    List<Notification> findAllByMemberId(Long memberId);

    @Modifying
    @Query("DELETE FROM Notification n WHERE n.member.id = :memberId")
    void deleteAllByMemberId(Long memberId);

    @Modifying
    @Query("DELETE FROM Notification n WHERE n.id IN :deleteIds")
    void deleteByIdList(@Param("deleteIds") List<Long> deleteIdList);
}
